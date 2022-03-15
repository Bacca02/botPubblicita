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
    String id,nome,citta;

    public datiPersona(String id, String nome, String citta) {
        this.id = id;
        this.nome = nome;
        this.citta = citta;
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

    @Override
    public String toString() {
        return id + ";" + nome + ";" + citta + ";";
    }
    
}
