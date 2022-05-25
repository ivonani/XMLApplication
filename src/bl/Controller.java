/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import dbb.DBBroker;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Header;
import model.Outlet;
import model.Product;
import model.Log;
import model.Sale;
import model.TransactionDetails;

/**
 *
 * @author ivona
 */
public class Controller {

    private static Controller instance;
    DBBroker dbb;

    private Controller() {
        dbb = new DBBroker();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public LinkedList<Header> returnHeaders() {
        LinkedList<Header> headers = new LinkedList<>();
        Log log;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            headers = dbb.returnHeaders();

            if (headers.isEmpty()) {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                log = new Log(date, "Tabela cchZaglavlje prazna", -1);
                dbb.saveLog(log);
                return null;
            }

            if (headers.size() > 1) {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                log = new Log(date, "Podaci u tabeli cchZaglavlje nisu ispravni", -2);
                dbb.saveLog(log);
                return null;
            }

            dbb.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
            dbb.saveLog(log);
            return null;
        }

        return headers;
    }

    public LinkedList<Outlet> returnOutlets() {
        LinkedList<Outlet> outlets = new LinkedList<>();
        Log log;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            outlets = dbb.returnOutlets();

            dbb.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
            dbb.saveLog(log);
        }

        return outlets;
    }

    public LinkedList<Sale> returnSales() {
        LinkedList<Sale> sales = new LinkedList<>();
        Log log;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            sales = dbb.returnSales();

            dbb.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
            dbb.saveLog(log);
        }

        return sales;
    }

    public LinkedList<Product> returnProducts() {
        LinkedList<Product> products = new LinkedList<>();
        Log log;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            products = dbb.returnProducts();

            dbb.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
            dbb.saveLog(log);
        }

        return products;
    }

    public boolean clearDatabase() {
        boolean feedback = false;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            feedback = dbb.clearDatabase();
            if (feedback) {
                dbb.commit();
            } else {
                dbb.rollback();
            }
            dbb.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            dbb.rollback();
            feedback = false;
        }

        return feedback;
    }

    public boolean saveLog(Log res) {
        boolean feedback = false;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            feedback = dbb.saveLog(res);

            dbb.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            feedback = false;
        }

        return feedback;
    }

    public void setDBName(String serverName, String dbName) {
        dbb.setNameDB(dbName);
        dbb.setServerName(serverName);
    }
}
