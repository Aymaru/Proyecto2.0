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
public class MarkersyCenter extends MapaDecorator{
    
    String Ncenter = "";
    String NMarker = "";

    public MarkersyCenter(DataMapa data) {
        super(data);
    }

   
    @Override
    public String getLink() {
        
        for (int i=0;i<getData().getMarcadores().size();i++){
            int color = i % getData().getColores().size();
            NMarker += "&markers=color:"+getData().getColores().get(color)+"%7Clabel:"+getData().getMarcadores().get(i).getLabel()+"%7C"+getData().getMarcadores().get(i).getDir();
            if (getData().getMinLat()>Double.valueOf(getData().getMarcadores().get(i).getLat())){
                getData().setMinLat(Double.valueOf(getData().getMarcadores().get(i).getLat()));
            }
            if (getData().getMinLog()>Double.valueOf(getData().getMarcadores().get(i).getLon())){
                getData().setMinLog(Double.valueOf(getData().getMarcadores().get(i).getLon()));
            }
            if (getData().getMaxLat()<Double.valueOf(getData().getMarcadores().get(i).getLat())){
                getData().setMaxLat(Double.valueOf(getData().getMarcadores().get(i).getLat()));
            }
            if (getData().getMaxLog()<Double.valueOf(getData().getMarcadores().get(i).getLon())){
                getData().setMaxLog(Double.valueOf(getData().getMarcadores().get(i).getLon()));
            }
        }
        if (getData().getMarcadores().size() > 0 && getData().getVista() != TipoVista.PROVINCIA){
            double centerLatitude = ( getData().getMinLat() + getData().getMaxLat() ) / 2;
            double centerLongitude = ( getData().getMinLog() + getData().getMaxLog() ) / 2;
            Ncenter = String.valueOf(centerLatitude)+","+String.valueOf(centerLongitude);
        }
        getData().setCenter("?center="+Ncenter);
        getData().setMarkers(NMarker);
        return getData().getLink();
    
    }

    
    
}
