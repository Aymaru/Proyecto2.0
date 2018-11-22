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
public class DecoratorZoom extends MapaDecorator{

    
    public DecoratorZoom(DataMapa data) {
        super(data);
    }

    @Override
    public String getLink() {
        String Nzoom = "8";
        if (getData().isKeepZoomC()){
            Nzoom = "8";
        }
        else if (getData().isKeepZoomD()){
            Nzoom = "10";
        }
        else{
            if (getData().getMarcadores().size() > 0){
            if (TipoVista.PROVINCIA == getData().getVista()){
                Nzoom = "8";
            }
            else if (TipoVista.CANTON == getData().getVista()){
                Nzoom = "9";
                
            }
            else if (TipoVista.DISTRITO == getData().getVista()){
                Nzoom = "11";
            }
            
            }
        }
        return getData().getLink().substring(0,getData().getLink().indexOf("&zoom=")+6)+Nzoom+getData().getLink().substring(getData().getLink().indexOf("&size"));
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
