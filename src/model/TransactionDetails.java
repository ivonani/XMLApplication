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
public class TransactionDetails {
    private String tipTransaction;
    private String invoiceNumber;
    private String productNumber;
    private double quantity;

    public TransactionDetails() {
    }

    public TransactionDetails(String tipTransaction, String invoiceNumber, String productNumber, double quantity) {
        this.tipTransaction = tipTransaction;
        this.invoiceNumber = invoiceNumber;
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getTipTransaction() {
        return tipTransaction;
    }

    public void setTipTransaction(String tipTransaction) {
        this.tipTransaction = tipTransaction;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
    
    
}
