/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram;

//import telegram.api.TelegramAPI;

import java.io.IOException;
import javax.swing.JFrame;
import org.xml.sax.SAXException;
import telegram.api.TelegramAPI;
/**
 *
 * @author Utente
 */
public class Telegram{

  public static String chiave = "5203116920:AAHuP1bVt5Q0v4lZh62Np_qww2-4uxnstt8";
    public static void main(String[] args) throws IOException, SAXException{
       threadTelegram t= new threadTelegram();
       t.start();
    }
}
