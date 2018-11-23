/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.MapaDecorator;

/**
 *
 * @author juan_
 */
public abstract class MapaDecorator extends DataMapa{
    private DataMapa data;

    public MapaDecorator(DataMapa data) {
        setData(data);
    }

    public DataMapa getData() {
        return data;
    }

    public void setData(DataMapa data) {
        this.data = data;
    }

    
    
    
    
}
