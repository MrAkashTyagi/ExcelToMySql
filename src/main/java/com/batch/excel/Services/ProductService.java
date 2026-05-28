package com.batch.excel.Services;


import com.batch.excel.Helper.Helper;
import com.batch.excel.entities.Family;
import com.batch.excel.entities.Guest;
import com.batch.excel.entities.Product;
import com.batch.excel.repository.ProductRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    //saving data from excel to db
    public void save(MultipartFile file){

        try {
            List<Product> products= Helper.convertExcelToListOfProducts(file.getInputStream());
            this.productRepo.saveAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Product> getAllProducts(){

        return this.productRepo.findAll();
    }


    public ByteArrayInputStream getActualData() throws IOException {
         List<Product> productList = this.productRepo.findAll();
        System.out.println(productList);
         ByteArrayInputStream stream = Helper.dataToExcel(productList);
         return stream;
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
    public static List<Family> convertExcelToListOfFamily(InputStream is) {
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
