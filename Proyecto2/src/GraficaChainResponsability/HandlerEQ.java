/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaChainResponsability;

import org.jfree.chart.JFreeChart;

/**
 *
 * @author Sebastian
 */
public class HandlerEQ implements IHandler{
    
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
    public void generarChart(String ano1, String ano2, String tipo, String[] indicadores) {
        if (tipo.equals("Edad Quiquenal")){
            // Se empieza a generar la grafica
            
        }else{
            nextHandler.generarChart(ano1, ano2, tipo, indicadores); // siguiente
        }         
    }
    
}
