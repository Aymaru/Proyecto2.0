package GraficaChainResponsability;

import Controlador.TipoIdentificador;
import java.util.ArrayList;


public class HandlerTL implements IHandler{
    
    private IHandler nextHandler;

    @Override
    public void setNuevoHandler(IHandler handler) {
        nextHandler = handler;
    }

    @Override
    public IHandler getSiguiente() {
        return nextHandler;
    }

    @Override
    public void generarChart(String ano1, String ano2, TipoIdentificador tipo, ArrayList indicadores) {
        //Setteado de precesores
        HandlerS hS = new HandlerS();
        this.setNuevoHandler(hS);
        HandlerRA hRA = new HandlerRA();
        hS.setNuevoHandler(hRA);
        HandlerEQ hEQ = new HandlerEQ();
        hRA.setNuevoHandler(hEQ);
        
        if (tipo == TipoIdentificador.TIPO_LESION){
            // Se empieza a generar la grafica
            
        }else{
            nextHandler.generarChart(ano1, ano2, tipo, indicadores); // siguiente
        }      
        
    }
    
}
