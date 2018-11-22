/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import GraficaChainResponsability.HandlerTL;
import GraficaChainResponsability.IHandler;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class GestorChart {
    

    public GestorChart() {
    }
    
    public DTOConsulta generarGrafica(DTOConsulta dtoConsulta){
        IHandler handlerGrafico = new HandlerTL();
        String ano1 = dtoConsulta.año_ini;
        String ano2 = dtoConsulta.año_fin;
        TipoIdentificador tipo = dtoConsulta.tipoIdentificador;
        ArrayList indicadores = dtoConsulta.getIndicadores();
        handlerGrafico.generarChart(ano1, ano2, tipo, indicadores);
        return dtoConsulta;
    }
    
}
