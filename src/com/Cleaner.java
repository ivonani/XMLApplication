/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;

/**
 *
 * @author ivona
 */
public class Cleaner {

    public void clearDB() {
        boolean feedback = Controller.getInstance().clearDatabase();

        if (feedback) {
            System.out.println("Database data successfully deleted");
        } else {
            System.out.println("Database data unsuccessfully deleted");
        }
    }

}
