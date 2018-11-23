/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GraficaIterator;

/**
 *
 * @author Sebastian
 */
public interface AgregadorIterator {
    
    void insertarCantidad(int numero);
    int mostrarInformacion(int indice);
    AgregadorConcreto getIterador();
    
}
