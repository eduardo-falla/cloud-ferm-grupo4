const { BlobServiceClient } = require('@azure/storage-blob');
const { Pool } = require('pg');
const axios = require("axios");

const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
  ssl: { rejectUnauthorized: false }
});

module.exports = async function (context, req) {
  try {
    // --- PARÁMETROS RECIBIDOS ---
    const codigoLote = (req.query.codigoLote || 'sinCodigo').trim();
    const co2ppm = parseFloat(req.query.co2ppm || '0');

    const comentario = (req.query.comentario || '').trim();
    const responsable = (req.query.responsable || '').trim();
    const fechaInicio = (req.query.fechaInicio || '').trim();
    const duracionEstimada = parseInt(req.query.duracionEstimada || '0');
    const metodo = parseInt(req.query.metodo || '0');
    const variedad = parseInt(req.query.variedad || '0');
    const peso = parseFloat(req.query.peso || '0');

    // --- CONVERTIR CUERPO A BUFFER ---
    let buffer;
    const tipo = typeof req.body;

    if (Buffer.isBuffer(req.body)) {
      buffer = req.body;
    } else if (tipo === 'string') {
      buffer = Buffer.from(req.body, 'base64');
    } else if (req.body?.buffer) {
      buffer = Buffer.from(req.body.buffer);
    }

    if (!buffer || buffer.length < 100) {
      return context.res = {
        status: 400,
        body: { mensaje: '❌ El archivo está vacío o inválido.' }
      };
    }

    // --- CONEXIÓN A BLOB STORAGE ---
    const AZURE_STORAGE_CONNECTION_STRING = process.env.STORAGE_CONNECTION_STRING;
    const blobServiceClient = BlobServiceClient.fromConnectionString(AZURE_STORAGE_CONNECTION_STRING);
    const containerName = 'imagenes';
    const containerClient = blobServiceClient.getContainerClient(containerName);
    await containerClient.createIfNotExists();

    // --- NOMBRE DEL ARCHIVO ---
    const safeDate = new Date().toISOString().replace(/[:.]/g, '-');
    const blobName = `${codigoLote}_${safeDate}.jpg`;
    const blockBlobClient = containerClient.getBlockBlobClient(blobName);

    // --- SUBIR IMAGEN ---
    await blockBlobClient.uploadData(buffer, {
      blobHTTPHeaders: { blobContentType: 'image/jpeg' }
    });

    const urlImagen = blockBlobClient.url;

    // Duplicar la imagen como 'ultima_captura.jpg' para visualización fija
    const nombreUltima = "ultima_captura.jpg";
    const ultimaBlobClient = containerClient.getBlockBlobClient(nombreUltima);

    // Copiar la imagen original a la nueva
    const copyResult = await ultimaBlobClient.beginCopyFromURL(urlImagen);
    await copyResult.pollUntilDone();

    const urlUltimaImagen = ultimaBlobClient.url;

    // --- GUARDAR EN SUPABASE ---
    await pool.query(
      `INSERT INTO registros_fermentacion 
       (codigo_lote, co2ppm, comentario, responsable, fecha_inicio, duracion_estimada, metodo_fermentacion, variedad_cacao, peso_bruto, url_imagen) 
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)`,
      [codigoLote, co2ppm, comentario, responsable, fechaInicio, duracionEstimada, metodo, variedad, peso, urlImagen]
    );

    // --- LOG DE DEBUG ---
    context.log("📦 Datos insertados en Supabase:", {
      codigoLote, co2ppm, comentario, responsable, fechaInicio,
      duracionEstimada, metodo, variedad, peso, urlImagen
    });

    // --- ACTUALIZAR PROPIEDAD EN IOT CENTRAL ---
    // Parámetros de conexión
    const deviceId = "13w6fa3y2nk";  // Ajusta si es dinámico
    const appSubdomain = "monitordefermentacion-fincavalentin";
    const apiVersion = "2022-07-31";
    const token = "SharedAccessSignature sr=59356816-64a1-4afc-b9c3-12a1f334dcc5&sig=7%2BfCxMxNVOjgn3oQ3nKROcSmy5jEJGzt38WprfbkuVw%3D&skn=CacaoToken&se=1783773251040";

    const url = `https://${appSubdomain}.azureiotcentral.com/api/devices/${deviceId}/properties?api-version=${apiVersion}`;

    // Construir contenido Markdown
    const timestamp = Date.now();
    //const markdown = `![Última Imagen](${urlUltimaImagen}?t=${timestamp})`;

    const markdown = urlImagen;
    //const markdown = "https://storagetest01062025.blob.core.windows.net/imagenes/ultima_captura.jpg";
    //const markdown = `![Última Imagen](${urlUltimaImagen})`;

    try {
      context.log("📡 Enviando PATCH a IoT Central con el siguiente contenido:");
      context.log({
        url,
        payload: { ContenidoMarkdown: markdown },
        headers: {
          Authorization: token,
          "Content-Type": "application/json"
        }
      });

      const respuestaIot = await axios.patch(
        url,
        { ContenidoMarkdown: markdown },
        {
          headers: {
            Authorization: token,
            "Content-Type": "application/json"
          }
        }
      );

      context.log(`✅ ContenidoMarkdown enviado exitosamente a IoT Central.`);
      context.log(`🔢 Código de respuesta: ${respuestaIot.status}`);
      context.log(`📨 Respuesta de Azure:`, respuestaIot.data);
    } catch (error) {
      context.log.error("❌ Error al enviar ContenidoMarkdown a IoT Central:");
      context.log.error(`🧾 Mensaje: ${error.message}`);
      if (error.response) {
        context.log.error(`🔢 Código de estado: ${error.response.status}`);
        context.log.error(`📨 Respuesta: ${JSON.stringify(error.response.data)}`);
      }
    }

    // --- RESPUESTA OK ---
    context.res = {
      status: 200,
      body: {
        mensaje: `✅ Imagen subida como '${blobName}' y datos guardados en Supabase.`,
        url: urlImagen
      }
    };

  } catch (error) {
    context.res = {
      status: 500,
      body: {
        mensaje: '❌ Error interno',
        error: error.message,
        stack: error.stack
      }
    };
  }
};
