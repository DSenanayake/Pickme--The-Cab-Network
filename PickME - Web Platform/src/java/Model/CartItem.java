package Model;

public class CartItem {

    public final static int STATUS_OK = 0;

    private Location location;
    private Destination destination;
    private double km;
    private double cost_per_km;
    private double amount;
    private int status;
    private int service_provider_id;

    public CartItem() {
    }

    public CartItem(Location location, Destination destination, double km, double cost_per_km, double amount, int status, int service_provider_id) {
        this.location = location;
        this.destination = destination;
        this.km = km;
        this.cost_per_km = cost_per_km;
        this.amount = amount;
        this.status = status;
        this.service_provider_id = service_provider_id;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * @return the km
     */
    public double getKm() {
        return km;
    }

    /**
     * @param km the km to set
     */
    public void setKm(double km) {
        this.km = km;
    }

    /**
     * @return the cost_per_km
     */
    public double getCost_per_km() {
        return cost_per_km;
    }

    /**
     * @param cost_per_km the cost_per_km to set
     */
    public void setCost_per_km(double cost_per_km) {
        this.cost_per_km = cost_per_km;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the service_provider_id
     */
    public int getService_provider_id() {
        return service_provider_id;
    }

    /**
     * @param service_provider_id the service_provider_id to set
     */
    public void setService_provider_id(int service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

}
