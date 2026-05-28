package com.batch.excel.Helper;

import com.batch.excel.entities.Guest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuestHelper {

    public static String[] HEADERS = {
            "id",
            "email",
            "name",
            "gender",
            "guestCategory",
            "adultOrchild",
            "phoneNumber",
            "whatsapp_number",
            "familyId"
    };

    public static String SHEET_NAME = "GUESTS_DETAILS";

    // dumping data to excel from db
    public static ByteArrayInputStream dataToExcel(List<Guest> guestList) throws IOException {
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
            for (Guest guest : guestList) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(guest.getId());
                dataRow.createCell(1).setCellValue(guest.getName());
                dataRow.createCell(2).setCellValue(guest.getEmail());
                dataRow.createCell(3).setCellValue(guest.getGender());
                dataRow.createCell(4).setCellValue(guest.getGuestCategory());
                dataRow.createCell(5).setCellValue(guest.getAdultOrchild());
                dataRow.createCell(6).setCellValue(guest.getPhoneNumber());
                dataRow.createCell(7).setCellValue(guest.getWhatsapp_Number());
                dataRow.createCell(8).setCellValue(guest.getFamilyId());
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
    public static List<Guest> convertExcelToListOfGuests(InputStream is) {
        List<Guest> guests = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            // Hamesha index 0 se sheet fetch karein taaki naam badalne par code na toote
            Sheet sheet = workbook.getSheetAt(0);

            int totalRows = sheet.getPhysicalNumberOfRows();

            // Row 1 se start karein (Row 0 header hai)
            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Agar poori row blank ho toh skip karein

                Guest guest = new Guest();

                // 0: id
                Cell cell0 = row.getCell(0);
                if (cell0 != null && cell0.getCellType() == CellType.NUMERIC) {
                    guest.setId((int) cell0.getNumericCellValue());
                }

                // 1: email (Khaali hone par bhi safe)
                Cell cell1 = row.getCell(1);
                guest.setEmail(dataFormatter.formatCellValue(cell1));

                // 2: name
                Cell cell2 = row.getCell(2);
                guest.setName(dataFormatter.formatCellValue(cell2));

                // 3: gender
                Cell cell3 = row.getCell(3);
                guest.setGender(dataFormatter.formatCellValue(cell3));

                // 4: guestCategory
                Cell cell4 = row.getCell(4);
                guest.setGuestCategory(dataFormatter.formatCellValue(cell4));

                // 5: adultOrchild
                Cell cell5 = row.getCell(5);
                guest.setAdultOrchild(dataFormatter.formatCellValue(cell5));

                // 6: phoneNumber
                Cell cell6 = row.getCell(6);
                guest.setPhoneNumber(dataFormatter.formatCellValue(cell6));

                // 7: whatsapp_Number
                Cell cell7 = row.getCell(7);
                guest.setWhatsapp_Number(dataFormatter.formatCellValue(cell7));

                // 8: familyId
                Cell cell8 = row.getCell(8);
                guest.setFamilyId(dataFormatter.formatCellValue(cell8));

                guests.add(guest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return guests;
    }
}
