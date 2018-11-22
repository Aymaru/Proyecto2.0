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
public abstract class MapaDecorator implements IAgregable{
    private IAgregable agregable;

    public MapaDecorator(IAgregable agregable) {
        setAgregable(agregable);
    }

    public IAgregable getAgregable() {
        return agregable;
    }

    public void setAgregable(IAgregable agregable) {
        this.agregable = agregable;
    }
    
    
    
}
