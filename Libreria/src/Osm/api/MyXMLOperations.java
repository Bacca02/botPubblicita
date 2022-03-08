/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Osm.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Utente
 */
public class MyXMLOperations {

    private Document document;

    public Document getDocument() {
        return document;
    }
//https://nominatim.openstreetmap.org/search?q=nome citta&format=xml&addressdetails=1
    public void validate(String XMLdocument, String XSDschema) throws SAXException, IOException {
        // creazione di uno schema XSD a partire dal file
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaFile = new File(XSDschema);
        Schema schema = factory.newSchema(schemaFile);
        // creazione di un validatore rispetto allo schema XSD
        Validator validator = schema.newValidator();
        // validazione del documento XML
        Source source = new StreamSource(XMLdocument);
        validator.validate(source);
    }

    public List parseDocument(String filename)
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Element root, element;
        NodeList nodelist;
        // creazione dell’albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        document = builder.parse(filename);
        root = document.getDocumentElement();
        List<attributi> dati = new ArrayList();
        attributi dato;

        nodelist = root.getElementsByTagName("place");

        if (nodelist != null && nodelist.getLength() > 0) {
            int numNode = nodelist.getLength();

            //for (int j = 0; j < numNode; j++) {

                element = (Element) nodelist.item(0);
//                System.out.println("Numero di figli: "+element.getChildNodes().getLength());
                int numeroAttributi = nodelist.item(0).getAttributes().getLength();
 //               System.out.println("Numero Child " + j + " " + numeroChild);
                dato = getInfo(element, numeroAttributi);
                dati.add(dato);
           // }

        }

        return dati;
    }

    private attributi getInfo(Element element, int numeroAttributi) {

        attributi valute = null;
        String lat = null;
        String lon = null;
        lat= element.getAttribute("lat");
        lon= element.getAttribute("lon");
        System.out.println("Lat: "+lat+" Lon: "+lon);
        valute= new attributi(lat,lon);
        return valute;
    }

    // restituisce il valore testuale dell’elemento figlio specificato
    private String getTextValue(Element element, String tag) {
        String value = null;
        NodeList nodelist;
        nodelist = element.getElementsByTagName(tag);
        if (nodelist != null && nodelist.getLength() > 0) {
            value = nodelist.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }

    // restituisce il valore intero dell’elemento figlio specificato
    private int getIntValue(Element element, String tag) {
        return Integer.parseInt(getTextValue(element, tag));
    }

    // restituisce il valore numerico dell’elemento figlio specificato
    private float getFloatValue(Element element, String tag) {
        return Float.parseFloat(getTextValue(element, tag));
    }

    // restituisce il valore numerico dell’elemento figlio specificato
    private double getDoubleValue(Element element, String tag) {
        return Double.parseDouble(getTextValue(element, tag));
    }

}