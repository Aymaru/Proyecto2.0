package Controlador.GraficaIterator;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class IteratorConcreto implements IteradorHandler{

    
     private AgregadorConcreto agregado;
     private int posicion_actual = 0;

    public IteratorConcreto(AgregadorConcreto agregado) {
        this.agregado = agregado;
    }

    @Override
    public boolean hasNext() {
         if(posicion_actual < agregado.getListaCantidadAccidentes().size()){
            return true;
         }
         return false;
    }

    @Override
    public int next() {
        
        int cantAccidente = 0;
        
        if( (this.posicion_actual ) < this.agregado.getListaCantidadAccidentes().size()){
            cantAccidente = (int)this.agregado.getListaCantidadAccidentes().get(this.posicion_actual);
            this.posicion_actual = this.posicion_actual + 1;
        }
        return cantAccidente;
    }
          
    
    
}
