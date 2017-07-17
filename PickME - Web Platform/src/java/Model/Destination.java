/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Deeptha
 */
public class Destination {

    private double lattitude;
    private double longitude;

    public Destination(double lattitude, double longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    /**
     * @return the lattitude
     */
    public double getLattitude() {
        return lattitude;
    }

    /**
     * @param lattitude the lattitude to set
     */
    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
