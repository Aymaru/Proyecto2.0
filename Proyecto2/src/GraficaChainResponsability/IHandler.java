/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaChainResponsability;

import org.jfree.chart.JFreeChart;


public interface IHandler {
    
   public  void setNuevoHandler(IHandler handler);
   
   public  IHandler getSiguiente();
   
   public  void generarChart(String ano1, String ano2, String tipo, String[] indicadores);
   
    
}
