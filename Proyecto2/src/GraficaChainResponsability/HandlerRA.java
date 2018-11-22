/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaChainResponsability;

import Controlador.DAODB;
import Controlador.DTOConsulta;
import Controlador.TipoIdentificador;
import java.util.ArrayList;


/**
 *
 * @author Sebastian
 */
public class HandlerRA implements IHandler{
    
    private IHandler nextHandler;
    private DAODB baseDatos;
    
    public HandlerRA() throws ClassNotFoundException {
        this.baseDatos = new DAODB();
    }

    @Override
    public void setNuevoHandler(IHandler handler) {
        nextHandler = handler;
    }

    @Override
    public IHandler getSiguiente() {
        return nextHandler;
    }

    @Override
    public DTOConsulta generarChart(DTOConsulta dto) {
        if (dto.getTipoIdentificador() == TipoIdentificador.ROL_AFECTADO){
            for (String indicador : dto.getIndicadores()){
                dto.setIdentificador(indicador);
                dto = baseDatos.consultaGrafica(dto);
                
            }
            
        }else{
            nextHandler.generarChart(dto); // siguiente
        } 
        return dto;
    }
   
    
}
