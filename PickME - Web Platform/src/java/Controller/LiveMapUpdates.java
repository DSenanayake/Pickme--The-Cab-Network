package Controller;

import Db.CabDriver;
import Model.UserStatus;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;

public class LiveMapUpdates {

    private static SessionFactory sf;
    private Session ses;
    private Transaction tr;

    public LiveMapUpdates() {
        if (sf == null) {
            sf = Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public String getCabLocations(Db.LoginDetails log) {
        String status = "OK";

        JSONArray array = new JSONArray();
        HashMap map = new HashMap();
        try {

            if (log != null) {
                Db.ServiceProvider provider = (Db.ServiceProvider) ses.createQuery("FROM ServiceProvider sp WHERE sp.loginDetails='" + log.getLoginDetailsId() + "'").uniqueResult();
                if (provider != null) {
                    map.put("id", provider.getServiceProviderId());
                    map.put("cabs", provider.getCabDrivers().size());
                    map.put("lat", provider.getServiceProviderLocationDetails().getLattitude());
                    map.put("lng", provider.getServiceProviderLocationDetails().getLongitude());
                } else {
                    status = "NO_LOG";
                }
            } else {
                status = "NO_LOG";
            }

        } catch (Exception e) {
            status = "ERROR";
        } finally {
            ses.close();
            System.gc();
        }
        map.put("status", status);

        array.add(map);
        System.out.println(array.toJSONString());
        return array.toJSONString();
    }

    public String getCabs(int sp) {
        JSONArray array = new JSONArray();

        try {
            Query qr = ses.createQuery("FROM CabDriver c WHERE c.serviceProvider='" + sp + "' AND c.loginDetails.loginStatus='" + UserStatus.ONLINE + "' ");

            List<Db.CabDriver> list = qr.list();
            for (Db.CabDriver d : list) {
                HashMap cab = new HashMap();

                cab.put("label", d.getCabDriverDetails().getFirstName() + " " + d.getCabDriverDetails().getLastName());
                cab.put("lat", d.getCabDriverLocation().getLattitude());
                cab.put("lng", d.getCabDriverLocation().getLongitude());
                cab.put("pic", d.getProfilePicture().getProfilePicUrl());
                Db.DriverContactNo no = (Db.DriverContactNo) (d.getDriverContactNos() == null ? null : d.getDriverContactNos().isEmpty() ? null : d.getDriverContactNos().toArray()[0]);
                cab.put("mobile", no == null ? "No Contact Number" : no.getContactNo());
                array.add(cab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
        return array.toJSONString();
    }

    public static void main(String[] args) {
        System.out.println(new LiveMapUpdates().getCabs(1));
    }

}
