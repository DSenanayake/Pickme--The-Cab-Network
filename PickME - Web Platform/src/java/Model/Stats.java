package Model;

import java.util.HashMap;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.json.simple.JSONArray;

/**
 *
 * @author Deeptha
 */
public class Stats {

    private static SessionFactory sf;
    private static Session ses;

    public int getDrivers(Db.LoginDetails d) {
        Query qr = ses.createQuery("FROM CabDriver c WHERE c.serviceProvider.loginDetails='" + d.getLoginDetailsId() + "'");
        return qr.list().size();
    }

    public Stats() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public static void main(String[] args) {
        System.out.println(new Stats().getLiveStats());
    }

    public String getLiveStats() {
        JSONArray array = new JSONArray();
        HashMap map = new HashMap();

        try {
            map.put("on_c", (long) ses.createQuery("SELECT COUNT(c.cabDriverId) FROM CabDriver c WHERE c.loginDetails.loginStatus='" + UserStatus.ONLINE + "'").uniqueResult());
            map.put("in_c", (long) ses.createQuery("SELECT COUNT(c.cabDriverId) FROM CabDriver c WHERE c.loginDetails.loginStatus='" + UserStatus.IN_A_SERVICE + "'").uniqueResult());
//            map.put("on_s", (long) ses.createQuery("SELECT COUNT(c.serviceProviderId) FROM ServiceProvider c WHERE c.loginDetails.loginStatus='" + UserStatus.ONLINE + "'").uniqueResult());
//            map.put("reg_s", (long) ses.createQuery("SELECT COUNT(c.serviceProviderId) FROM ServiceProvider ").uniqueResult());
//            map.put("reg_c", (long) ses.createQuery("SELECT COUNT()").uniqueResult());
//            map.put("c", (long) ses.createQuery("SELECT COUNT(c.cityId) FROM City c").uniqueResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
            ses.close();
        }
        array.add(map);
        return array.toJSONString();
    }

}
