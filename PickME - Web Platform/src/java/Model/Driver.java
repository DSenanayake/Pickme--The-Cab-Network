package Model;

import Db.LoginDetails;
import Db.LoginStatus;
import Db.OrderStatus;
import static Model.CabDriver.ses;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;

public class Driver {

    private static SessionFactory sf;
    private Session ses;
    private Transaction tr;

    public Driver() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    private Db.CabDriver getCabDriverByEmail(String email) {
        System.out.println(email);
        return (Db.CabDriver) ses.createQuery("FROM CabDriver c WHERE c.loginDetails.email='" + email + "'").uniqueResult();
    }

    public String getProfilePic(Db.LoginDetails log) {
        try {
            Db.CabDriver d = getCabDriverByEmail(log.getEmail());

            return d.getProfilePicture().getProfilePicUrl();
        } catch (Exception e) {
            return "images/profiles/driver.png";
        } finally {
            ses.close();
        }
    }

    public String printDriverDetails(Db.LoginDetails log, boolean edit) {
        try {
            String html = "";
            Db.CabDriver d = getCabDriverByEmail(log.getEmail());

            html += "<div class='form-group'>";
            html += "<label class='col-md-2 control-label'>First Name</label>";
            html += "<div class='col-md-10'>";
            if (edit) {
                html += "<input required type='text' class='form-control' value='" + d.getCabDriverDetails().getFirstName() + "'/>";
            } else {
                html += "<p class='form-control-static'>" + d.getCabDriverDetails().getFirstName() + "</p>";
            }
            html += "</div>";
            html += "</div>";

            html += "<div class='form-group'>";
            html += "<label class='col-md-2 control-label'>Last Name</label>";
            html += "<div class='col-md-10'>";
            if (edit) {
                html += "<input required type='text' class='form-control' value='" + d.getCabDriverDetails().getLastName() + "' />";
            } else {
                html += "<p class='form-control-static'>" + d.getCabDriverDetails().getLastName() + "</p>";
            }
            html += "</div>";
            html += "</div>";

            html += "<div class='form-group'>";
            html += "<label class='col-md-2 control-label'>Date of Birth</label>";
            html += "<div class='col-md-10'>";
            if (edit) {
                html += "<input required type='date' class='form-control' value='" + d.getCabDriverDetails().getDob() + "' />";
            } else {
                html += "<p class='form-control-static'>" + d.getCabDriverDetails().getDob() + "</p>";
            }
            html += "</div>";
            html += "</div>";

            html += "<div class='form-group'>";
            html += "<label class='col-md-2 control-label'>Address1</label>";
            html += "<div class='col-md-10'>";
            if (edit) {
                html += "<input required type='text' class='form-control' value='" + d.getCabDriverDetails().getAddress1() + "' />";
            } else {
                html += "<p class='form-control-static'>" + d.getCabDriverDetails().getAddress1() + "</p>";
            }
            html += "</div>";
            html += "</div>";

            html += "<div class='form-group'>";
            html += "<label class='col-md-2 control-label'>Address2</label>";
            html += "<div class='col-md-10'>";
            if (edit) {
                html += "<input required type='text' class='form-control' value='" + d.getCabDriverDetails().getAddress2() + "'/>";
            } else {
                html += "<p class='form-control-static'>" + d.getCabDriverDetails().getAddress2() + "</p>";
            }
            html += "</div>";
            html += "</div>";

            return html;
        } catch (Exception e) {
            return "Unable to load.";
        } finally {
            ses.close();
        }
    }

    public String getCurrentOrderDetails(LoginDetails log) {
        JSONArray array = new JSONArray();
        HashMap map = new HashMap();
        try {
            Db.CabDriver d = getCabDriverByEmail(log.getEmail());
            Db.ServiceOrder o = getCurrentOrder(d);

            if (o != null) {

                map.put("id", o.getServiceOrderId());
                map.put("time", new SimpleDateFormat("hh:mm:ss a").format(o.getOrderedAt()));
                map.put("cab", o.getCab().getCabId() + " - " + o.getCab().getVehicleColour().getVehicleColour() + " " + o.getCab().getVehicleModel().getVehicleMake().getVehicleMake() + " " + o.getCab().getVehicleModel().getVehicleModel());
                map.put("km", o.getKm());

                HashMap user = new HashMap();
                Db.User u = o.getServiceInvoice().getUser();

                user.put("fname", u.getUserDetails().getFirstname());
                user.put("lname", u.getUserDetails().getLastname());
                user.put("gender", u.getUserDetails().getGender().getType());
                user.put("mobile", u.getUserDetails().getMobile());
                user.put("pic", u.getUserDetails().getProfilePicture().getProfilePicUrl());

                map.put("user", user);

                HashMap myloc = new HashMap();
                myloc.put("lat", d.getCabDriverLocation().getLattitude());
                myloc.put("lng", d.getCabDriverLocation().getLongitude());
                map.put("my_location", myloc);

                HashMap start = new HashMap();
                start.put("lat", o.getStartPoint().getLatitude());
                start.put("lng", o.getStartPoint().getLongitude());
                map.put("start_point", start);

                HashMap end = new HashMap();
                end.put("lat", o.getDestination().getLatitude());
                end.put("lng", o.getDestination().getLongitude());
                map.put("end_point", end);

                map.put("status", "FOUND");
            } else {
                map.put("status", "NO_ORDER");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "ERROR");
        } finally {
            System.gc();
            ses.close();
        }
        array.add(map);
        System.out.println(array.toJSONString());
        return array.toJSONString();
    }

    private Db.ServiceOrder getCurrentOrder(Db.CabDriver d) throws Exception {
        Criteria cr = ses.createCriteria(Db.ServiceOrder.class);
        cr.add(Restrictions.eq("cabDriver", (Db.CabDriver) ses.load(Db.CabDriver.class, d.getCabDriverId())));
        cr.add(Restrictions.or(Restrictions.eq("orderStatus", (Db.OrderStatus) ses.load(Db.OrderStatus.class, Model.Invoice.ORDER_STATUS_REFERED)), Restrictions.eq("orderStatus", (Db.OrderStatus) ses.load(Db.OrderStatus.class, Model.Invoice.ORDER_STATUS_PROCESSING))));
        return (Db.ServiceOrder) cr.uniqueResult();
    }

    public void reached(int parseInt) {
        try {
            tr = ses.beginTransaction();
            Db.ServiceOrder o = (Db.ServiceOrder) ses.load(Db.ServiceOrder.class, parseInt);
            o.setOrderStatus((OrderStatus) ses.load(Db.OrderStatus.class, Invoice.ORDER_STATUS_PROCESSING));
            ses.update(o);
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
    }

    public void completed(int parseInt) {
        try {
            tr = ses.beginTransaction();
            Db.ServiceOrder o = (Db.ServiceOrder) ses.load(Db.ServiceOrder.class, parseInt);
            o.setOrderStatus((OrderStatus) ses.load(Db.OrderStatus.class, Invoice.ORDER_STATUS_COMPLETED));
            ses.update(o);

            LoginDetails l = o.getCabDriver().getLoginDetails();
            l.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class, Model.UserStatus.ONLINE));
            ses.update(l);

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
    }

    public String getStatus(Db.LoginDetails log) {
        Db.LoginDetails load = (Db.LoginDetails) ses.load(Db.LoginDetails.class, log.getLoginDetailsId());
        ses.close();
        return load.getLoginStatus().getLoginStatus();
    }
}
