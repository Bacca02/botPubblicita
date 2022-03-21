/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram;

import java.util.logging.Level;
import java.util.logging.Logger;
import static telegram.telegramMain.chiave;
import telegram.api.TelegramAPI;

/**
 *
 * @author Utente
 */
public class threadTelegram extends Thread{

    public threadTelegram() {
        
    }

    @Override
    public void run() {
        while (true) {            
           TelegramAPI a= new TelegramAPI(chiave);
           a.getUpdates(); 
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(threadTelegram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
