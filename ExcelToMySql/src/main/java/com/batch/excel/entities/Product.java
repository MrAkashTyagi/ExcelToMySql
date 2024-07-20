package com.batch.excel.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

        @Id
        private Integer productId;
        private String productName;
        private Integer units;
        private Double unitPrice;
        private Double salePrice;

    public Product() {
    }

    public Product(Integer productId, String productName, Integer units, Double unitPrice, Double salePrice) {
        this.productId = productId;
        this.productName = productName;
        this.units = units;
        this.unitPrice = unitPrice;
        this.salePrice = salePrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
}
