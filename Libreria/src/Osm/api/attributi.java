/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Osm.api;

/**
 *
 * @author baccaglini_christian
 */
public class attributi {

    String lon, lat;

    public attributi() {

    }

    public attributi(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;

    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

}
