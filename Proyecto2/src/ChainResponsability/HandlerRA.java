/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChainResponsability;


/**
 *
 * @author Sebastian
 */
public class HandlerRA implements IHandler{
    
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
        if (tipo.equals("Tipo de Afectado")){
            
            
        }else{
            nextHandler.generarChart(ano1, ano2, tipo, indicadores); // siguiente
        } 
        
    }
   
    
}
