/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import static java.lang.Math.log;

/**
 *
 * @author Sebastian
 */
public class LatLongUtils {
 
    public static Double[] toDecimal(String latitude, String longitude) {
        try {
            String[] lat = latitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            String[] lng = longitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double dlat = toDecimal(lat); 
            Double dlng = toDecimal(lng);
            return new Double[]{dlat, dlng};
        } catch(Exception ex) {
            return null;
        }
    }
 
    public static Double toDecimal(String latOrLng) {
        try {
            String[] latlng = latOrLng.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double dlatlng = toDecimal(latlng); 
            return dlatlng;
        } catch(Exception ex) {
            return null;
        }
    }
 
    public static Double toDecimal(String[] coord) {
        double d = Double.parseDouble(coord[0]);
        double m = Double.parseDouble(coord[1]);
        double s = Double.parseDouble(coord[2]);
        double signo = 1;
        if (coord[0].startsWith("-"))
            signo = -1;
        return signo * (Math.abs(d) + (m / 60.0) + (s / 3600.0));
    }
 
    public static void main(String[] args) {
        //9°54'30"	-84°4'1"
        //9°54'57\"	-84°6'7\"
        //9° 55' 17\"	-84° 3' 26\"
        //10° 01' 24\"	-84° 48' 39\"
        //10° 07' 02\"	-84° 49' 40\"
        //09° 10' 15\"	-83° 44' 45\"
        //09° 41' 22\"	-85° 06' 26\"
        //10° 07' 57\"	-85° 01' 15\"
        //08° 37' 39\"	-83° 09' 22\"

        Double[] coord = toDecimal("08° 37' 39\"", "-83° 09' 22\"");
        System.out.println(coord[0] + " " + coord[1]);
    }
 
}