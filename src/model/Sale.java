/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ivona
 */
public class Sale {
    private String tipTransaction;
    private String invoiceNumber;
    private String outletnumber;
    private Date deliveryDate;
    private LinkedList<TransactionDetails> listTransactionDetails;

    public Sale() {
    }

    public Sale(String tipTransaction, String invoiceNumber, String outletnumber, Date deliveryDate) {
        this.tipTransaction = tipTransaction;
        this.invoiceNumber = invoiceNumber;
        this.outletnumber = outletnumber;
        this.deliveryDate = deliveryDate;
    }

    public Sale(String tipTransaction, String invoiceNumber, String outletnumber, Date deliveryDate, LinkedList<TransactionDetails> listTransactionDetails) {
        this.tipTransaction = tipTransaction;
        this.invoiceNumber = invoiceNumber;
        this.outletnumber = outletnumber;
        this.deliveryDate = deliveryDate;
        this.listTransactionDetails = listTransactionDetails;
    }
    
    
    
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public String getOutletnumber() {
        return outletnumber;
    }

    public void setOutletnumber(String outletnumber) {
        this.outletnumber = outletnumber;
    }

    public LinkedList<TransactionDetails> getListTransactionDetails() {
        return listTransactionDetails;
    }

    public void setListTransactionDetails(LinkedList<TransactionDetails> listTransactionDetails) {
        this.listTransactionDetails = listTransactionDetails;
    }
    
    
}
