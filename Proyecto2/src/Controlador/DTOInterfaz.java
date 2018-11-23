/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.MapaDecorator.DataMapa;
import Modelo.Marker;
import Modelo.TipoVista;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ayma-93
 */
public class DTOInterfaz {
    
    private ResultSet rs;
    private String provincia;
    private String canton;
    private boolean keepZoomC;
    private boolean keepZoomD;
    private TipoVista vista;
    private ArrayList<Marker> marcadores;
    private DataMapa mapa;

    public void DTOInterfaz() {
        this.rs = null;
        this.provincia = "";
        this.canton = "";
        this.keepZoomC = false;
        this.keepZoomD = false;
        this.vista = TipoVista.PROVINCIA;
        this.marcadores = new ArrayList<>();
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public boolean isKeepZoomC() {
        return keepZoomC;
    }

    public void setKeepZoomC(boolean keepZoomC) {
        this.keepZoomC = keepZoomC;
    }

    public boolean isKeepZoomD() {
        return keepZoomD;
    }

    public void setKeepZoomD(boolean keepZoomD) {
        this.keepZoomD = keepZoomD;
    }

    public TipoVista getVista() {
        return vista;
    }

    public void setVista(TipoVista vista) {
        this.vista = vista;
    }

    public ArrayList<Marker> getMarcadores() {
        return marcadores;
    }

    public void setMarcadores(ArrayList<Marker> marcadores) {
        this.marcadores = marcadores;
    }

    public DataMapa getMapa() {
        return mapa;
    }

    public void setMapa(DataMapa mapa) {
        this.mapa = mapa;
    }
    
    
    
    
    
}
