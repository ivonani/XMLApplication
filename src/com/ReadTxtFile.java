/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author ivona
 */
public class ReadTxtFile {   
    CreateXML cr;

    public void readDBName() {
        File file = new File("C:\\InSoft\\Baza.txt");
        String name = "";
        LinkedList<String> data = new LinkedList<>();
        cr = new CreateXML();
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                name = input.nextLine();
                data.add(name);
            }
        } catch (Exception e) {
            System.out.println("File not found or is empty");
            return;
        }
        //Controller.getInstance().setDBName(data.get(0), data.get(1));
    }

}
