/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.MapaDecorator.DataMapa;
import Controlador.MapaDecorator.DecoratorCenter;
import Controlador.MapaDecorator.DecoratorMarkers;
import Controlador.MapaDecorator.DecoratorZoom;
import Controlador.MapaDecorator.Mapa;
import Modelo.Marker;
import Modelo.TipoVista;
import java.util.ArrayList;

/**
 *
 * @author juan_
 */
public class GestorDashboard {

    public GestorDashboard() {
    }
    
    public DTOInterfaz GetMapa(DTOInterfaz dto){
        DataMapa MapaConsulta1 = new Mapa(dto.getVista(),dto.isKeepZoomC(),dto.isKeepZoomD(),dto.getMarcadores());
        MapaConsulta1 = new DecoratorMarkers(MapaConsulta1);
        MapaConsulta1 = new DecoratorCenter(MapaConsulta1);
        MapaConsulta1 = new DecoratorZoom(MapaConsulta1);
        dto.setMapa(MapaConsulta1);
        return dto;
    }
}
