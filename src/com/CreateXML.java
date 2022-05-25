/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import model.Header;
import model.Log;
import model.Outlet;
import model.Product;
import model.Sale;
import model.TransactionDetails;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ivona
 */
public class CreateXML {

    LinkedList<Header> headers;
    LinkedList<Outlet> listOutlets;
    LinkedList<Product> listProducts;
    LinkedList<Sale> listSales;
    Log log;

    public CreateXML() {
    }

    private void setEverything() {
        headers = Controller.getInstance().returnHeaders();
        listOutlets = Controller.getInstance().returnOutlets();
        listSales = Controller.getInstance().returnSales();
        listProducts = Controller.getInstance().returnProducts();
    }

    public void create() throws Exception {

        setEverything();

        if (headers == null || listOutlets == null || listProducts == null || listSales == null) {
            return;
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        try {
            //header
            Element element = document.createElement("Payload");
            document.appendChild(element);

            Attr attr = document.createAttribute("StructureVersion");
            attr.setValue(headers.get(0).getStructureVersion());
            element.setAttributeNode(attr);

            Attr attrID = document.createAttribute("WholesalerID");
            attrID.setValue(headers.get(0).getWholesalerID());
            element.setAttributeNode(attrID);

            Element period = document.createElement("Period");
            element.appendChild(period);

            Attr attrPeriodType = document.createAttribute("PeriodType");
            attrPeriodType.setValue(headers.get(0).getPeriodType());
            period.setAttributeNode(attrPeriodType);

            Attr attrDateTo = document.createAttribute("DateTo");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateTo = sdf.format(headers.get(0).getDateTo());
            attrDateTo.setValue(dateTo);
            period.setAttributeNode(attrDateTo);

            Attr attrDateFrom = document.createAttribute("DateFrom");
            String dateFrom = sdf.format(headers.get(0).getDateFrom());
            attrDateFrom.setValue(dateFrom);
            period.setAttributeNode(attrDateFrom);

            Attr attrTotalRecordsCount = document.createAttribute("TotalRecordsCount");
            attrTotalRecordsCount.setValue(String.valueOf(headers.get(0).getTotalRecordsCount()));
            period.setAttributeNode(attrTotalRecordsCount);

            Attr attrTotalVolume = document.createAttribute("TotalVolume");
            attrTotalVolume.setValue(String.valueOf(headers.get(0).getTotalVolume()));
            period.setAttributeNode(attrTotalVolume);

            //Outlets
            if (!listOutlets.isEmpty()) {
                Element outlets = document.createElement("Outlets");
                period.appendChild(outlets);

                for (Outlet outlet : listOutlets) {

                    Element outletEntry = document.createElement("OutletEntry");
                    outlets.appendChild(outletEntry);

                    Element deliverTo = document.createElement("DeliverTo");
                    outletEntry.appendChild(deliverTo);

                    makeOutletEntry(deliverTo, document, outlet);

                    /*Element billTo = document.createElement("BillTo");
                    outletEntry.appendChild(billTo);

                    makeOutletEntry(billTo, document, outlet);*/
                }

            } else {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);

                log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
                Controller.getInstance().saveLog(log);
                return;
            }
            //Sales
            if (!listSales.isEmpty()) {

                Element sales = document.createElement("Sales");
                period.appendChild(sales);

                Attr attrTransactionType = document.createAttribute("TransactionType");
                attrTransactionType.setValue("Sales");
                sales.setAttributeNode(attrTransactionType);

                for (Sale sale : listSales) {
                    makeSales(document, sales, sale);
                }
            } else {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);

                log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
                Controller.getInstance().saveLog(log);
                return;
            }
            //Products
            if (!listProducts.isEmpty()) {
                Element products = document.createElement("Products");
                period.appendChild(products);
                for (Product product : listProducts) {
                    makeProducts(document, products, product);
                }
            } else {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);

                log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
                Controller.getInstance().saveLog(log);
                return;
            }

        } catch (Exception e) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, e);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);

            log = new Log(date, "Nepravilni podaci u cch tabelama", -3);
            Controller.getInstance().saveLog(log);
            return;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        boolean feedback = validateXML(source);
        if (!feedback) {
            Controller.getInstance().saveLog(log);
        } else {
            StreamResult streamResult = new StreamResult(new File("C:\\InSoft\\data.xml"));
            transformer.transform(source, streamResult);
            /*Cleaner cl = new Cleaner();
            cl.clearDB();
            Controller.getInstance().saveLog(log);*/
            /*XMLHttpClient hc = new XMLHttpClient();
            hc.httpRequest();*/
            /*HttpRequest hc = new HttpRequest();
            hc.request();*/
            /*HttpRequestFinal hrf = new HttpRequestFinal();
            hrf.httpRequest();*/
        }

    }

    private void makeOutletEntry(Element deliverTo, Document document, Outlet outlet) {
        Element outletNumber = document.createElement("OutletNumber");
        outletNumber.appendChild(document.createTextNode(outlet.getOutletNumber()));
        deliverTo.appendChild(outletNumber);

        Element name1 = document.createElement("Name1");
        name1.appendChild(document.createTextNode(outlet.getName1()));
        deliverTo.appendChild(name1);
        //non-mandatory
        /*Element name2 = document.createElement("Name2");
        name2.appendChild(document.createTextNode(""));
        deliverTo.appendChild(name2);
        //non-mandatory
        Element contactPerson = document.createElement("ContactPerson");
        contactPerson.appendChild(document.createTextNode(""));
        deliverTo.appendChild(contactPerson);*/

        Element address1 = document.createElement("Address1");
        address1.appendChild(document.createTextNode(outlet.getAddress1()));
        deliverTo.appendChild(address1);
        //non-mandatory
        /*Element address2 = document.createElement("Address2");
        address2.appendChild(document.createTextNode(""));
        deliverTo.appendChild(address2);*/

        Element postalCode = document.createElement("PostalCode");
        postalCode.appendChild(document.createTextNode(outlet.getPostalCode()));
        deliverTo.appendChild(postalCode);

        Element city = document.createElement("City");
        city.appendChild(document.createTextNode(outlet.getCity()));
        deliverTo.appendChild(city);
        //non-mandatory
        Element telephone1 = document.createElement("Telephone1");
        if (outlet.getTelephone1() != null) {
            telephone1.appendChild(document.createTextNode(outlet.getTelephone1()));
        }
        deliverTo.appendChild(telephone1);
        //non-mandatory
        Element telephone2 = document.createElement("Telephone2");
        if (outlet.getTelephone2() != null) {
            telephone2.appendChild(document.createTextNode(outlet.getTelephone2()));
        }
        deliverTo.appendChild(telephone2);
        //non-mandatory
        Element fax = document.createElement("Fax");
        if (outlet.getFax() != null) {
            fax.appendChild(document.createTextNode(outlet.getFax()));
        }
        deliverTo.appendChild(fax);
        //non-mandatory
        Element email = document.createElement("Email");
        if (outlet.getFax() != null) {
            email.appendChild(document.createTextNode(outlet.getEmail()));
        }
        deliverTo.appendChild(email);
        //non-mandatory
        Element vatNumber = document.createElement("VatNumber");
        if (outlet.getFax() != null) {
            vatNumber.appendChild(document.createTextNode(outlet.getVatNumber()));
        }
        deliverTo.appendChild(vatNumber);
        //non-mandatory
        /*Element keyAccount = document.createElement("KeyAccount");
        keyAccount.appendChild(document.createTextNode(""));
        deliverTo.appendChild(keyAccount);
        //non-mandatory
        Element channel = document.createElement("Channel");
        channel.appendChild(document.createTextNode(""));
        deliverTo.appendChild(channel);

        Element outletNumberHbo = document.createElement("OutletNumberHbc");
        outletNumberHbo.appendChild(document.createTextNode(""));
        deliverTo.appendChild(outletNumberHbo);*/
    }

    private void makeSales(Document document, Element sales, Sale sale) {
        if (sale.getTipTransaction().equals("T")) {
            Element transaction = document.createElement("Transaction");
            sales.appendChild(transaction);

            Element outletNumber = document.createElement("OutletNumber");
            outletNumber.appendChild(document.createTextNode(sale.getOutletnumber()));
            transaction.appendChild(outletNumber);

            Element deliveryDate = document.createElement("DeliveryDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dDate = sdf.format(sale.getDeliveryDate());
            deliveryDate.appendChild(document.createTextNode(dDate));
            transaction.appendChild(deliveryDate);
            //non-mandatory
            /*Element orderNumberHbc = document.createElement("OrderNumberHbc");
            orderNumberHbc.appendChild(document.createTextNode(""));
            transaction.appendChild(orderNumberHbc);*/

            Element invoiceNumber = document.createElement("InvoiceNumber");
            invoiceNumber.appendChild(document.createTextNode(sale.getInvoiceNumber()));
            transaction.appendChild(invoiceNumber);

            for (TransactionDetails transactionDetail : sale.getListTransactionDetails()) {
                makeTransactionDetails(document, transaction, transactionDetail);
            }
        }
        if (sale.getTipTransaction().equals("C")) {
            Element canceledTransaction = document.createElement("CanceledTransaction");
            sales.appendChild(canceledTransaction);

            Element invoiceNumber1 = document.createElement("InvoiceNumber");
            invoiceNumber1.appendChild(document.createTextNode(sale.getInvoiceNumber()));
            canceledTransaction.appendChild(invoiceNumber1);

            Element outletNumber1 = document.createElement("OutletNumber");
            outletNumber1.appendChild(document.createTextNode(sale.getOutletnumber()));
            canceledTransaction.appendChild(outletNumber1);
        }

    }

    private void makeTransactionDetails(Document document, Element transaction, TransactionDetails transactionDetail) {
        Element transactionDetails = document.createElement("TransactionDetails");
        transaction.appendChild(transactionDetails);

        Element productNumber = document.createElement("ProductNumber");
        productNumber.appendChild(document.createTextNode(transactionDetail.getProductNumber()));
        transactionDetails.appendChild(productNumber);

        Element quantity = document.createElement("Quantity");
        quantity.appendChild(document.createTextNode(String.valueOf(transactionDetail.getQuantity())));
        transactionDetails.appendChild(quantity);

        /*Element price = document.createElement("Price");
        price.appendChild(document.createTextNode("765.72"));
        transactionDetails.appendChild(price);*/
    }

    private void makeProducts(Document document, Element products, Product product) {
        Element productEntry = document.createElement("ProductEntry");
        products.appendChild(productEntry);

        Element productNumber = document.createElement("ProductNumber");
        productNumber.appendChild(document.createTextNode(product.getProductNumber()));
        productEntry.appendChild(productNumber);

        Element productName = document.createElement("ProductName");
        productName.appendChild(document.createTextNode(product.getProductName()));
        productEntry.appendChild(productName);

        Element unitOfQuantity = document.createElement("UnitOfQuantity");
        unitOfQuantity.appendChild(document.createTextNode(product.getUnitOfQuantity().trim()));
        productEntry.appendChild(unitOfQuantity);

        /*Element articleNameHbc = document.createElement("ArticleNameHbc");
        articleNameHbc.appendChild(document.createTextNode("500 PET X12 CAPPY TE"));
        productEntry.appendChild(articleNameHbc);

        Element articleNumberHbc = document.createElement("ArticleNumberHbc");
        articleNumberHbc.appendChild(document.createTextNode("1058703"));
        productEntry.appendChild(articleNumberHbc);*/
        Element eanConsumerUnit = document.createElement("EanConsumerUnit");
        eanConsumerUnit.appendChild(document.createTextNode(product.getEanConsumerUnit()));
        productEntry.appendChild(eanConsumerUnit);
        //non-mandatory
        /*Element eanMultipack = document.createElement("EanMultipack");
        eanMultipack.appendChild(document.createTextNode(""));
        productEntry.appendChild(eanMultipack);
        //non-mandatory
        Element eanTradeUnit = document.createElement("EanTradeUnit");
        eanTradeUnit.appendChild(document.createTextNode(""));
        productEntry.appendChild(eanTradeUnit);
        //non-mandatory
        Element productRemarks = document.createElement("ProductRemarks");
        productRemarks.appendChild(document.createTextNode(""));
        productEntry.appendChild(productRemarks);
        //non-mandatory
        Element purchasePrice = document.createElement("PurchasePrice");
        purchasePrice.appendChild(document.createTextNode(""));
        productEntry.appendChild(purchasePrice);

        Element packageSizeLitres = document.createElement("PackageSizeLitres");
        packageSizeLitres.appendChild(document.createTextNode("6"));
        productEntry.appendChild(packageSizeLitres);

        Element salesUnit = document.createElement("SalesUnit");
        salesUnit.appendChild(document.createTextNode("1"));
        productEntry.appendChild(salesUnit);

        Element packageType = document.createElement("PackageType");
        packageType.appendChild(document.createTextNode("PET"));
        productEntry.appendChild(packageType);

        Element subunits = document.createElement("Subunits");
        subunits.appendChild(document.createTextNode("12"));
        productEntry.appendChild(subunits);*/
    }

    private boolean validateXML(DOMSource source) throws Exception {
        URL schemaFile = new URL("http://cch.icd.rs/documentation/documents/wsdata_v3.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        boolean flag = false;
        Validator validator = schema.newValidator();
        DOMResult result = new DOMResult();
        try {
            validator.validate(source, result);
            System.out.println("XML file is valid");

            long millis = System.currentTimeMillis();
            Date date = new Date(millis);

            log = new Log(date, "No Error", 200);
            flag = true;
        } catch (SAXException e) {
            System.out.println("XML file is not valid because " + e.getLocalizedMessage());

            long millis = System.currentTimeMillis();
            Date date = new Date(millis);

            log = new Log(date, "XML file is not valid because " + e.getLocalizedMessage(), -5);
            flag = false;
        }
        return flag;
    }
    
    
}
