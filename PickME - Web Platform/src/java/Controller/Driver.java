package Controller;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Deeptha
 */
@WebService(serviceName = "Driver")
public class Driver {

    /**
     * This is a sample web service operation
     *
     * @param uname
     * @param pword
     * @return
     */
    @WebMethod(operationName = "userLogin")
    public String userLogin(@WebParam(name = "uname") String uname, @WebParam(name = "pword") String pword) {
        String json = Model.CabDriver.loginDriver(uname, pword);
        return json;
    }

    /**
     *
     * @param driverKey
     * @param latitude
     * @param longitude
     */
    @WebMethod(operationName = "updateLocation")
    public String updateLocation(@WebParam(name = "driverKey") String driverKey, @WebParam(name = "latitude") double latitude, @WebParam(name = "longitude") double longitude) {
        try {
            Model.CabDriver.updateLocation(driverKey, latitude, longitude);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @WebMethod(operationName = "requestLeave")
    public String requestLeave(@WebParam(name = "driverKey") String driverKey, @WebParam(name = "reason") String reason) {
        try {
            return Model.CabDriver.requestLeave(driverKey, reason);
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
