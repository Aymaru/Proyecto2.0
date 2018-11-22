/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapaDecorator;

/**
 *
 * @author ayma-
 */
public class Marker {
    
    private static int count = 0;
    private String lat;
    private String lon;
    private char label;
    private int cantidad;

    public Marker(String lat, String lon,int cantidad) {
        this.lat = lat;
        this.lon = lon;
        this.label = (char) (count + 'A');
        this.cantidad = cantidad;
        count++;
        if (this.count > 25){
            count -=43;
        }
        
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    

    public String getDir() {
        return lat + "," + lon;
    }

    
    
    
    
}
