/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbb;

import form.ErrorForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.swing.JOptionPane;
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
public class DBBroker {

    private Connection conn;
    private String nameDB;
    private String serverName;
    ErrorForm ef;

    public void loadDriver() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            System.exit(1); 
        }
    }

    public void openConnection() throws SQLException {
        String url, user, pass;
        try {
            url = "jdbc:sqlserver://" + serverName + ";database=" + nameDB + ";";
            user = "cchUser";
            pass = "In$oft60181680";
            conn = DriverManager.getConnection(url, user, pass);
            //integratedSecurity=true;
            //jdbc:sqlserver://servername=server_name;integratedSecurity=true;authenticationScheme=JavaKerberos;userName=user@REALM;password=****
            System.out.println("Successfully established connection");
        } catch (SQLException ex) {
            System.out.println("Unsuccessful connection");
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            System.exit(1);  
            throw ex;
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            System.exit(1); 
        }
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            return; 
        }
    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            return;
        }
    }

    public LinkedList<Header> returnHeaders() {
        String sql = "select * from cchZaglavlje";
        LinkedList<Header> headers = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Header h = new Header();
                String structureVersion = rs.getString("StructureVersion");
                h.setStructureVersion(structureVersion);

                String wholesalerID = rs.getString("WholesalerID");
                h.setWholesalerID(wholesalerID);

                String periodType = rs.getString("PeriodType");
                h.setPeriodType(periodType);

                Date dateFrom = new Date(rs.getDate("DateFrom").getTime());
                h.setDateFrom(dateFrom);

                Date dateTo = new Date(rs.getDate("DateTo").getTime());
                h.setDateTo(dateTo);

                int totalRecordsCount = rs.getInt("TotalRecordsCount");
                h.setTotalRecordsCount(totalRecordsCount);

                double totalVolume = rs.getDouble("TotalVolume");
                h.setTotalVolume(totalVolume);
                headers.add(h);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);    
        }

        return headers;
    }

    public LinkedList<Outlet> returnOutlets() {
        String sql = "select * from cchOutlets";
        LinkedList<Outlet> outlets = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String outletNumber = rs.getString("OutletNumber");
                String name1 = rs.getString("Name1");
                String address1 = rs.getString("Address1");
                String postalCode = rs.getString("PostalCode");
                String city = rs.getString("City");
                String telephone1 = rs.getString("Telephone1");
                String telephone2 = rs.getString("Telephone2");
                String fax = rs.getString("Fax");
                String email = rs.getString("Email");
                String vatNumber = rs.getString("VatNumber");

                Outlet o = new Outlet(outletNumber, name1, address1, postalCode, city, telephone1, telephone2, fax, email, vatNumber);
                outlets.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            
            
        }

        return outlets;
    }

    public LinkedList<Sale> returnSales() {
        String sql = "select * from cchSales";
        LinkedList<Sale> sales = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String tipTransaction = rs.getString("TipTransaction");
                String invoiceNumber = rs.getString("InvoiceNumber");
                String outletNumber = rs.getString("OutletNumber");
                Date deliveryDate = new Date(rs.getDate("DeliveryDate").getTime());
                LinkedList<TransactionDetails> listTransactionDetails = returnTransactionDetails(invoiceNumber);

                Sale sale = new Sale(tipTransaction, invoiceNumber, outletNumber, deliveryDate, listTransactionDetails);
                sales.add(sale);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sales;
    }

    public LinkedList<TransactionDetails> returnTransactionDetails(String invoiceNumber) {
        String sql = "select * from cchTransactionDetails where InvoiceNumber='" + invoiceNumber + "'";
        LinkedList<TransactionDetails> tranDetails = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String tipTransaction = rs.getString("TipTransaction");
                String productNumber = rs.getString("ProductNumber");
                double quantity = rs.getDouble("Quantity");

                TransactionDetails td = new TransactionDetails(tipTransaction, invoiceNumber, productNumber, quantity);
                tranDetails.add(td);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tranDetails;
    }

    public LinkedList<Product> returnProducts() {
        String sql = "select * from cchProducts";
        LinkedList<Product> products = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String productNumber = rs.getString("ProductNumber");
                String productName = rs.getString("ProductName");
                String unitOfQuantity = rs.getString("UnitOfQuantity");
                String eanConsumerUnit = rs.getString("EanConsumerUnit");

                Product p = new Product(productNumber, productName, unitOfQuantity, eanConsumerUnit);
                products.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    public boolean clearDatabase() {
        boolean fb = false;
        fb = clearDatabase("delete cchZaglavlje");
        if (fb) {
            fb = clearDatabase("delete cchOutlets");
            if (fb) {
                fb = clearDatabase("delete cchSales");
                if (fb) {
                    fb = clearDatabase("delete cchTransactionDetails");
                    if (fb) {
                        fb = clearDatabase("delete cchProducts");
                    }
                }
            }
        }
        return fb;

    }

    private boolean clearDatabase(String sql) {
        boolean feedback = false;
        try {

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            feedback = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
        }
        return feedback;
    }

    public boolean saveLog(Log log) {
        boolean flag = false;
        try {
            String sql = "INSERT INTO cchLog (DateLog,ErrorLog,ResponseLog) VALUES (?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setTimestamp(1, new java.sql.Timestamp(log.getDateLog().getTime()));
            preparedStatement.setInt(2, log.getErrorLog());
            preparedStatement.setString(3, log.getResponseLog());

            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ef = new ErrorForm(ex.getMessage());
            JOptionPane.showMessageDialog(ef, ex.getMessage());
            flag = false;
        }
        return flag;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getNameDB() {
        return nameDB;
    }

    public void setNameDB(String nameDB) {
        this.nameDB = nameDB;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
