package com.batch.excel.Controllers;

import com.batch.excel.Helper.GuestHelper;
import com.batch.excel.Helper.Helper;
import com.batch.excel.Services.GuestService;
import com.batch.excel.Services.ProductService;
import com.batch.excel.entities.Guest;
import com.batch.excel.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/guests")
public class GuestController {

        @Autowired
        private GuestService guestService;

        @PostMapping("/upload")
        public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){

            if (Helper.checkExcelFormat(file)){

                //upload
                this.guestService.save(file);
                return ResponseEntity.ok(Map.of("message", "File is uploaded successfully !! Data is saved to db !!"));

            }else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file only");

            }

        }

        @GetMapping("/guest")
        public List<Guest> getAllProducts(){
            return this.guestService.getAllGuests();
        }


        @RequestMapping("/download")
        public ResponseEntity<Resource> downloadExcel() throws IOException {

            String fileName = "products.xlsx";
            ByteArrayInputStream actualData = this.guestService.getActualData();
            InputStreamResource file = new InputStreamResource(actualData);
            ResponseEntity<Resource> body = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName"+fileName)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(file);

            return body;
        }


}
