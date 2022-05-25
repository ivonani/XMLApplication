/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.Cleaner;
import com.CreateXML;
import com.ReadTxtFile;
import com.XMLHttpClient;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivona
 */
public class Start extends TimerTask {

    CreateXML cr;
    XMLHttpClient xhc;
    ReadTxtFile rtf;
    Cleaner cl;

    @Override
    public void run() {
        System.out.println("Started");
        rtf = new ReadTxtFile();
        cr = new CreateXML();
        try {
            rtf.readDBName();
            cr.create();
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
