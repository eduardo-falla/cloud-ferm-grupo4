const { Pool } = require('pg');
const pool = new Pool({ connectionString: process.env.DATABASE_URL });

module.exports = async function (context, req) {
  const codigoLote = req.query.codigoLote || 'SIN_LOTE';
  const estadoMezcla = parseInt(req.query.estadoMezcla || '0');
  const responsable = req.query.responsable || 'DESCONOCIDO';

  const timestamp = new Date().toISOString();

  try {
    await pool.query(
      `INSERT INTO frm_estado_mezcla (codigo_lote, estado_mezcla, responsable, fecha_hora)
       VALUES ($1, $2, $3, $4)`,
      [codigoLote, estadoMezcla, responsable, timestamp]
    );

    context.res = {
      status: 200,
      body: { mensaje: '✅ Log registrado en base de datos.' }
    };
  } catch (err) {
    context.res = {
      status: 500,
      body: { mensaje: '❌ Error al registrar log', error: err.message }
    };
  }
};
