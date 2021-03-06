/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author ayma-
 */
public class Marker {
    
    private static int count = 0;
    private String lat;
    private String lon;
    private char label;
    private String nombre;
    private int cantidad;

    public Marker(String lat, String lon,int cantidad,String nombre) {
        this.lat = lat;
        this.lon = lon;
        this.label = (char) (count + 'A');
        this.cantidad = cantidad;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

    public String getDir() {
        return lat + "," + lon;
    }

    public static void setCount(int count) {
        Marker.count = count;
    }

    
    
    
    
}
