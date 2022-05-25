/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import static com.XMLHttpClient.toString;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ivona
 */
public class HttpRequest {

    String attachmentName = "data";
    File xmlFile = new File("C:\\InSoft\\data.xml");
    String attachmentFileName = xmlFile.getName();
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String token = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjRhYjAxM2JiMmVjYmM4ZjRjOGM1MjQ3ODU2MDc0Yzg1ZGQ2ZDE4NjM5ZGRlN2ViZmU5MGVmOTY2MjY5ZDc2NTk4MjRiNTY5ZTljYjFiMTU2In0.eyJhdWQiOiIxIiwianRpIjoiNGFiMDEzYmIyZWNiYzhmNGM4YzUyNDc4NTYwNzRjODVkZDZkMTg2MzlkZGU3ZWJmZTkwZWY5NjYyNjlkNzY1OTgyNGI1NjllOWNiMWIxNTYiLCJpYXQiOjE1ODAxMTE0NzUsIm5iZiI6MTU4MDExMTQ3NSwiZXhwIjo0NzM1Nzg1MDc1LCJzdWIiOiI0MiIsInNjb3BlcyI6W119.DlXJgPp9W9knroRa01yyAFF5wKaQgRa4lJmvxzFRrgSiaBXeHJusQoAkLxBf6uz4L4YCzCUD7K3MDCI1m9nzxo0IOktug9LsuV8i3CVxgubTHMT3X1yqxK1LdDjwuFX68jUzbOomO5-itt_r5d0MvCr2ans3a8tEiF57bHdcnIa45AxaHiq8t1qz3lw8LzqCFlWa3Db6uFqqm46eArD2bJ1gagyV9TVA4VbaMeYUOOh5VFqoof-M5n_qLqy0nZLs3GMFzRt8Sz0lDv9-QfV8QYwHgEm3P2WyeQx8L9txjcm-dq7wlb0O7beqpJtWQpTNsbwraYldzSFGozsgurvVv05XUGDhGGYFvXRPobcgZOiknGTwDPuWQIdKYyzGdBPn9-GbKDDGZbcgqE1ojfo1-JoVCQBXhz-T-2ID4Ka-yYqFerCUhQ3NChZiGgJpzs0vdWdGku8ohr_biJye0wFI5LlNnIqmlMmIbaWdMpqTP2k7mv02Y-iC8pIGYFkC3M-OS_Pcmq97AShOJHDx8u4pU5vSlHfaCDXOuA7dOgJH1AmiTvU52QIhcC8zpZu4RveSmLY0qdxwApVMbE3jwnwlnSIOckgD7Gg0ybyAP9jj2FtRUggrExMSp2G2oTKsSgjqKn25_Y4ra00ydqAPUk05KY1vBb9wTqkNRllQVqsM8TM";

    public void request() {
        try {

            HttpURLConnection httpUrlConnection = null;
            URL url = new URL("http://cch.icd.rs/api/v3/document");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + this.boundary);
            httpUrlConnection.addRequestProperty("Accept", "application/json");
            httpUrlConnection.addRequestProperty("Authentication", token);

            DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());

            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
            //writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + binaryFile.getName() + "\"").append(crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + this.attachmentFileName + "\"" + this.crlf);
            request.writeBytes(this.crlf);

            String xml = readXML();
            byte[] b = xml.getBytes("UTF-8");
            request.write(b);

            request.writeBytes(this.crlf);
            request.writeBytes(this.twoHyphens + this.boundary
                    + this.twoHyphens + this.crlf);

            request.flush();
            request.close();
            //read response
            InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader
                    = new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            String response = stringBuilder.toString();
            System.out.println(response);
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readXML() throws ParserConfigurationException, SAXException, IOException {
        String xmlDoc = "";
        try {

            File xmlFile = new File("C:\\InSoft\\data.xml");

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

}
