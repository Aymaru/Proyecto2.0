/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.ResultSet;

/**
 *
 * @author ayma-93
 */
public class DTOInterfaz {
    
    ResultSet rs;
    String provincia;
    String canton;

    public void DTOInterfaz() {
        this.rs = null;
        this.provincia = "";
        this.canton = "";
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }
    
    
}
