/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GraficaIterator;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class AgregadorConcreto implements AgregadorIterator{

    private ArrayList listaCantidadAccidentes = new ArrayList();

    @Override
    public void insertarCantidad(int numero) {
        listaCantidadAccidentes.add(numero);
    }

    @Override
    public IteradorHandler getIterador() {
        return new IteratorConcreto(this);
    }

    public ArrayList getListaCantidadAccidentes() {
        return listaCantidadAccidentes;
    }

    public void setListaCantidadAccidentes(ArrayList listaCantidadAccidentes) {
        this.listaCantidadAccidentes = listaCantidadAccidentes;
    }
    
    

    
    
   
}
