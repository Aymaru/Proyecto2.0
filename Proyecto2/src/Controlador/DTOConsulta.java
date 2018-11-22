/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import MapaDecorator.TipoVista;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class DTOConsulta {
    
    //Variables
    TipoVista tipoVista;
    TipoIdentificador tipoIdentificador;
    ResultSet rs;
    String año_ini;
    String año_fin;
    String identificador;
    ArrayList<String> provincias;
    ArrayList<String> cantones;
    ArrayList<String> distritos;
    ArrayList<String> sexos;
    ArrayList<String> rolAfectados;
    ArrayList<String> tipoLesiones;
    ArrayList<String> edadQuinquenales;

    //Constructor
    public DTOConsulta() {
    }

    //Getters y Setters

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getAño_ini() {
        return año_ini;
    }

    public void setAño_ini(String año_ini) {
        this.año_ini = año_ini;
    }

    public String getAño_fin() {
        return año_fin;
    }

    public void setAño_fin(String año_fin) {
        this.año_fin = año_fin;
    }

       public ArrayList<String> getProvincias() {
        return provincias;
    }

    public void setProvincias(ArrayList<String> provincias) {
        this.provincias = provincias;
    }

    public ArrayList<String> getCantones() {
        return cantones;
    }

    public void setCantones(ArrayList<String> cantones) {
        this.cantones = cantones;
    }

    public ArrayList<String> getDistritos() {
        return distritos;
    }

    public void setDistritos(ArrayList<String> distritos) {
        this.distritos = distritos;
    }

    public ArrayList<String> getSexos() {
        return sexos;
    }

    public void setSexos(ArrayList<String> sexos) {
        this.sexos = sexos;
    }

    public ArrayList<String> getRolAfectados() {
        return rolAfectados;
    }

    public void setRolAfectados(ArrayList<String> rolAfectados) {
        this.rolAfectados = rolAfectados;
    }

    public ArrayList<String> getTipoLesiones() {
        return tipoLesiones;
    }

    public void setTipoLesiones(ArrayList<String> tipoLesiones) {
        this.tipoLesiones = tipoLesiones;
    }

    public ArrayList<String> getEdadQuinquenales() {
        return edadQuinquenales;
    }

    public void setEdadQuinquenales(ArrayList<String> edadQuinquenales) {
        this.edadQuinquenales = edadQuinquenales;
    }

    public TipoVista getTipoVista() {
        return tipoVista;
    }

    public void setTipoVista(TipoVista tipoVista) {
        this.tipoVista = tipoVista;
    }

    public TipoIdentificador getTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setTipoIdentificador(TipoIdentificador tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
  

    
    
    
}
