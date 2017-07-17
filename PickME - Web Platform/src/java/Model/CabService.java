package Model;

public class CabService {
    private int id;
    private int meters;

    public CabService(int id, int meters) {
        this.id = id;
        this.meters = meters;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the meters
     */
    public int getMeters() {
        return meters;
    }

    /**
     * @param meters the meters to set
     */
    public void setMeters(int meters) {
        this.meters = meters;
    }
}
