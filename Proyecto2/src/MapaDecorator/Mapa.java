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
public class Mapa extends DataMapa{

    public Mapa(TipoVista vista,boolean keepZoom) {
        super.setBegingLink("https://maps.googleapis.com/maps/api/staticmap"); 
        super.setEndLink("&size=1029x550&scale=4");
        super.setKey("&key=AIzaSyAUt8LSR_YdBgd-F5uLmk1F5iUqhnbwA7E");
        super.setCenter("?center=9.72828,-84.2299717");
        super.setZoom("&zoom=8");
        super.setMarkers("");
        super.setKeepZoom(keepZoom);
        super.setVista(vista);
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
        return super.getBegingLink()+super.getCenter()+super.getZoom()+super.getEndLink()+super.getMarkers()+super.getKey();
    }

   
    
    
    
}
