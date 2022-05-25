/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ivona
 */
public class Main {

    public static void main(String[] args) {
        Start s = new Start();
        s.run();
        /*Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Timer t = new Timer();

        t.schedule(new Start(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));*/
    }
}
