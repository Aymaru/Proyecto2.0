/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2dis;

import Vista.MainFrame;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ayma-93
 */
public class Proyecto2Dis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        try {
            // TODO code application logic here
            
            MainFrame mf = new MainFrame();
            mf.show();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto2Dis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
