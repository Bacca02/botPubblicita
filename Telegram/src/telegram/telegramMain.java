/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram;

import Osm.api.OsmAPI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import telegram.api.TelegramAPI;
import telegram.api.datiPersona;
import Osm.api.attributi;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Utente
 */
public class telegramMain extends javax.swing.JFrame {

    /**
     * Creates new form telegramMain
     */
    public telegramMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Citta_TextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Citta_TextArea = new javax.swing.JTextArea();
        Invia_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Citta_TextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Città");

        Citta_TextArea.setColumns(20);
        Citta_TextArea.setRows(5);
        jScrollPane1.setViewportView(Citta_TextArea);

        Invia_button.setText("INVIA");
        Invia_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Invia_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Citta_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Invia_button))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Citta_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Invia_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Invia_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Invia_buttonActionPerformed
        if (Citta_TextField.getText().equals("") || Citta_TextArea.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Errore, inserisci città o testo");
        } else {
            try {
                OsmAPI leggi_coordinate = new OsmAPI();
                System.out.println(leggi_coordinate.leggiXML(Citta_TextField.getText()));
                List<datiPersona> dati = this.leggiFile();
                
                OsmAPI osm = new OsmAPI();
                attributi a = osm.leggiXML(Citta_TextField.getText());
                System.out.println("Size dati "+dati.size());
                for (int i = 0; i < dati.size(); i++) {
                    URL url;
                    Scanner s;
                    if (distanza(Double.parseDouble(dati.get(i).getLat()), Double.parseDouble(dati.get(i).getLon()),Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "K") <= 5) {
                    System.out.println("KM "+distanza(Double.parseDouble(dati.get(i).getLat()), Double.parseDouble(dati.get(i).getLon()),Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "K"));
                    System.out.println("CittaLon "+a.getLon());
                    System.out.println("CittaLat "+a.getLat());
                      System.out.println("CittaLonDati "+dati.get(i).getLon());
                    System.out.println("CittaLatDati "+dati.get(i).getLat());
                    System.out.println("*********************************");
                    //if (Citta_TextField.getText().equals(dati.get(i).getCitta())) {
                        url = new URL("https://api.telegram.org/bot" + chiave + "/sendMessage?chat_id=" + dati.get(i).getId() + "&text=" + Citta_TextArea.getText());
                        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                        System.out.println("Chat id "+dati.get(i).getId());
                        System.out.println("Testo "+Citta_TextArea.getText());
                        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                        s = new Scanner(url.openStream());
                        s.next();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(telegramMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(telegramMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(telegramMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            Citta_TextArea.setText("");
            Citta_TextField.setText("");
            
        }

    }//GEN-LAST:event_Invia_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static String chiave = "5203116920:AAHuP1bVt5Q0v4lZh62Np_qww2-4uxnstt8";
    
    public static void main(String args[]) {
        
        threadTelegram t = new threadTelegram();
        t.start();
        Telegram main = new Telegram();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telegramMain().setVisible(true);
            }
        });
    }
    
    public List<datiPersona> leggiFile() throws FileNotFoundException, IOException {
        BufferedReader reader;
        List<datiPersona> n = new ArrayList<>();
        
        reader = new BufferedReader(new FileReader("salvaUtenti.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(";");
            n.add(new datiPersona(split[0], split[1], split[2], split[3], split[4]));
        }
        
        reader.close();
        return n;
    }
    
    private double distanza(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Citta_TextArea;
    private javax.swing.JTextField Citta_TextField;
    private javax.swing.JButton Invia_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}