/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapaDecorator;

/**
 *
 * @author juan_
 */
public abstract class DataMapa implements IAgregable{
    private String BegingLink;
    private String EndLink;
    private String key;
    private String zoom;
    private String markers;
    private String center;
    private double minLat = Integer.MAX_VALUE;
    private double maxLat = Integer.MIN_VALUE;
    private double minLog = Integer.MAX_VALUE;
    private double maxLog = Integer.MIN_VALUE;

    public String getBegingLink() {
        return BegingLink;
    }

    public void setBegingLink(String BegingLink) {
        this.BegingLink = BegingLink;
    }

    public String getEndLink() {
        return EndLink;
    }

    public void setEndLink(String EndLink) {
        this.EndLink = EndLink;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getMarkers() {
        return markers;
    }

    public void setMarkers(String markers) {
        this.markers = markers;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLog() {
        return minLog;
    }

    public void setMinLog(double minLog) {
        this.minLog = minLog;
    }

    public double getMaxLog() {
        return maxLog;
    }

    public void setMaxLog(double maxLog) {
        this.maxLog = maxLog;
    }
    
    
    
}
