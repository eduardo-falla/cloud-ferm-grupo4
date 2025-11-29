package com.cloudferm.service;

import com.cloudferm.model.Batch;
import com.cloudferm.model.DailyRecord;
import com.cloudferm.repository.BatchRepository;
import com.cloudferm.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BatchRepository batchRepository;
    private final RecordRepository recordRepository;

    public byte[] generateExcelReport(Long batchId) throws IOException {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        
        List<DailyRecord> records = recordRepository.findByBatchIdOrderByRecordedAtAsc(batchId);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Batch Report");

            // Create header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            int rowNum = 0;

            // Batch Information Header
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("CLOUDFERM - Batch Fermentation Report");
            titleCell.setCellStyle(headerStyle);

            rowNum++; // Empty row

            // Batch Details
            createInfoRow(sheet, rowNum++, "Batch Code:", batch.getCode());
            createInfoRow(sheet, rowNum++, "Cocoa Variety:", batch.getCocoaVariety().getName());
            createInfoRow(sheet, rowNum++, "Gross Weight (kg):", String.valueOf(batch.getGrossWeight()));
            createInfoRow(sheet, rowNum++, "Start Date:", batch.getStartDate().toString());
            if (batch.getEndDate() != null) {
                createInfoRow(sheet, rowNum++, "End Date:", batch.getEndDate().toString());
            }
            createInfoRow(sheet, rowNum++, "Status:", batch.getStatus().name());
            createInfoRow(sheet, rowNum++, "Operator:", batch.getOperator().getFullName());

            rowNum++; // Empty row

            // Daily Records Table
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Date/Time", "Temp Mass (°C)", "Temp Ambient (°C)", 
                               "Humidity Mass (%)", "Humidity Ambient (%)", "pH", "Turned", "Observations"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (DailyRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getRecordedAt().format(formatter));
                row.createCell(1).setCellValue(record.getTempMass());
                row.createCell(2).setCellValue(record.getTempAmbient());
                row.createCell(3).setCellValue(record.getHumidityMass());
                row.createCell(4).setCellValue(record.getHumidityAmbient());
                row.createCell(5).setCellValue(record.getPhLevel());
                row.createCell(6).setCellValue(record.isTurned() ? "Yes" : "No");
                row.createCell(7).setCellValue(record.getOperatorObservations() != null ? 
                                              record.getOperatorObservations() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void createInfoRow(Sheet sheet, int rowNum, String label, String value) {
        Row row = sheet.createRow(rowNum);
        Cell labelCell = row.createCell(0);
        labelCell.setCellValue(label);
        
        Font boldFont = sheet.getWorkbook().createFont();
        boldFont.setBold(true);
        CellStyle boldStyle = sheet.getWorkbook().createCellStyle();
        boldStyle.setFont(boldFont);
        labelCell.setCellStyle(boldStyle);
        
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
    }
}
