/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api;

import java.io.IOException;
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

    public getUpdates(String string) {
        JSONArray arrayAtt;
        try {
            URL url = new URL(string);
            Scanner s = new Scanner(url.openStream());
            s.useDelimiter("\u001a");
            String jsonString = s.next();
            System.out.println(jsonString);
            System.out.println("/////////////////////////////////");
            JSONObject obj = new JSONObject(jsonString);
          
            arrayAtt = obj.getJSONArray("result");
            System.out.println("Elementi: "+arrayAtt.length());
            
            for (int i = 0; i < arrayAtt.length(); i++) {
                int updtate_id =  arrayAtt.getJSONObject(i).getInt("update_id");
                int message_id =arrayAtt.getJSONObject(i).getJSONObject("message").getInt("message_id");
                    int id=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getInt("id");
                      boolean is_bot=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getBoolean("is_bot");
                      String  first_name=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("first_name");
                      String language_code=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("language_code");
                      
                int id_chat=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getInt("id");
                String first_name_chat=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("first_name");          
                String type=arrayAtt.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("type");          
                
                int date = arrayAtt.getJSONObject(i).getJSONObject("message").getInt("date");
                String text = (String) arrayAtt.getJSONObject(i).getJSONObject("message").getString("text");
                System.out.println("updtate_id: "+updtate_id +"\n"+"message_id: "+message_id +"\n"+"id: "+id +"\n"+"is_bot: "+is_bot +"\n"+"first_name: "+first_name +"\n"+"language_code: "+language_code +"\n"+"id_chat: "+id_chat +"\n"+"first_name_chat: "+first_name_chat +"\n"+"date: "+date +"\n"+"text: "+text);
                System.out.println("|---------------------------|");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
