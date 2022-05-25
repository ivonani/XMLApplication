/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import sun.net.www.http.HttpClient;

/**
 *
 * @author ivona
 */
public class XMLHttpClient {

    public void httpRequest() throws IOException {

        String token = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjRhYjAxM2JiMmVjYmM4ZjRjOGM1MjQ3ODU2MDc0Yzg1ZGQ2ZDE4NjM5ZGRlN2ViZmU5MGVmOTY2MjY5ZDc2NTk4MjRiNTY5ZTljYjFiMTU2In0.eyJhdWQiOiIxIiwianRpIjoiNGFiMDEzYmIyZWNiYzhmNGM4YzUyNDc4NTYwNzRjODVkZDZkMTg2MzlkZGU3ZWJmZTkwZWY5NjYyNjlkNzY1OTgyNGI1NjllOWNiMWIxNTYiLCJpYXQiOjE1ODAxMTE0NzUsIm5iZiI6MTU4MDExMTQ3NSwiZXhwIjo0NzM1Nzg1MDc1LCJzdWIiOiI0MiIsInNjb3BlcyI6W119.DlXJgPp9W9knroRa01yyAFF5wKaQgRa4lJmvxzFRrgSiaBXeHJusQoAkLxBf6uz4L4YCzCUD7K3MDCI1m9nzxo0IOktug9LsuV8i3CVxgubTHMT3X1yqxK1LdDjwuFX68jUzbOomO5-itt_r5d0MvCr2ans3a8tEiF57bHdcnIa45AxaHiq8t1qz3lw8LzqCFlWa3Db6uFqqm46eArD2bJ1gagyV9TVA4VbaMeYUOOh5VFqoof-M5n_qLqy0nZLs3GMFzRt8Sz0lDv9-QfV8QYwHgEm3P2WyeQx8L9txjcm-dq7wlb0O7beqpJtWQpTNsbwraYldzSFGozsgurvVv05XUGDhGGYFvXRPobcgZOiknGTwDPuWQIdKYyzGdBPn9-GbKDDGZbcgqE1ojfo1-JoVCQBXhz-T-2ID4Ka-yYqFerCUhQ3NChZiGgJpzs0vdWdGku8ohr_biJye0wFI5LlNnIqmlMmIbaWdMpqTP2k7mv02Y-iC8pIGYFkC3M-OS_Pcmq97AShOJHDx8u4pU5vSlHfaCDXOuA7dOgJH1AmiTvU52QIhcC8zpZu4RveSmLY0qdxwApVMbE3jwnwlnSIOckgD7Gg0ybyAP9jj2FtRUggrExMSp2G2oTKsSgjqKn25_Y4ra00ydqAPUk05KY1vBb9wTqkNRllQVqsM8TM";
        Log log;
        try {
            sendPOSTRequest("http://cch.icd.rs/api/v3/document", token, "C:\\\\InSoft\\\\data.xml");
        } catch (Exception ex) {
            Logger.getLogger(XMLHttpClient.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nemoguć pristup servisu", -4);
            Controller.getInstance().saveLog(log);
            return;
        }
        /*String request = "";
        File xmlFile = new File("C:\\InSoft\\data.xml");
        String boundary = "*****";
        String crlf = "\r\n";
        try {
            request = readXML(xmlFile);
        } catch (Exception e) {
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Unable to read created XML", -6);
            Controller.getInstance().saveLog(log);
            return;
        }
        HttpURLConnection connection = null;
        Cleaner cl = new Cleaner();
        try {
            URL url = new URL("http://cch.icd.rs/api/v3/document");
            connection = (HttpURLConnection) url.openConnection();
            // Set timeout as per needs
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setDoOutput(true);
            //connection.setUseCaches(true);
            connection.setRequestMethod("POST");
            // Set Headers
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjRhYjAxM2JiMmVjYmM4ZjRjOGM1MjQ3ODU2MDc0Yzg1ZGQ2ZDE4NjM5ZGRlN2ViZmU5MGVmOTY2MjY5ZDc2NTk4MjRiNTY5ZTljYjFiMTU2In0.eyJhdWQiOiIxIiwianRpIjoiNGFiMDEzYmIyZWNiYzhmNGM4YzUyNDc4NTYwNzRjODVkZDZkMTg2MzlkZGU3ZWJmZTkwZWY5NjYyNjlkNzY1OTgyNGI1NjllOWNiMWIxNTYiLCJpYXQiOjE1ODAxMTE0NzUsIm5iZiI6MTU4MDExMTQ3NSwiZXhwIjo0NzM1Nzg1MDc1LCJzdWIiOiI0MiIsInNjb3BlcyI6W119.DlXJgPp9W9knroRa01yyAFF5wKaQgRa4lJmvxzFRrgSiaBXeHJusQoAkLxBf6uz4L4YCzCUD7K3MDCI1m9nzxo0IOktug9LsuV8i3CVxgubTHMT3X1yqxK1LdDjwuFX68jUzbOomO5-itt_r5d0MvCr2ans3a8tEiF57bHdcnIa45AxaHiq8t1qz3lw8LzqCFlWa3Db6uFqqm46eArD2bJ1gagyV9TVA4VbaMeYUOOh5VFqoof-M5n_qLqy0nZLs3GMFzRt8Sz0lDv9-QfV8QYwHgEm3P2WyeQx8L9txjcm-dq7wlb0O7beqpJtWQpTNsbwraYldzSFGozsgurvVv05XUGDhGGYFvXRPobcgZOiknGTwDPuWQIdKYyzGdBPn9-GbKDDGZbcgqE1ojfo1-JoVCQBXhz-T-2ID4Ka-yYqFerCUhQ3NChZiGgJpzs0vdWdGku8ohr_biJye0wFI5LlNnIqmlMmIbaWdMpqTP2k7mv02Y-iC8pIGYFkC3M-OS_Pcmq97AShOJHDx8u4pU5vSlHfaCDXOuA7dOgJH1AmiTvU52QIhcC8zpZu4RveSmLY0qdxwApVMbE3jwnwlnSIOckgD7Gg0ybyAP9jj2FtRUggrExMSp2G2oTKsSgjqKn25_Y4ra00ydqAPUk05KY1vBb9wTqkNRllQVqsM8TM");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Disposition", "form-data; name=\"file\"; filename=\"" + xmlFile.getName() + "\"" + crlf);

        } catch (Exception e) {
            Logger.getLogger(XMLHttpClient.class.getName()).log(Level.SEVERE, null, e);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nemoguć pristup servisu", -4);
            Controller.getInstance().saveLog(log);
            return;
        }
        // Write XML
        try {
            OutputStream outputStream = connection.getOutputStream();
            byte[] b = request.getBytes("UTF-8");
            outputStream.write(b, 0, b.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Logger.getLogger(XMLHttpClient.class.getName()).log(Level.SEVERE, null, e);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nemoguće slanje XML-a", -4);
            Controller.getInstance().saveLog(log);
            return;
        }

        StringBuffer response = null;
        try {
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            Logger.getLogger(XMLHttpClient.class.getName()).log(Level.SEVERE, null, e);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Nemoguće citanje odgovora od servera", -4);
            Controller.getInstance().saveLog(log);
            return;
        }

        System.out.println(response.toString());

        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(response.toString());

        } catch (ParseException ex) {
            Logger.getLogger(XMLHttpClient.class.getName()).log(Level.SEVERE, null, ex);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "Couldn't parse String to json", -6);
            Controller.getInstance().saveLog(log);
            return;
        }

        String message = (String) json.get("message");
        int status_code = Integer.parseInt((String) json.get("status_code"));
        //Cleaner cl = new Cleaner();
        if (status_code == 200) {
            cl.clearDB();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, "No Error", status_code);
        } else {
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            log = new Log(date, message, status_code);
        }
        Controller.getInstance().saveLog(log);*/
    }

    private String readXML(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        String xmlDoc = "";
        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            xmlDoc = toString(document);
            return xmlDoc;
        } catch (ParserConfigurationException ex) {
            throw new ParserConfigurationException(ex.getMessage());
        } catch (SAXException ex) {
            throw new SAXException(ex.getMessage());
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }

    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public void sendPOSTRequest(String url, String authData, String attachmentFilePath) throws Exception {
        String charset = "UTF-8";
        File binaryFile = new File(attachmentFilePath);
        String boundary = "------------------------" + Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        int responseCode = 0;
        String message = null;
        Log log = null;
        try {
            //Set POST general headers along with the boundary string (the seperator string of each part)
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.addRequestProperty("User-Agent", "CheckpaySrv/1.0.0");
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Authentication", authData);

            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

            // Send binary file - part
            // Part header
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: application/octet-stream").append(CRLF);// + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append(CRLF).flush();

            // File data
            Files.copy(binaryFile.toPath(), output);
            output.flush();

            // End of multipart/form-data.
            writer.append(CRLF).append("--" + boundary + "--").flush();

            responseCode = ((HttpURLConnection) connection).getResponseCode();
            message = ((HttpURLConnection) connection).getResponseMessage();
            
            System.out.println(responseCode + "");
            System.out.println(message);
            System.out.println(((HttpURLConnection) connection));
            
            Cleaner cl = new Cleaner();
            if (responseCode == 200) {
                //cl.clearDB();
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                log = new Log(date, "No Error", responseCode);
            } else {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                log = new Log(date, message, responseCode);
            }
            Controller.getInstance().saveLog(log);
            
        } catch (Exception e) {
           throw new Exception();
        }
    }

}
