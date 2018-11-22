/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapaDecorator;

/**
 *
 * @author juan_
 */
public class Mapa extends DataMapa{

    public Mapa() {
        super.setBegingLink("https://maps.googleapis.com/maps/api/staticmap");
        super.setEndLink("&size=1029x550&scale=4");
        super.setKey("&key=AIzaSyAUt8LSR_YdBgd-F5uLmk1F5iUqhnbwA7E");
        super.setCenter("?center=9.72828,-84.2299717");
        super.setZoom("&zoom=8");
        super.setMarkers("");
    }

    
    
    @Override
    public String getLink() {
        return super.getBegingLink()+super.getCenter()+super.getZoom()+super.getEndLink()+super.getMarkers()+super.getKey();
    }
    
}
