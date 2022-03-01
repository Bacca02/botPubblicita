/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram;

import telegram.api.TelegramAPI;

/**
 *
 * @author Utente
 */
public class Telegram {

  public static String chiave = "";
    public static void main(String[] args) {
        TelegramAPI a= new TelegramAPI(chiave);
        a.getUpdates();       
    }
    
}
