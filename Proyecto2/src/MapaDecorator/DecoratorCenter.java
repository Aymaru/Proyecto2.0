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
public class DecoratorCenter extends MapaDecorator{

    
    
    
    public DecoratorCenter(DataMapa data) {
        super(data);
    }

    
    
    @Override
    public String getLink() {
        String Ncenter = "";
        double minLat = Integer.MAX_VALUE;
        double maxLat = Integer.MIN_VALUE;
        double minLog = Integer.MAX_VALUE;
        double maxLog = Integer.MIN_VALUE;
        for (int i=0;i<getData().getMarcadores().size();i++){
             if (minLat>Double.valueOf(getData().getMarcadores().get(i).getLat())){
                minLat = Double.valueOf(getData().getMarcadores().get(i).getLat());
            }
            if (minLog>Double.valueOf(getData().getMarcadores().get(i).getLon())){
                minLog = Double.valueOf(getData().getMarcadores().get(i).getLon());
            }
            if (maxLat<Double.valueOf(getData().getMarcadores().get(i).getLat())){
                maxLat = Double.valueOf(getData().getMarcadores().get(i).getLat());
            }
            if (maxLog<Double.valueOf(getData().getMarcadores().get(i).getLon())){
                maxLog = Double.valueOf(getData().getMarcadores().get(i).getLon());
            }
        }
        if (getData().getMarcadores().size() > 0 && getData().getVista() != TipoVista.PROVINCIA){
            double centerLatitude = ( minLat + maxLat ) / 2;
            double centerLongitude = ( minLog + maxLog ) / 2;
            Ncenter = String.valueOf(centerLatitude)+","+String.valueOf(centerLongitude);
            return getData().getLink().substring(0,getData().getLink().indexOf("?center=")+8)+Ncenter+getData().getLink().substring(getData().getLink().indexOf("&zoom="));
        }
        
        return getData().getLink();
    }
    
    @Override
    protected TipoVista getVista() {
        return getData().getVista();
    }

    @Override
    protected String getBegingLink() {
        return getData().getBegingLink();
    }

    @Override
    protected String getEndLink() {
        return getData().getEndLink();
    }

    

    @Override
    protected boolean isKeepZoom() {
        return getData().isKeepZoom();
    }

    @Override
    protected ArrayList<Marker> getMarcadores() {
        return getData().getMarcadores();
    }

    @Override
    protected ArrayList<String> getColores() {
        return getData().getColores();
    }

    @Override
    protected String getKey() {
        return getData().getKey();
    }

    @Override
    protected String getZoom() {
        return getData().getZoom();
    }

    @Override
    protected String getMarkers() {
        return getData().getMarkers();
    }

    @Override
    protected String getCenter() {
        return getData().getCenter();
    }
    
}
