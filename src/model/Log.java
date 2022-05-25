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
public class Log {
    private Date dateLog;
    private String ResponseLog;
    private int errorLog;

    public Log() {
    }

    public Log(Date dateLog, String ResponseLog, int errorLog) {
        this.dateLog = dateLog;
        this.ResponseLog = ResponseLog;
        this.errorLog = errorLog;
    }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }

    public String getResponseLog() {
        return ResponseLog;
    }

    public void setResponseLog(String ResponseLog) {
        this.ResponseLog = ResponseLog;
    }

    public int getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(int errorLog) {
        this.errorLog = errorLog;
    }

    
    
    
}
