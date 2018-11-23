/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.MapaDecorator.DataMapa;
import Modelo.Marker;
import Modelo.TipoVista;
import java.util.ArrayList;

/**
 *
 * @author ayma-93
 */
public class Controlador {
    private DTOInterfaz dtoInterfaz;
    private DTOConsulta dtoConsulta;
    private DAODB dao;
    private GestorChart gestorGrafico;
    private GestorDashboard gestorDashboard;
    public Controlador() throws ClassNotFoundException {
        this.dao = new DAODB();
        this.dtoInterfaz = new DTOInterfaz();
        this.dtoConsulta = new DTOConsulta();
        this.gestorGrafico = new GestorChart();
        this.gestorDashboard = new GestorDashboard();
        
    }
    
    public DTOInterfaz getMapa(DTOInterfaz dto){
        return gestorDashboard.GetMapa(dto);
    }
    
    
    //Consultas
    
    public DTOConsulta consultaLibre(DTOConsulta dto){
        this.dtoConsulta = dao.consultaLibre(dto);
        return this.dtoConsulta;
    }
    
    public DTOConsulta consultaDashboard(DTOConsulta dto){
        this.dtoConsulta = dao.consultaDashboard(dto);
        return this.dtoConsulta;
    }
    
    public DTOConsulta getGrafica(DTOConsulta dto){
        return gestorGrafico.generarGrafica(dto);
    }       
    
    //Funciones para cargar datos a la interfaz
    
    public DTOInterfaz getListaProvincias(DTOInterfaz dto){       
        this.dtoInterfaz = dao.getProvincias(dto);
        return this.dtoInterfaz;
    }

    public DTOInterfaz getListaTipoLesion(DTOInterfaz dto){
        this.dtoInterfaz = dao.getTiposLesion(dto);
        return this.dtoInterfaz;
    }

    public DTOInterfaz getListaRolAfectado(DTOInterfaz dto){
        this.dtoInterfaz = dao.getRolesAfectado(dto);
        return this.dtoInterfaz;
    }

    public DTOInterfaz getListaEdadQuinquenal(DTOInterfaz dto){
        this.dtoInterfaz = dao.getEdadesQuinquenal(dto);
        return this.dtoInterfaz;
    }
    
    public DTOInterfaz getListaCantones(DTOInterfaz dto){
        this.dtoInterfaz = dao.getCantones(dto);
        return this.dtoInterfaz;
    }
    
    public DTOInterfaz getListaDistritos(DTOInterfaz dto){
        this.dtoInterfaz = dao.getDistritos(dto);
        return this.dtoInterfaz;
    }
       
    //Getters y Setters

    public DAODB getDao() {
        return dao;
    }

    public void setDao(DAODB dao) {    
        this.dao = dao;
    }

    
    
    
    
}
