const { Pool } = require('pg');
const { format } = require('@fast-csv/format');
const ExcelJS = require('exceljs');

const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
  ssl: { rejectUnauthorized: false }
});

module.exports = async function (context, req) {
  const tabla = (req.query.tabla || '').trim().toLowerCase();
  const formato = (req.query.formato || 'csv').trim().toLowerCase();
  const codigoLote = req.query.codigoLote || null;

  if (!tabla) {
    context.res = { status: 400, body: { mensaje: '❌ Falta el parámetro \"tabla\".' } };
    return;
  }

  try {
    // Construir query SQL dinámico (básico con filtro opcional por codigoLote)
    let query = `SELECT * FROM ${tabla}`;
    const valores = [];

    if (codigoLote) {
      query += ` WHERE codigo_lote = $1`;
      valores.push(codigoLote);
    }

    const result = await pool.query(query, valores);

    if (formato === 'excel') {
      const workbook = new ExcelJS.Workbook();
      const worksheet = workbook.addWorksheet('Datos');

      if (result.rows.length > 0) {
        worksheet.columns = Object.keys(result.rows[0]).map(key => ({ header: key, key: key }));
        result.rows.forEach(row => worksheet.addRow(row));
      }

      const buffer = await workbook.xlsx.writeBuffer();

      context.res = {
        status: 200,
        headers: {
          'Content-Type': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
          'Content-Disposition': `attachment; filename=${tabla}.xlsx`
        },
        body: buffer
      };
    } else { // CSV por defecto
      let csvData = '';
      const csvStream = format({ headers: true });
      csvStream.on('data', chunk => csvData += chunk);
      result.rows.forEach(row => csvStream.write(row));
      csvStream.end();

      await new Promise(resolve => csvStream.on('end', resolve));

      context.res = {
        status: 200,
        headers: {
          'Content-Type': 'text/csv',
          'Content-Disposition': `attachment; filename=${tabla}.csv`
        },
        body: csvData
      };
    }
  } catch (error) {
    context.res = {
      status: 500,
      body: { mensaje: '❌ Error al exportar', error: error.message }
    };
  }
};
