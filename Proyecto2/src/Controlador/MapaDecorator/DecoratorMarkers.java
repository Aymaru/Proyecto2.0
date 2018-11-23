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
public class DecoratorMarkers extends MapaDecorator{
    
    
    

    public DecoratorMarkers(DataMapa data) {
        super(data);
    }

   
    @Override
    public String getLink() {
        String NMarker = "";
        for (int i=0;i<getData().getMarcadores().size();i++){
            int color = i % getData().getColores().size();
            NMarker += "&markers=color:"+getData().getColores().get(color)+"%7Clabel:"+getData().getMarcadores().get(i).getLabel()+"%7C"+getData().getMarcadores().get(i).getDir();
        }
        return getData().getLink().substring(0,getData().getLink().indexOf("&key"))+NMarker+getData().getLink().substring(getData().getLink().indexOf("&key"));
    
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
    protected boolean isKeepZoomD() {
        return getData().isKeepZoomD();
    }


    @Override
    protected boolean isKeepZoomC() {
        return getData().isKeepZoomC();
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
