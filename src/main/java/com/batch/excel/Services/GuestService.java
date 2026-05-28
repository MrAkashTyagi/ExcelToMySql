package com.batch.excel.Services;

import com.batch.excel.Helper.GuestHelper;
import com.batch.excel.Helper.Helper;
import com.batch.excel.entities.Guest;
import com.batch.excel.entities.Product;
import com.batch.excel.repository.GuestRepo;
import com.batch.excel.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GuestService {


    @Autowired
    private GuestRepo guestRepo;

    //saving data from excel to db
    public void save(MultipartFile file) {

        try {
            List<Guest> guests = GuestHelper.convertExcelToListOfGuests(file.getInputStream());
            this.guestRepo.saveAll(guests);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Guest> getAllGuests() {
        return this.guestRepo.findAll();
    }

    public ByteArrayInputStream getActualData() throws IOException {
        List<Guest> guestList = this.guestRepo.findAll();
        System.out.println(guestList);
        ByteArrayInputStream stream = GuestHelper.dataToExcel(guestList);
        return stream;
    }

}
