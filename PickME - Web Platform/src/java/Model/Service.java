package Model;

import Db.LoginDetails;
import Db.LoginStatus;
import Db.Logo;
import Db.MembershipDuration;
import Db.MembershipStatus;
import Db.ServiceProvider;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;

public class Service {

    public static final int DEFAULT_LOGO = 1;
    public static final int NOT_SUBSCRIBED = -1;
    private static Session ses;
    private static SessionFactory sf;
    public static final int NO_USER = -1;

    synchronized private static void refresh() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public static List<ServiceProvider> getServicesByCity(String place_id) {
        refresh();
        try {
            Query qr = ses.createQuery("FROM ServiceProvider sp where sp.serviceProviderLocationDetails.city.googlePlaceId='" + place_id + "' "
                    + "and sp.loginDetails.loginStatus='" + UserStatus.ONLINE + "' AND sp.membershipStatus='" + MembershipPlan.ACTIVATED + "' ");

            System.out.println("[ FIND SERV ] - " + qr.list().size() + " Service(s) Found !");
            List<Db.ServiceProvider> providers = qr.list();

            return providers;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ FIND SERV ] - Error : " + e);
        }
        return null;
    }

    public static ServiceProvider getServiceById(int id) {
        refresh();
        try {
            Db.ServiceProvider provider = (Db.ServiceProvider) ses.load(Db.ServiceProvider.class, id);
            return provider;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Db.ServiceOrder> getCurrentServiceOrders(String email) {
        refresh();
        try {
            Query qr = ses.createQuery("FROM ServiceOrder so WHERE so.serviceProvider.loginDetails.email='" + email + "' AND so.orderStatus='" + Model.Invoice.ORDER_STATUS_ORDERED + "' ");

            return qr.list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ GET SERV ORDRS ] - Error : " + e);
            return null;
        } finally {
            System.gc();
        }
    }

    public static String getAvailableCabsAndDriversHtml(String email) throws Exception {
        refresh();
        String html = getOnlineCabs(email);
        html += getOnlineDrivers(email);
        return html;
    }

//    public static void main(String[] args) throws Exception {
//        getAvailableCabsAndDriversHtml("chalithadeeptha@gmail.com");
//    }
    static boolean na = false;

    private static String getOnlineCabs(String email) {
        String html = "";
        Query cabs = ses.createQuery("FROM Cab cab WHERE cab.serviceProvider.loginDetails.email='" + email + "' AND cab.vehicleStatus='" + Model.Vehicle.ONLINE + "'");
        List<Db.Cab> list = cabs.list();

        if (!list.isEmpty()) {
            html += "<div class='form-group'>"
                    + "<label class='control-label'>"
                    + "Select Cab :"
                    + "</label>"
                    + "<select id='cab' class='form-control'>";
            for (Db.Cab cab : list) {
                html += "<option value='" + cab.getCabId() + "'>" + cab.getCabId() + " - " + cab.getVehicleColour().getVehicleColour() + " " + cab.getVehicleModel().getVehicleMake().getVehicleMake() + " " + cab.getVehicleModel().getVehicleModel() + "</option>";
            }
            html += "</select>"
                    + "</div>";
        } else {
            na = true;
            html += "<div class='form-group'>"
                    + "<h4 class='text text-warning'>Sorry, there is no resources available to Complete this Order. If you can't complete the order you can use these options.</h4>"
                    + "<button class='btn btn-danger'>Request for Refund</button> or"
                    + " Assign Unregistered <button class='btn btn-default'>Cab</button> to this Order."
                    + "</div>";
        }
        return html;
    }

    private static String getOnlineDrivers(String email) {
        String html = "";
        Query drtivers = ses.createQuery("FROM CabDriver cd WHERE cd.serviceProvider.loginDetails.email='" + email + "' AND cd.loginDetails.loginStatus.loginStatusId='" + UserStatus.ONLINE + "'");
        List<Db.CabDriver> list = drtivers.list();

        if (!list.isEmpty()) {
            html += "<div class='form-group'>"
                    + "<label class='control-label'>"
                    + "Select Driver :"
                    + "</label>"
                    + "<select id='driver' class='form-control'>";
            for (Db.CabDriver driver : list) {
                html += "<option value='" + driver.getCabDriverId() + "'>" + driver.getCabDriverDetails().getFirstName() + " " + driver.getCabDriverDetails().getLastName() + " - " + (driver.getDriverContactNos().isEmpty() ? "No Contact Num." : driver.getDriverContactNos().toArray()[0]) + "</option>";
            }
            html += "</select>"
                    + "</div>";
        } else {
            html += "<div class='form-group'>"
                    + (na ? "" : "<button class='btn btn-danger'>Request for Refund</button>")
                    + " Assign Unregistered <button class='btn btn-default'>Driver</button> to this Order."
                    + "</div>";
        }
        return html;
    }

    public static void saveProfilePicture(String email, String path) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.Logo l = new Db.Logo();
        l.setLogoUrl(path);
        ses.save(l);
        Db.ServiceProvider sp = (Db.ServiceProvider) ses.createQuery("FROM ServiceProvider sp where sp.loginDetails.email='" + email + "'").uniqueResult();
        if (sp != null) {
            sp.setLogo(l);
            ses.update(sp);
        } else {
            throw new Exception();
        }

        tr.commit();
    }

    public static String filterServices(int start, String sort, String avail, String keywords, String city, String area_max, String cost_min, String cost_max, String dist_min, String dist_max, String cabs_min, String cabs_max, String make, String model) throws Exception {
        refresh();
        int count, rip;
        String result = "<ul class='list-group'>", pagination = "<nav><ul style='margin:0;' class='pagination'>";

        String sort_q = "", available_q = "";

//        avail = avail != null ? avail : "ALL";
        if (avail.equalsIgnoreCase("ONLINE")) {
            available_q = " AND sp.loginDetails.loginStatus='" + UserStatus.ONLINE + "' ";
        } else if (avail.equalsIgnoreCase("OFFLINE")) {
            available_q = " AND sp.loginDetails.loginStatus='" + UserStatus.OFFLINE + "' ";
        }

        if (sort.equalsIgnoreCase("NAME")) {
            sort_q = "ORDER BY sp.serviceProviderName asc ";
        } else if (sort.equalsIgnoreCase("COST_LOW")) {
            sort_q = "ORDER BY sp.serviceDetails.costPerKm desc ";
        } else if (sort.equalsIgnoreCase("COST_HIGH")) {
            sort_q = "ORDER BY sp.serviceDetails.costPerKm asc ";
        }

        String BIG_QUERY = "FROM ServiceProvider sp "
                + "WHERE sp.serviceProviderName LIKE '%" + (keywords != null ? keywords : "") + "%' "
                + available_q + " "
                + (city != null ? !city.isEmpty() ? " AND sp.serviceProviderLocationDetails.city.googlePlaceId='" + city + "' " : "" : "")
                + (area_max != null ? " AND sp.serviceDetails.coverageArea>=0 AND sp.serviceDetails.coverageArea<=" + area_max + " " : "")
                + (cost_min != null ? " AND sp.serviceDetails.costPerKm>=" + cost_min + " " : "")
                + (cost_max != null ? " AND sp.serviceDetails.costPerKm<=" + cost_max + " " : "")
                + (dist_min != null ? " AND sp.serviceDetails.minimumDistance>=" + dist_min + " " : "")
                + (dist_max != null ? " AND sp.serviceDetails.minimumDistance<=" + dist_max + " " : "")
                + (cabs_min != null ? "AND (SELECT COUNT(c.cabId) FROM Cab c WHERE c.serviceProvider=sp.serviceProviderId)>=" + cabs_min + " " : "")
                + (cabs_max != null ? "AND (SELECT COUNT(c.cabId) FROM Cab c WHERE c.serviceProvider=sp.serviceProviderId)<=" + cabs_max + " " : "")
                + " AND sp.membershipStatus='" + MembershipPlan.ACTIVATED + "'"
                + (make != null & model != null ? (make.equals("-1") ? "" : " AND sp.serviceProviderId IN(SELECT c.serviceProvider FROM Cab c WHERE c.vehicleModel.vehicleMake='" + make + "' " + (model.equals("-1") ? "" : " AND c.vehicleModel='" + model + "' ") + " ) ") : "")
                + sort_q;

//        search query
        Query qr = ses.createQuery(BIG_QUERY);

        System.out.println(qr);
        count = qr.list().isEmpty() ? 0 : qr.list().size();
        rip = qr.list().size() % 5 == 0 ? qr.list().size() / 5 : (qr.list().size() / 5) + 1;

        qr.setFirstResult(start);
        qr.setMaxResults(5);
        List<Db.ServiceProvider> providers = qr.list();

        for (ServiceProvider provider : providers) {
            result += "<a style='cursor:pointer' class='list-group-item'><div class='row'><img class='col-md-2' src='" + provider.getLogo().getLogoUrl() + "' width='90px'><div class='col-md-10'><h3 class='list-group-item-heading'>" + provider.getServiceProviderName() + "</h3><p class='list-group-item-text'>" + GooglePlaceDetails.getCityById(provider.getServiceProviderLocationDetails().getCity().getGooglePlaceId()) + "</p></div></div></a>";
        }
//        pagination
        for (int i = 0; i < rip; i++) {
            pagination += "<li><a onclick='setStart(" + (i * 5) + ")' style='cursor:pointer;'>" + (i + 1) + "</a></li>";
        }

        if (rip == 0) {
            result += "<li class='list-group-item' style='text-align:center;'><img src='images/other/restricted.png' width='150px'><h3 class='text text-warning'>Sorry, No result found !</h3></li>";
            pagination += "No Result Found !.";
        }

        JSONArray array = new JSONArray();
        HashMap json = new HashMap();

        json.put("count", count);
        json.put("result", result + "</ul>");
        json.put("pagination", pagination + "</ul></nav>");

        array.add(json);
        System.out.println("[ JSON OUT ] - " + array.toJSONString());

        return array.toJSONString();
    }

    public static String validateCompleteProfileForm(HttpServletRequest request) {
        String result;
        try {

            String name = request.getParameter("name");
            String area = request.getParameter("coverage");
            String distance = request.getParameter("distance");
            String cost = request.getParameter("cost");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String city = request.getParameter("city");
            String tel1 = request.getParameter("tel1");
            String password = request.getParameter("password");

            System.out.println(name + "\t"
                    + area + "\t"
                    + distance + "\t"
                    + cost + "\t"
                    + address1 + "\t"
                    + address2 + "\t"
                    + latitude + "\t"
                    + longitude + "\t"
                    + city + "\t"
                    + tel1 + "\t"
                    + password + "\t"
            );
            if (!name.isEmpty()) {
                try {
                    Double.parseDouble(area);
                    try {
                        Double.parseDouble(distance);
                        try {
                            Double.parseDouble(cost);
                            if (!address1.isEmpty()) {
                                if (!address2.isEmpty()) {
                                    if (!latitude.isEmpty()) {
                                        if (!longitude.isEmpty()) {
                                            if (!city.isEmpty()) {
                                                if (!tel1.isEmpty()) {
                                                    if (tel1.length() == 9) {
                                                        if (!password.isEmpty()) {
                                                            if (validatePassword(password)) {
                                                                result = "OK";
                                                            } else {
                                                                result = "PASSWORD:FORMAT";
                                                            }
                                                        } else {
                                                            result = "PASSWORD:NULL";
                                                        }
                                                    } else {
                                                        result = "TEL:FORMAT";
                                                    }
                                                } else {
                                                    result = "TEL:NULL";
                                                }
                                            } else {
                                                result = "CITY:NULL";
                                            }
                                        } else {
                                            result = "LNG:NULL";
                                        }
                                    } else {
                                        result = "LAT:NULL";
                                    }
                                } else {
                                    result = "ADDRESS2:NULL";
                                }
                            } else {
                                result = "ADDRESS1:NULL";
                            }
                        } catch (NumberFormatException e) {
                            result = "COST:FORMAT";
                        }
                    } catch (NumberFormatException e) {
                        result = "DISTANCE:FORMAT";
                    }
                } catch (NumberFormatException e) {
                    result = "AREA:FORMAT";
                }
            } else {
                result = "NAME:NULL";
            }
        } catch (Exception e) {
            result = "ERROR";
        }

        return result;
    }

    public static String completeProfile(HttpServletRequest request) throws Exception {
        refresh();
        Db.LoginDetails log = (Db.LoginDetails) request.getSession().getAttribute("CURRENT_USER");
        if (log != null) {
            Db.ServiceProvider p = getServiceByEmail(log.getEmail());
            if (p != null) {
                Transaction tr = ses.beginTransaction();
                try {
                    String name = request.getParameter("name");
                    String area = request.getParameter("coverage");
                    String distance = request.getParameter("distance");
                    String cost = request.getParameter("cost");
                    String address1 = request.getParameter("address1");
                    String address2 = request.getParameter("address2");
                    String latitude = request.getParameter("latitude");
                    String longitude = request.getParameter("longitude");
                    String city = request.getParameter("city");
                    String tel1 = request.getParameter("tel1");
                    String password = request.getParameter("password");

                    Db.ServiceDetails service = new Db.ServiceDetails();
                    service.setCoverageArea(Double.parseDouble(area));
                    service.setMinimumDistance(Double.parseDouble(distance));
                    service.setCostPerKm(Double.parseDouble(cost));
                    ses.save(service);

                    Db.ServiceProviderLocationDetails loc = new Db.ServiceProviderLocationDetails();
                    loc.setAddress1(address1);
                    loc.setAddress2(address2);
                    loc.setLattitude(Double.parseDouble(latitude));
                    loc.setLongitude(Double.parseDouble(longitude));
                    loc.setCity(Model.City.saveOrGetCity(city));
                    ses.save(loc);

                    Db.LoginDetails l = (Db.LoginDetails) ses.load(Db.LoginDetails.class, log.getLoginDetailsId());
                    l.setPassword(password);
                    l.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.ONLINE));
                    ses.update(l);

                    Db.ServiceProvider provider = (Db.ServiceProvider) ses.load(Db.ServiceProvider.class, p.getServiceProviderId());
                    provider.setServiceProviderName(name);
                    provider.setContactNo(tel1);
                    provider.setServiceDetails(service);
                    provider.setServiceProviderLocationDetails(loc);
                    provider.setLoginDetails(l);
                    ses.update(provider);

                    tr.commit();
                    return "OK";
                } catch (Exception e) {
                    tr.rollback();
                    return "ERROR";
                }
            } else {
                return "NO_USER";
            }
        } else {
            return "NO_USER";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GetServiceByEmail">
    private static ServiceProvider getServiceByEmail(String email) {
        refresh();
        try {
            Query qr = ses.createQuery("FROM ServiceProvider sp WHERE sp.loginDetails.email='" + email + "'");
            return (ServiceProvider) qr.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Validate Password">
    private static boolean validatePassword(String password) {
        boolean app = false;
        if (password != null) {
            if (password.length() > 6) {
                boolean uc = false, lc = false, dig = false;
                for (int i = 0; i < password.length(); i++) {
                    if (!uc) {
                        uc = Character.isUpperCase(password.charAt(i));
                    }
                    if (!lc) {
                        lc = Character.isLowerCase(password.charAt(i));
                    }
                    if (!dig) {
                        dig = Character.isDigit(password.charAt(i));
                    }
                }
                app = uc & lc & dig;
            }
        }
        return app;
    }
//</editor-fold>

    static void setOnline(boolean enable, LoginDetails loginDetails) {
        refresh();
        try {
            int status = loginDetails.getLoginStatus().getLoginStatusId();
            if (status == (enable ? Model.UserStatus.OFFLINE : Model.UserStatus.ONLINE)) {
                Transaction tr = ses.beginTransaction();

                Db.LoginDetails log = (Db.LoginDetails) ses.load(Db.LoginDetails.class, loginDetails.getLoginDetailsId());
                log.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class, (enable ? Model.UserStatus.ONLINE : Model.UserStatus.OFFLINE)));
                ses.update(log);

                tr.commit();
                System.out.println("[ STATUS CHANGED ] - " + log.getEmail() + " TO:" + log.getLoginStatus().getLoginStatus());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("[NO USER] TO MAKE CHANGE");
        } catch (Exception w) {
            w.printStackTrace();
        }
    }

    public Service() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    //<editor-fold defaultstate="collapsed" desc="Subsribe Service">
    public int SubscribeService(String name, String email, String tel, int plan, int duration) throws Exception {
        try {
            Transaction tr = ses.beginTransaction();
            Db.LoginDetails ld = new Db.LoginDetails();

            ld.setEmail(email);
            String pass = generatePassword();
            ld.setPassword(pass);
            ld.setLastUpdate(new Date());
            ld.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.NOT_CONFIRMED));
            ld.setUserType((Db.UserType) ses.load(Db.UserType.class, UserType.SERVICE_PROVIDER));

            ses.save(ld);

            Db.ServiceProvider sp = new Db.ServiceProvider();

            sp.setServiceProviderName(name);
            sp.setLoginDetails(ld);
            sp.setLogo((Logo) ses.load(Db.Logo.class, Service.DEFAULT_LOGO));
            sp.setMembershipStatus((MembershipStatus) ses.load(Db.MembershipStatus.class, MembershipPlan.PENDING));
            sp.setContactNo(tel);

            ses.save(sp);

            Db.MembershipUpgradeHistory history = new Db.MembershipUpgradeHistory();
            history.setMembershipPlan((Db.MembershipPlan) ses.load(Db.MembershipPlan.class, plan));
            history.setMembershipDuration((MembershipDuration) ses.load(Db.MembershipDuration.class, duration));
            history.setServiceProvider(sp);

            ses.save(history);

            tr.commit();

            Controller.Services.sendServiceConfirmationEmail(String.valueOf(ld.getLoginDetailsId()), name, email, pass);
            return sp.getServiceProviderId();
        } catch (Exception e) {
            return NOT_SUBSCRIBED;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Generate Password">
    private static String generatePassword() {
        char[] chars = "AaBbCcDdEeFfGgHhIiLlKkMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(chars[r.nextInt(chars.length)]);
        }
        builder.append(r.nextInt(99));
        return builder.toString();
    }
//</editor-fold>

    public static String setMembershipMsg(HttpSession session) {
        String html = "";
        try {
            Db.LoginDetails log = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
            int result = checkMembership(log.getEmail());
            if (result != NO_USER) {
                if (result == MembershipPlan.EXPIRED) {
                    html = "<div class='top-notification-wrapper'>"
                            + "<div class='top-notification'>"
                            + "Your Membership has been Expired. Please <a href='#'>Upgrade</a> your Membership Plan."
                            + "</div>"
                            + "</div>";
                } else if (result == MembershipPlan.PENDING) {
                    html = "<div class='top-notification-wrapper'>"
                            + "<div class='top-notification'>"
                            + "Your Membership Confirmation is still Pending. Please Check After Few Hours."
                            + "</div>"
                            + "</div>";
                } else if (result == MembershipPlan.SUSPENDED) {
                    html = "<div class='top-notification-wrapper'>"
                            + "<div class='top-notification'>"
                            + "Your Membership has been Suspended. Please <a href='#'>Contact</a> the Administrator."
                            + "</div>"
                            + "</div>";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(html);
        return html;
    }

    //<editor-fold defaultstate="collapsed" desc="Check Membership">
    public static int checkMembership(String email) throws Exception {
        refresh();
        int STATUS;
        Query qr = ses.createQuery("SELECT sp.membershipStatus.membershipStatusId FROM ServiceProvider sp WHERE sp.loginDetails.email='" + email + "'");
        STATUS = (int) (qr.uniqueResult() != null ? qr.uniqueResult() : NO_USER);
        return STATUS;
    }
//</editor-fold>

    public static void redirect(HttpServletRequest request, HttpServletResponse response) {
        refresh();
        if (request.getSession().getAttribute("CURRENT_USER") != null) {
            Db.LoginDetails log = (Db.LoginDetails) request.getSession().getAttribute("CURRENT_USER");
            log = (LoginDetails) ses.load(Db.LoginDetails.class, log.getLoginDetailsId());
            int s = log.getLoginStatus().getLoginStatusId();
            if (s == Model.UserStatus.INACTIVATED) {
                try {
                    response.sendRedirect("completeRegistration.jsp");
                } catch (IOException ex) {
                    Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
