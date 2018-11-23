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
        
    }

//    @Override
//    public int current() {
//        
//    }
//
//    @Override
//    public int next() {
//        
//    }
//
//    @Override
//    public boolean quedanElementos() {
//        
//    }

    @Override
    public int current() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int next() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean quedanElementos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
