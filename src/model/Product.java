/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ivona
 */
public class Product {
    private String productNumber;
    private String productName;
    private String unitOfQuantity;
    private String eanConsumerUnit;

    public Product() {
    }

    public Product(String productNumber, String productName, String unitOfQuantity, String eanConsumerUnit) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.unitOfQuantity = unitOfQuantity;
        this.eanConsumerUnit = eanConsumerUnit;
    }

    public String getEanConsumerUnit() {
        return eanConsumerUnit;
    }

    public void setEanConsumerUnit(String eanConsumerUnit) {
        this.eanConsumerUnit = eanConsumerUnit;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitOfQuantity() {
        return unitOfQuantity;
    }

    public void setUnitOfQuantity(String unitOfQuantity) {
        this.unitOfQuantity = unitOfQuantity;
    }
    
    
}
