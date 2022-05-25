/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author ivona
 */
public class Header {
    private int id;
    private String structureVersion;
    private String wholesalerID;
    private String periodType;
    private Date dateFrom;
    private Date dateTo;
    private int totalRecordsCount;
    private double totalVolume;

    public Header() {

    }

    public Header(int id, String structureVersion, String wholesalerID, String periodType, Date dateFrom, Date dateTo, int totalRecordsCount, double totalVolume) {
        this.id = id;
        this.structureVersion = structureVersion;
        this.wholesalerID = wholesalerID;
        this.periodType = periodType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.totalRecordsCount = totalRecordsCount;
        this.totalVolume = totalVolume;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStructureVersion() {
        return structureVersion;
    }

    public void setStructureVersion(String structureVersion) {
        this.structureVersion = structureVersion;
    }

    public String getWholesalerID() {
        return wholesalerID;
    }

    public void setWholesalerID(String wholesalerID) {
        this.wholesalerID = wholesalerID;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    

    public int getTotalRecordsCount() {
        return totalRecordsCount;
    }

    public void setTotalRecordsCount(int totalRecordsCount) {
        this.totalRecordsCount = totalRecordsCount;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    
    
}
