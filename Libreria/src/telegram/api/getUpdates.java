/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
                            FileWriter myWriter = new FileWriter("salvaUtenti.csv", true);
                            //myWriter.append(updtate_id+";"+first_name+";"+tmpString+";"+"\n");
                            myWriter.write(updtate_id + ";" + first_name + ";" + tmpString + ";" + "\n");
                            myWriter.close();

                        }

                        System.out.println("si");
                        
                        url = new URL("https://api.telegram.org/bot" + key + "/sendMessage?chat_id=" + id_chat + "&text=coordinate");
                        s = new Scanner(url.openStream());
                        s.next();

                    } else {
                        System.out.println("no");
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
        } catch (IOException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
