/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaChainResponsability;

import Controlador.DTOConsulta;


public interface IHandler {
    
   public  void setNuevoHandler(IHandler handler);
   
   public  IHandler getSiguiente();
   
   public  DTOConsulta generarChart(DTOConsulta dto);
   
    
}
