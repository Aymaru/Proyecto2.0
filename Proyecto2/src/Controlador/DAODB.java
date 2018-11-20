/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class DAODB {
     
    ResultSet rs = null;
    Connection conn = null;

    public DAODB() throws ClassNotFoundException {
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3307/proyecto2db?verifyServerCertificate=false&useSSL=true";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl,"root","root"); 

          } catch (SQLException e) {
          }
    }
 

    public ResultSet getProvincias(){
        String query = "call getProvincias()";
        try {
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getCantones(String provincia){
        String query = "{call getCantones(?)}";
        
        try {
            CallableStatement cs = null;
            
            cs = conn.prepareCall(query);
            cs.setString("provincia", provincia);
            rs = cs.executeQuery();
                       
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getDistritos(String provincia, String canton){
        String query = "{call getDistritos(?,?)}";
        
        try {
            CallableStatement cs = null;
            
            cs = conn.prepareCall(query);
            cs.setString("provincia", provincia);
            cs.setString("canton", canton);
            rs = cs.executeQuery();
                       
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getTiposLesion(){
        String query = "call getTiposLesion()";
        try {
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getRolesAfectado(){
        String query = "call getRolesAfectado()";
        try {
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getEdadesQuinquenal(){
        String query = "call getEdadesQuinquenales()";
        try {
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAODB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {

        }
    }
}

