/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GraficaChainResponsability;

import Controlador.DAODB;
import Controlador.DTOConsulta;
import Modelo.TipoIdentificador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author Sebastian
 */
public class HandlerS implements IHandler{
    
    private IHandler nextHandler;
    private DAODB baseDatos;

    public HandlerS() throws ClassNotFoundException {
        this.baseDatos = new DAODB();
    }
    
    @Override
    public void setNuevoHandler(IHandler handler) {
        nextHandler = handler;
    }

    @Override
    public IHandler getSiguiente() {
        return nextHandler;
    }

    @Override
    public DTOConsulta generarChart(DTOConsulta dto) {
        if (dto.getTipoIdentificador() == TipoIdentificador.SEXO){
            // Se empieza a generar la grafica
            XYSeriesCollection dataset = new XYSeriesCollection();
            ArrayList<String> fechas = new ArrayList();
            for (String indicador : dto.getIndicadores()){
                dto.setIdentificador(indicador);
                baseDatos.consultaGrafica(dto);
                ResultSet rs = dto.getRs();
                try {
                    XYSeries serie = new XYSeries(indicador);
                    int acum = 0;
                    while(rs.next()){
                        int cantidad = rs.getInt("Cantidad");
                        String fecha = rs.getString("Anno");
                        acum++;                                
                        serie.add(acum, cantidad);
                        if(!fechas.contains(fecha)){
                            fechas.add(fecha);
                        }
                    }
                    rs.close();
                    dataset.addSeries(serie);
                } catch (SQLException ex) {
                    Logger.getLogger(HandlerTL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String fch = "";
            for(String dato : fechas){
                 fch += "  "+dato;
            }
            String caso = fch;
            JFreeChart chart = ChartFactory.createXYLineChart(
                "Sexo",
                "Tiempo Fecha(s): " + caso,
                "Cantidad de Accidentes",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
            dto.setGrafica(chart);
            return dto;
        }else{
            nextHandler.generarChart(dto); // siguiente
        } 
        return dto;
   
    }
}
