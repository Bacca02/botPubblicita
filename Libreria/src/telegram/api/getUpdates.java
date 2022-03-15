/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api;

import Osm.api.OsmAPI;
import Osm.api.attributi;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Utente
 */
public class getUpdates {

    public getUpdates(String key) {
        JSONArray arrayAtt;
        int updtate_id, message_id, id, id_chat, date;
        boolean is_bot;
        String first_name, language_code, first_name_chat, type, text;

        try {
            URL url = new URL("https://api.telegram.org/bot" + key + "/getUpdates");
            Scanner s = new Scanner(url.openStream());
            s.useDelimiter("\u001a");
            String jsonString = s.next();
            System.out.println(jsonString);
            JSONObject obj = new JSONObject(jsonString);

            arrayAtt = obj.getJSONArray("result");
            System.out.println("Elementi: " + arrayAtt.length());
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            if (arrayAtt.length() > 0) {
                for (int i = 0; i < arrayAtt.length(); i++) {
                    updtate_id = arrayAtt.getJSONObject(i).getInt("update_id");
                    message_id = arrayAtt.getJSONObject(i).getJSONObject("message").getInt("message_id");
                    id = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getInt("id");
                    is_bot = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getBoolean("is_bot");
                    first_name = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("first_name");
                    language_code = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("language_code");
                    id_chat = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getInt("id");
                    first_name_chat = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("first_name");
                    type = arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("type");
                    date = arrayAtt.getJSONObject(i).getJSONObject("message").getInt("date");
                    text = (String) arrayAtt.getJSONObject(i).getJSONObject("message").getString("text");

                    if (text.startsWith("/citta")) {
                        if (text.length() > 6) {
                            String tmpString = text.substring(7);
                            System.out.println(tmpString);
                            //controllo della presenza dell'utente
                    List<datiPersona> dati = this.leggiFile();
                           for (int j = 0; j < dati.size(); j++) {
                               if (dati.get(j).getId().equals(id)) {
                                    dati.add(new datiPersona(dati.get(j).getId(),dati.get(j).getNome(),tmpString));
                                    dati.remove(j);    
                                    //scrivo su file
                                    this.scriviListFile(dati);
                               }
                                 
                            System.out.println(dati.get(i));
                        }
                     
                       
                        
                            FileWriter myWriter = new FileWriter("salvaUtenti.csv", true);
                            //myWriter.append(updtate_id+";"+first_name+";"+tmpString+";"+"\n");
                            myWriter.write(id + ";" + first_name + ";" + tmpString + ";" + "\n");
                            myWriter.close();
                            OsmAPI osmObject = new OsmAPI();

                            url = new URL("https://api.telegram.org/bot" + key + "/sendMessage?chat_id=" + id_chat + "&text=" + osmObject.leggiXML(tmpString));
                            s = new Scanner(url.openStream());
                            s.next();
                        } else {
                            url = new URL("https://api.telegram.org/bot" + key + "/sendMessage?chat_id=" + id_chat + "&text=errore");
                            s = new Scanner(url.openStream());
                            s.next();
                        }

                        System.out.println("-----si-----");

                    } else {
                        System.out.println("-----no-----");
                        
    
                     
                        
                    }

                    System.out.println("updtate_id: " + updtate_id + "\n" + "message_id: " + message_id + "\n" + "id: " + id + "\n" + "is_bot: " + is_bot + "\n" + "first_name: " + first_name + "\n" + "language_code: " + language_code + "\n" + "id_chat: " + id_chat + "\n" + "first_name_chat: " + first_name_chat + "\n" + "date: " + date + "\n" + "text: " + text);
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                }

                int tmpID = arrayAtt.getJSONObject(arrayAtt.length() - 1).getInt("update_id") + 1;
                System.out.println("S:" + tmpID);

                url = new URL("https://api.telegram.org/bot" + key + "/getUpdates?offset=" + tmpID);
                s = new Scanner(url.openStream());
                s.next();

            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<datiPersona> leggiFile() throws FileNotFoundException, IOException {
        BufferedReader reader;
        List<datiPersona> n = new ArrayList<>();

        reader = new BufferedReader(new FileReader("salvaUtenti.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(";");
            n.add(new datiPersona(split[0], split[1], split[2]));
            System.out.println(split[0] + " " + split[1] + " " + split[2]);
           
        }

        reader.close();
        return n;
    }
    
    public void scriviListFile(List<datiPersona> l) {
        FileWriter myWriter;
        try {
             myWriter = new FileWriter("salvaUtenti.csv");
             myWriter.write("");
             myWriter.close();
        myWriter = new FileWriter("salvaUtenti.csv",true);
            for (int i = 0; i < l.size(); i++) {
            String file= l.toString();
            myWriter.write(file);
            
            }
       myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
 

    }

}
