/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TipoVista;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.jfree.chart.JFreeChart;

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
    String provincia;
    String canton;
    String distrito;
    String tipo_lesion;
    String rol_afectado;
    String sexo;
    String edad_quinquenal;
    ArrayList<String> indicadores;
    JFreeChart grafica;

    //Constructor
    public DTOConsulta() {
    }

    //Getters y Setters

    public ArrayList<String> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(ArrayList<String> indicadores) {
        this.indicadores = indicadores;
    }
    
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTipo_lesion() {
        return tipo_lesion;
    }

    public void setTipo_lesion(String tipo_lesion) {
        this.tipo_lesion = tipo_lesion;
    }

    public String getRol_afectado() {
        return rol_afectado;
    }

    public void setRol_afectado(String rol_afectado) {
        this.rol_afectado = rol_afectado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad_quinquenal() {
        return edad_quinquenal;
    }

    public void setEdad_quinquenal(String edad_quinquenal) {
        this.edad_quinquenal = edad_quinquenal;
    }

    public JFreeChart getGrafica() {
        return grafica;
    }

    public void setGrafica(JFreeChart grafica) {
        this.grafica = grafica;
    }
     
}