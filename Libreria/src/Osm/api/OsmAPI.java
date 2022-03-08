/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Osm.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 *
 * @author baccaglini_christian
 */
public class OsmAPI {

    public OsmAPI() {

    }

    public String leggiXML(String ricerca) throws IOException, SAXException, ParserConfigurationException {

        BufferedReader in = null;
        PrintWriter out;
        List<attributi> dati;

        try {
            out = new PrintWriter("dati.xml");

            //URL dell' XML
            String ricercaUrlEncoded = URLEncoder.encode(ricerca, StandardCharsets.UTF_8);
            URL url;
            try {
//                url = new URL(" https://nominatim.openstreetmap.org/search?q=" + ricercaUrlEncoded + "&format=xml&addressdetails=1");
                url = new URL("https://nominatim.openstreetmap.org/search?q=" + ricercaUrlEncoded + "&format=xml&addressdetails=1");

                Scanner s = new Scanner(url.openStream());
                s.useDelimiter("\u001a");
                String oggetto = s.next();
                System.out.println("OsmAPI: " + oggetto);

                in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = in.readLine()) != null) {
// System.out.println(line);
                    out.println(line);
                }
                in.close();
                out.close();
            } catch (MalformedURLException ex) {
                Logger.getLogger(OsmAPI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OsmAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OsmAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Salva XML
        MyXMLOperations xml = new MyXMLOperations();
        // String xsd = "xml/grammatica.xsd";
        String xmlFile = "dati.xml";

        dati = new ArrayList<>();
        dati = xml.parseDocument(xmlFile);

        for (int i = 0; i < dati.size(); i++) {
            System.out.println(dati.get(i).lat);
            System.out.println(dati.get(i).lon);
        }
        return (dati.get(0).lat)+";"+(dati.get(0).lon)+";";
    }
}
