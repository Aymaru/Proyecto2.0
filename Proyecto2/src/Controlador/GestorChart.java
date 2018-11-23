/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import GraficaChainResponsability.HandlerTL;
import GraficaChainResponsability.IHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class GestorChart {
    

    public GestorChart() {
    }
    
    public DTOConsulta generarGrafica(DTOConsulta dtoConsulta){
        try {
            IHandler handlerGrafico = new HandlerTL();
            handlerGrafico.generarChart(dtoConsulta);            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestorChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtoConsulta;
    }
    
}
