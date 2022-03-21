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
            // System.out.println(jsonString);
            JSONObject obj = new JSONObject(jsonString);

            arrayAtt = obj.getJSONArray("result");
            // System.out.println("Elementi: " + arrayAtt.length());
            //System.out.println("---------------------------------------------------------------------------------------------------------------");

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
                            // System.out.println(tmpString);
                            //controllo della presenza dell'utente
                            boolean trovato = false;
                            List<datiPersona> dati = this.leggiFile();
                            if (dati.size() > 0) {
                                for (int j = 0; j < dati.size(); j++) {
                                    System.out.println("Prova-------------" + dati.get(j).getId() + " " + dati.get(j).getNome() + " " + dati.get(j).getCitta() + " " + dati.get(j).getLat() + " " + dati.get(j).getLon());
                                    //Controllo che l'ID alla pos j del file sia già presente
                                    System.out.println("Dati getId " + dati.get(j).getId());
                                    //System.out.println("Dati id "+id.);

                                    if (dati.get(j).getId().equals(id + "")) {
                                        //Persona trovata
                                        trovato = true;
                                        System.out.println("Dati 1 " + dati.size());
                                        //Aggiungo alla lista dati un nuovo elemento
                                        OsmAPI osm = new OsmAPI();
                                        attributi a = osm.leggiXML(tmpString);
                                        dati.add(new datiPersona(dati.get(j).getId(), dati.get(j).getNome(), tmpString, a.getLat(), a.getLon()));
                                        System.out.println("Dati 2 " + dati.size());
                                        //Rimuovo dalla lista l'elemento doppio
                                        dati.remove(j);
                                        System.out.println("Dati 3 " + dati.size());
                                        //scrivo la nuova lista su file
                                        this.resettaFile();
                                        this.scriviListFile(dati);
                                        //dati.size elementi-1
                                        for (int k = 0; k < dati.size(); k++) {
                                            System.out.println("GetID " + dati.get(j).getId());
                                            System.out.println("GetNome " + dati.get(j).getNome());
                                            System.out.println("GetCitta " + dati.get(j).getCitta());
                                            System.out.println("GetCitta " + dati.get(j).getCitta());
                                            //System.out.println("Prova2-------------" + dati.get(k).getId() + " " + dati.get(k).getNome() + " " + dati.get(k).getCitta());

                                        }

                                    }

                                    // System.out.println(dati.get(i));
                                }
                                if (!trovato) {
                                    FileWriter myWriter = new FileWriter("salvaUtenti.csv", true);
                                    //myWriter.append(updtate_id+";"+first_name+";"+tmpString+";"+"\n");
                                    OsmAPI osm = new OsmAPI();
                                    attributi a = osm.leggiXML(tmpString);
                                    myWriter.write(id + ";" + first_name + ";" + tmpString + ";" + a.getLat() + ";" + a.getLon() + ";" + "\n");
                                    myWriter.close();

                                }
                            } else {
                                FileWriter myWriter = new FileWriter("salvaUtenti.csv", true);
                                //myWriter.append(updtate_id+";"+first_name+";"+tmpString+";"+"\n");
                                OsmAPI osm = new OsmAPI();
                                attributi a = osm.leggiXML(tmpString);
                                myWriter.write(id + ";" + first_name + ";" + tmpString + ";" + a.getLat() + ";" + a.getLon() + ";" + "\n");
                                myWriter.close();
                            }

                            OsmAPI osmObject = new OsmAPI();
                            attributi a = osmObject.leggiXML(tmpString);

                            url = new URL("https://api.telegram.org/bot" + key + "/sendMessage?chat_id=" + id_chat + "&text=" + a.getLat() + " " + a.getLon());
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

                    //  System.out.println("updtate_id: " + updtate_id + "\n" + "message_id: " + message_id + "\n" + "id: " + id + "\n" + "is_bot: " + is_bot + "\n" + "first_name: " + first_name + "\n" + "language_code: " + language_code + "\n" + "id_chat: " + id_chat + "\n" + "first_name_chat: " + first_name_chat + "\n" + "date: " + date + "\n" + "text: " + text);
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                }

                int tmpID = arrayAtt.getJSONObject(arrayAtt.length() - 1).getInt("update_id") + 1;
                //  System.out.println("S:" + tmpID);

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
            n.add(new datiPersona(split[0], split[1], split[2], split[3], split[4]));
            //  System.out.println(split[0] + " " + split[1] + " " + split[2]);

        }

        reader.close();
        return n;
    }

    public void resettaFile() {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("salvaUtenti.csv");
            myWriter.write("");
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void scriviListFile(List<datiPersona> l) {
        System.out.println("salvato..................................................");
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("salvaUtenti.csv", true);
            for (int i = 0; i < l.size(); i++) {
                String file = l.get(i).datiToString();
                myWriter.write(file);

            }
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
