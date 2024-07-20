package com.batch.excel.Helper;

import com.batch.excel.entities.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    public static String[] HEADERS = {
            "id",
            "name",
            "salePrice",
            "unitPrice",
            "units"
    };

    public static String SHEET_NAME = "PRODUCT_DETAILS";

    // dumping data to excel from db
    public static ByteArrayInputStream dataToExcel(List<Product> productList) throws IOException {
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
            for (Product product : productList) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(product.getProductId());
                dataRow.createCell(1).setCellValue(product.getProductName());
                dataRow.createCell(2).setCellValue(product.getSalePrice());
                dataRow.createCell(3).setCellValue(product.getUnitPrice());
                dataRow.createCell(4).setCellValue(product.getUnits());
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

    //converting excel to list of products
    public static List<Product> convertExcelToListOfProducts(InputStream is) {
        List<Product> products = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;

                }

                Iterator<Cell> cells = row.iterator();

                Product product = new Product();

                int cid = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 0:
                            product.setProductId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            product.setProductName(cell.getStringCellValue());
                            break;
                        case 2:
                            product.setUnits((int) cell.getNumericCellValue());
                            break;
                        case 3:
                            product.setUnitPrice((double) cell.getNumericCellValue());
                            break;
                        case 4:
                            product.setSalePrice(cell.getNumericCellValue());
                            break;

                        default:
                            break;

                    }

                    cid++;

                }

                products.add(product);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        return products;
    }

}
