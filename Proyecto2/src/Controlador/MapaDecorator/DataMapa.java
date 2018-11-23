/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.MapaDecorator;

import Modelo.Marker;
import Modelo.TipoVista;
import java.util.ArrayList;

/**
 *
 * @author juan_
 */
public abstract class DataMapa{
    protected String BegingLink;
    protected String EndLink;
    protected String key;
    protected String zoom;
    protected String markers;
    protected String center;
    
    protected TipoVista vista;
    protected boolean keepZoomC;
    protected boolean keepZoomD;
    protected ArrayList<Marker> marcadores = new ArrayList<>();
    protected ArrayList<String> colores;

    
    public abstract String getLink();
    

    protected abstract String getBegingLink();

    protected void setBegingLink(String BegingLink) {
        this.BegingLink = BegingLink;
    }

    protected abstract String getEndLink();

    protected void setEndLink(String EndLink) {
        this.EndLink = EndLink;
    }
    

    protected abstract TipoVista getVista();

    public void setVista(TipoVista vista) {
        this.vista = vista;
    }

    protected abstract boolean isKeepZoomC();

    public void setKeepZoomC(boolean keepZoomC) {
        this.keepZoomC = keepZoomC;
    }
    
    protected abstract boolean isKeepZoomD();

    public void setKeepZoomD(boolean keepZoomD) {
        this.keepZoomD = keepZoomD;
    }
    protected abstract ArrayList<Marker> getMarcadores();

    public void setMarcadores(ArrayList<Marker> marcadores) {
        this.marcadores = marcadores;
    }

    protected abstract ArrayList<String> getColores();

    protected void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    protected abstract String getKey();

    protected void setKey(String key) {
        this.key = key;
    }

    protected abstract String getZoom();

    protected void setZoom(String zoom) {
        this.zoom = zoom;
    }

    protected abstract String getMarkers();

    protected void setMarkers(String markers) {
        this.markers = markers;
    }

    protected abstract String getCenter();

    protected void setCenter(String center) {
        this.center = center;
    }

    
    
    
    
}
