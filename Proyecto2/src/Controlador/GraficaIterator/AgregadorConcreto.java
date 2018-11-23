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

    private ArrayList listaCantidadAccidentes;
    
    @Override
    public void insertarCantidad(int numero) {
        listaCantidadAccidentes.add(numero);
    }
    
    public void registroAccidentes(){
        this.listaCantidadAccidentes = new ArrayList();
    }

    @Override
    public int mostrarInformacion(int indice) {
        return 6;
        
        
        
 
//        public void InsertarVehiculo(string marca, string modelo, double precio){
//            Vehiculo v = new Vehiculo(marca, modelo, DateTime.Now, precio);
//            listaCantidadAccidentes.add(v);
//        }
//
//        public Vehiculo MostrarInformacionVehiculo(int indice){
//            return (Vehiculo)listaCantidadAccidentes[indice];
//        }
    }

    @Override
    public AgregadorConcreto getIterador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
