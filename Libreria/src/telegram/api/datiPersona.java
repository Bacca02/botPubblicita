/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api;

/**
 *
 * @author baccaglini_christian
 */
public class datiPersona {
    String id,nome,citta,lat,lon;

    public datiPersona(String id, String nome, String citta,String lat,String lon) {
        this.id = id;
        this.nome = nome;
        this.citta = citta;
        this.lat= lat;
        this.lon=lon;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }


 public String datiToString(){
     String tmp = id+";"+nome+";"+citta+";"+lat+";"+lon+";"+"\n";
 return tmp;
 }
    
}
