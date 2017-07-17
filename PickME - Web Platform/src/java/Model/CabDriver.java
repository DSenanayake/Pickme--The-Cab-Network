package Model;

import Db.LoginDetails;
import Db.Requeststatus;
import Db.ServiceOrder;
import Db.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;

public class CabDriver {

    public static final int REQUEST_PENDING = 1;
    public static final int REQUEST_ACCEPTED = 2;
    public static final int REQUEST_REJECTED = 3;

    private static SessionFactory sf;
    static Session ses;

    public static void registerDriver(String email, Integer l) {
        refresh();
    }
    public static void main(String[] args) {
        registerDriver(null, 1);
    }
    private Session s;

    public CabDriver() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
            s = sf.openSession();
        }
    }

    private static void refresh() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public static void updateLocation(String driverKey, double latitude, double longitude) {
        refresh();
        try {
            int driverId = Integer.parseInt(Controller.Services.decrypt(driverKey));
            Transaction tr = ses.beginTransaction();

            Db.CabDriver driver = (Db.CabDriver) ses.load(Db.CabDriver.class, driverId);
            Db.CabDriverLocation location = driver.getCabDriverLocation();

            if (location == null) {
                location = new Db.CabDriverLocation();

                location.setLattitude(latitude);
                location.setLongitude(longitude);

                ses.saveOrUpdate(location);

                driver.setCabDriverLocation(location);
                ses.update(driver);
            } else {

                location.setLattitude(latitude);
                location.setLongitude(longitude);

                ses.update(location);
            }

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loginDriver(String uname, String pword) {
        refresh();
        String stat;
        JSONArray array = new JSONArray();
        HashMap details = new HashMap();

        try {
            System.out.println("ATTP_TO_LOG: " + uname);
            LoginDetails loginUser = Model.User.loginUser(uname, pword);
            if (loginUser != null) {
                if (loginUser.getLoginStatus().getLoginStatusId() != Model.UserStatus.NOT_CONFIRMED & loginUser.getLoginStatus().getLoginStatusId() != Model.UserStatus.DELETED) {
                    if (loginUser.getUserType().getUserTypeId() == Model.UserType.CAB_DRIVER) {

                        Db.CabDriver cabDriver = (Db.CabDriver) loginUser.getCabDrivers().toArray()[0];

                        details.put("firstname", cabDriver.getCabDriverDetails().getFirstName());
                        details.put("lastname", cabDriver.getCabDriverDetails().getLastName());
                        details.put("address1", cabDriver.getCabDriverDetails().getAddress1());
                        details.put("address2", cabDriver.getCabDriverDetails().getAddress2());
                        details.put("city", cabDriver.getCabDriverDetails().getCity().getGooglePlaceId());
                        details.put("dob", cabDriver.getCabDriverDetails().getDob());
                        details.put("profile_pic", cabDriver.getProfilePicture().getProfilePicUrl());
                        details.put("service_provider_id", cabDriver.getServiceProvider().getServiceProviderId());
                        details.put("service_provider", cabDriver.getServiceProvider().getServiceProviderName());
                        details.put("driver_key", Controller.Services.encrypt(cabDriver.getCabDriverId() + ""));
                        details.put("uname", cabDriver.getLoginDetails().getEmail());
                        details.put("pword", cabDriver.getLoginDetails().getPassword());

                        stat = "OK";
                    } else {
                        stat = "BAD_USER";
                    }
                } else {
                    stat = "NOT_CONFIRMED";
                }
            } else {
                stat = "NOT_EXIST";
            }
        } catch (Exception e) {
            e.printStackTrace();
            stat = "ERROR";
        }

        HashMap map = new HashMap();

        map.put("status", stat);
        map.put("details", details);

        array.add(map);

        System.out.println("[ WEB SERV - USER LOGING ] - JSON : " + array.toJSONString());
        return array.toJSONString();

    }

    public static String requestLeave(String driverKey, String reason) throws Exception {
        refresh();
        String result;

        int dId = Integer.parseInt(Controller.Services.decrypt(driverKey));

        Transaction tr = ses.beginTransaction();

        Db.CabDriver driver = (Db.CabDriver) ses.load(Db.CabDriver.class, dId);

        Criteria cr = ses.createCriteria(Db.ServiceOrder.class);
        cr.add(Restrictions.eq("orderStatus", (Db.OrderStatus) ses.load(Db.OrderStatus.class, Model.Invoice.ORDER_STATUS_REFERED)));
        cr.add(Restrictions.eq("cabDriver", driver));
        List l = cr.list();

        if (l.isEmpty()) {
            Db.Leavingrequest request = new Db.Leavingrequest();

            request.setCabDriver(driver);
            request.setReason(reason);
            request.setRequeststatus((Requeststatus) ses.load(Db.Requeststatus.class, REQUEST_PENDING));
            request.setDateTime(new Date());

            ses.save(request);

            tr.commit();
            result = "OK";
        } else {
            result = "IN_A_SERVICE";
        }
        return result;
    }

}
