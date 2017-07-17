package Model;

import java.util.List;

public class CabOrder {

    private String city;
    private Location location;
    private Destination destination;
    private List<CabService> selectedServices;
    private double distance;
    private String duration;

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the selectedServices
     */
    public List<CabService> getSelectedServices() {
        return selectedServices;
    }

    /**
     * @param selectedServices the selectedServices to set
     */
    public void setSelectedServices(List<CabService> selectedServices) {
        this.selectedServices = selectedServices;
    }

    /**
     * @return the l
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param l the l to set
     */
    public void setLocation(Location l) {
        this.location = l;
    }

    /**
     * @return the d
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param d the d to set
     */
    public void setDestination(Destination d) {
        this.destination = d;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
