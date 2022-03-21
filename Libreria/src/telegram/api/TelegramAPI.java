/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api;
import org.json.*;

/**
 *
 * @author baccaglini_christian
 */

public class TelegramAPI {

    String key;

    public TelegramAPI(String key) {
        //JSONArray arrayAtt = null;
        this.key = key;
    }

    public TelegramAPI() {
    }
    

    public void getUpdates() {
        new getUpdates(key);
    }

}
