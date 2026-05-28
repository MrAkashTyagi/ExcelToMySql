package com.batch.excel.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Family {

    @Id
    private Integer id;

    private String familyName;

    public Family(Integer id, String familyName) {
        this.id = id;
        this.familyName = familyName;
    }

    public Family() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
