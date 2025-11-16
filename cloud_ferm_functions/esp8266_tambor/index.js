const axios = require("axios");

module.exports = async function (context, req) {
  const estadoMezcla = parseInt(req.query.estadoMezcla || req.body?.estadoMezcla);

  if (isNaN(estadoMezcla)) {
    context.res = {
      status: 400,
      body: {
        mensaje: "❌ Se requiere el parámetro numérico 'estadoMezcla'",
        recibido: req.body || {}
      }
    };
    return;
  }

  // Parámetros de tu aplicación IoT Central
  const deviceId = "13w6fa3y2nk";
  const appSubdomain = "monitordefermentacion-fincavalentin";
  const apiVersion = "2022-07-31";

  // Token API generado desde Azure IoT Central (CacaoToken)
  const token = "SharedAccessSignature sr=59356816-64a1-4afc-b9c3-12a1f334dcc5&sig=7%2BfCxMxNVOjgn3oQ3nKROcSmy5jEJGzt38WprfbkuVw%3D&skn=CacaoToken&se=1783773251040";

  const url = `https://${appSubdomain}.azureiotcentral.com/api/devices/${deviceId}/properties?api-version=${apiVersion}`;

  try {
    const response = await axios.patch(url, { EstadoMezcla: estadoMezcla }, {
      headers: {
        Authorization: token,
        "Content-Type": "application/json"
      }
    });

    context.res = {
      status: 200,
      body: {
        mensaje: `✅ EstadoMezcla actualizado a ${estadoMezcla}`,
        estadoEnviado: estadoMezcla,
        statusCode: response.status
      }
    };
  } catch (error) {
    context.res = {
      status: 500,
      body: {
        mensaje: "❌ Error al enviar a IoT Central",
        error: error.message,
        enviadoA: url,
        estadoEnviado: estadoMezcla
      }
    };
  }
};
