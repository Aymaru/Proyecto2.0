package GraficaChainResponsability;

import Controlador.DAODB;
import Controlador.DTOConsulta;
import Controlador.TipoIdentificador;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HandlerTL implements IHandler{
    
    private IHandler nextHandler;
    private DAODB baseDatos;

    public HandlerTL() throws ClassNotFoundException {
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
        try {
            //Setteado de precesores
            HandlerS hS = new HandlerS();
            this.setNuevoHandler(hS);
            HandlerRA hRA = new HandlerRA();
            hS.setNuevoHandler(hRA);
            HandlerEQ hEQ = new HandlerEQ();
            hRA.setNuevoHandler(hEQ);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HandlerTL.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dto.getTipoIdentificador() == TipoIdentificador.TIPO_LESION){
            // Se empieza a generar la grafica
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
