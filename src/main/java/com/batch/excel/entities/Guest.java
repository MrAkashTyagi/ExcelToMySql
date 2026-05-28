package com.batch.excel.entities;

import jakarta.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phoneNumber;
    private String whatsapp_Number;
    private String email;
    private String guestCategory;
    private String gender;
    private String adultOrchild;
    private String familyId;

    public Guest(Integer id, String name, String phoneNumber, String whatsapp_Number, String email, String guestCategory, String gender, String adultOrchild, String familyId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.whatsapp_Number = whatsapp_Number;
        this.email = email;
        this.guestCategory = guestCategory;
        this.gender = gender;
        this.adultOrchild = adultOrchild;
        this.familyId = familyId;
    }

    public Guest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsapp_Number() {
        return whatsapp_Number;
    }

    public void setWhatsapp_Number(String whatsapp_Number) {
        this.whatsapp_Number = whatsapp_Number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuestCategory() {
        return guestCategory;
    }

    public void setGuestCategory(String guestCategory) {
        this.guestCategory = guestCategory;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdultOrchild() {
        return adultOrchild;
    }

    public void setAdultOrchild(String adultOrchild) {
        this.adultOrchild = adultOrchild;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
}
