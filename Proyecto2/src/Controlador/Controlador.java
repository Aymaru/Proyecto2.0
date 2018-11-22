/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author ayma-93
 */
public class Controlador {
    DTOInterfaz dtoInterfaz;
    DAODB dao;
    public Controlador() throws ClassNotFoundException {
        this.dao = new DAODB();
        this.dtoInterfaz = new DTOInterfaz();
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
