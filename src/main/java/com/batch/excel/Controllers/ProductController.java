package com.batch.excel.Controllers;


import com.batch.excel.Helper.Helper;
import com.batch.excel.Services.ProductService;
import com.batch.excel.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/upload")
    public ResponseEntity<?> upload(@RequestParam ("file")MultipartFile file){

        if (Helper.checkExcelFormat(file)){

            //upload
            this.productService.save(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded successfully !! Data is saved to db !!"));

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file only");

        }

    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }


    @RequestMapping("/download")
    public ResponseEntity<Resource> downloadExcel() throws IOException {

        String fileName = "products.xlsx";
         ByteArrayInputStream actualData = this.productService.getActualData();
        InputStreamResource file = new InputStreamResource(actualData);
         ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName"+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);

         return body;
    }

}
