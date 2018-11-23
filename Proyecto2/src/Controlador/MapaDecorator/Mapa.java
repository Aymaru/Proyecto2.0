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
public class Mapa extends DataMapa{

    public Mapa(TipoVista vista,boolean keepZoomC,boolean keepZoomD,ArrayList<Marker> markers) {
        super.setBegingLink("https://maps.googleapis.com/maps/api/staticmap"); 
        super.setEndLink("&size=1029x550&scale=4");
        super.setKey("&key=AIzaSyAUt8LSR_YdBgd-F5uLmk1F5iUqhnbwA7E");
        super.setCenter("?center=9.72828,-84.2299717");
        super.setZoom("&zoom=8");
        super.setMarkers("");
        super.setKeepZoomC(keepZoomC);
        super.setKeepZoomD(keepZoomD);
        super.setVista(vista);
        super.setMarcadores(markers);
        ArrayList<String> colores = new ArrayList<>();
        colores.add("black");
        colores.add("brown");
        colores.add("green");
        colores.add("purple");
        colores.add("yellow");
        colores.add("blue");
        colores.add("gray");
        colores.add("orange");
        colores.add("red");
        colores.add("white");
        super.setColores(colores);
        
    }

    
    
    @Override
    public String getLink() {
        return super.BegingLink+super.center+super.zoom+super.EndLink+super.markers+super.key;
    }

    @Override
    protected TipoVista getVista() {
        return super.vista;
    }

    @Override
    protected String getBegingLink() {
        return super.BegingLink;
    }

    @Override
    protected String getEndLink() {
        return super.EndLink;
    }


     @Override
    protected boolean isKeepZoomD() {
        return super.keepZoomD;
    }


    @Override
    protected boolean isKeepZoomC() {
        return super.keepZoomC;
    }

    @Override
    protected ArrayList<Marker> getMarcadores() {
        return super.marcadores;
    }

    @Override
    protected ArrayList<String> getColores() {
        return super.colores;
    }

    @Override
    protected String getKey() {
        return super.key;
    }

    @Override
    protected String getZoom() {
        return super.zoom;
    }

    @Override
    protected String getMarkers() {
        return super.markers;
    }

    @Override
    protected String getCenter() {
        return super.center;
    }

   
    
    
    
}
