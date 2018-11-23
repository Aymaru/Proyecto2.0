package Controlador.GraficaIterator;

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
    public void first() {
        Object obj = null;
        if( this.agregado.aDatos.isEmpty() == false )
        {
            this.posicion_actual = 0;
            obj = this.agregado.aDatos.firstElement();
        }
        return obj;
    }

    @Override
    public int current() {
        
    }

    @Override
    public int next() {
        
    }

    @Override
    public boolean quedanElementos() {
        
    }
    
}
