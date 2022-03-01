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
import org.json.*;

/**
 *
 * @author baccaglini_christian
 */
public class TelegramAPI {

    public String url;
    public TelegramAPI(String key) {
        JSONArray arrayAtt = null;
        url = "https://api.telegram.org/bot" + key;
    }

    public void getUpdates() {
        url+="/getUpdates";
        new getUpdates(url);
    }
}
