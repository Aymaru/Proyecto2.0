/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapaDecorator;

/**
 *
 * @author juan_
 */
public class Zoom extends MapaDecorator{

    public Zoom(IAgregable agregable) {
        super(agregable);
    }
    
    
    @Override
    public String getLink() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
