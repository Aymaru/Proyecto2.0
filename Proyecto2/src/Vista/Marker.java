/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author ayma-
 */
public class Marker {
    
    private String lat;
    private String lon;
    private String label;

    public Marker(String lat, String lon, String label) {
        this.lat = lat;
        this.lon = lon;
        this.label = label;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    
    
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDir() {
        return lat + "," + lon;
    }

    
    
    
    
}
