package com.batch.excel.Helper;

import com.batch.excel.entities.Family;
import com.batch.excel.entities.Guest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FamilyHelper {

    public static String[] HEADERS= {

            "id",
            "name"

    };

    public static String SHEET_NAME = "FAMILY_DETAILS";

    // dumping data to excel from db
    public static ByteArrayInputStream dataToExcel(List<Family> familyList) throws IOException {
        //crerate workbbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //create row: header row
            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }


            //create row: value rows
            int rowIndex = 1;
            for (Family family : familyList) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(family.getId());
                dataRow.createCell(1).setCellValue(family.getFamilyName());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            workbook.close();
            out.close();
        }
    }

    //check if the file is of excel type
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    //converting excel to list of guests
    public static List<Family> convertExcelToListOfFamilies(InputStream is) {
        List<Family> families = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            // Hamesha index 0 se sheet fetch karein taaki naam badalne par code na toote
            Sheet sheet = workbook.getSheetAt(0);

            int totalRows = sheet.getPhysicalNumberOfRows();

            // Row 1 se start karein (Row 0 header hai)
            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Agar poori row blank ho toh skip karein

                Family family = new Family();

                // 0: id
                Cell cell0 = row.getCell(0);
                if (cell0 != null && cell0.getCellType() == CellType.NUMERIC) {
                    family.setId((int) cell0.getNumericCellValue());
                }

                // 1: email (Khaali hone par bhi safe)
                Cell cell1 = row.getCell(1);
                family.setFamilyName(dataFormatter.formatCellValue(cell1));


                families.add(family);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return families;
    }



}
