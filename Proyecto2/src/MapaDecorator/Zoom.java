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
public class Zoom extends MapaDecorator{

    
    public Zoom(DataMapa data) {
        super(data);
    }

    @Override
    public String getLink() {
        String Nzoom = "8";
        if (getData().isKeepZoom()){
            Nzoom = "8";
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
        getData().setZoom("&zoom=" + Nzoom);
        return getData().getLink();
    }

}
