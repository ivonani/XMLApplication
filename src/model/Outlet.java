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
public class Outlet {
    private String outletNumber;
    private String name1;
    private String address1;
    private String postalCode;
    private String city;
    private String telephone1;
    private String telephone2;
    private String fax;
    private String email;
    private String vatNumber;

    public Outlet() {
    }

    public Outlet(String outletNumber, String name1, String address1, String postalCode, String city, String telephone1, String telephone2, String fax, String email, String vatNumber) {
        this.outletNumber = outletNumber;
        this.name1 = name1;
        this.address1 = address1;
        this.postalCode = postalCode;
        this.city = city;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.fax = fax;
        this.email = email;
        this.vatNumber = vatNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getOutletNumber() {
        return outletNumber;
    }

    public void setOutletNumber(String outletNumber) {
        this.outletNumber = outletNumber;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
