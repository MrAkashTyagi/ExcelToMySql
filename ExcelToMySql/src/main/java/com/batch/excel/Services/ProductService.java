package com.batch.excel.Services;


import com.batch.excel.Helper.Helper;
import com.batch.excel.entities.Product;
import com.batch.excel.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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


}
