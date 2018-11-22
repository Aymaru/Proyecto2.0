/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapaDecorator;

import java.util.ArrayList;

/**
 *
 * @author juan_
 */
public abstract class DataMapa{
    private String BegingLink;
    private String EndLink;
    private String key;
    private String zoom;
    private String markers;
    private String center;
    private double minLat = Integer.MAX_VALUE;
    private double maxLat = Integer.MIN_VALUE;
    private double minLog = Integer.MAX_VALUE;
    private double maxLog = Integer.MIN_VALUE;
    private TipoVista vista;
    private boolean keepZoom;
    private ArrayList<Marker> marcadores = new ArrayList<>();
    private ArrayList<String> colores;

    
    public abstract String getLink();
    
    public void addMarker(Marker a){
        marcadores.add(a);
    }

    protected String getBegingLink() {
        return BegingLink;
    }

    protected void setBegingLink(String BegingLink) {
        this.BegingLink = BegingLink;
    }

    protected String getEndLink() {
        return EndLink;
    }

    protected void setEndLink(String EndLink) {
        this.EndLink = EndLink;
    }

    protected double getMinLat() {
        return minLat;
    }

    protected void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    protected double getMaxLat() {
        return maxLat;
    }

    protected void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    protected double getMinLog() {
        return minLog;
    }

    protected void setMinLog(double minLog) {
        this.minLog = minLog;
    }

    protected double getMaxLog() {
        return maxLog;
    }

    protected void setMaxLog(double maxLog) {
        this.maxLog = maxLog;
    }

    protected TipoVista getVista() {
        return vista;
    }

    protected void setVista(TipoVista vista) {
        this.vista = vista;
    }

    protected boolean isKeepZoom() {
        return keepZoom;
    }

    protected void setKeepZoom(boolean keepZoom) {
        this.keepZoom = keepZoom;
    }

    protected ArrayList<Marker> getMarcadores() {
        return marcadores;
    }

    protected void setMarcadores(ArrayList<Marker> marcadores) {
        this.marcadores = marcadores;
    }

    protected ArrayList<String> getColores() {
        return colores;
    }

    protected void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    protected String getKey() {
        return key;
    }

    protected void setKey(String key) {
        this.key = key;
    }

    protected String getZoom() {
        return zoom;
    }

    protected void setZoom(String zoom) {
        this.zoom = zoom;
    }

    protected String getMarkers() {
        return markers;
    }

    protected void setMarkers(String markers) {
        this.markers = markers;
    }

    protected String getCenter() {
        return center;
    }

    protected void setCenter(String center) {
        this.center = center;
    }

    
    
    
    
}
