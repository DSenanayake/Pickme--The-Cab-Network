package Model;

import Db.VehicleMake;
import Db.VehicleModel;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

public class Vehicle {

    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;
    public static final int IN_A_SERVICE = 3;

    private Session ses;
    private static SessionFactory sf;

    public String getVehicleModels(int make) throws Exception {
        refresh();
        String html = "<option value=\"-1\">Any</option>";
        Criteria cr = ses.createCriteria(Db.VehicleModel.class);
        if (make > -1) {
            cr.add(Restrictions.eq("vehicleMake", (Db.VehicleMake) ses.load(Db.VehicleMake.class, make)));
        }
        List<Db.VehicleModel> models = cr.list();
        for (VehicleModel model : models) {
            html += "<option value=\"" + model.getVehicleModelId() + "\">" + model.getVehicleModel() + "</option>";
        }
        System.gc();
//        close();
        return html;
    }

    public Vehicle() {
        refresh();
    }

    synchronized private void refresh() {
        if (sf == null) {
            sf = Controller.Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public String getVehicleMakes() {
        refresh();
        String html = "<option value=\"-1\">Any</option>";
        try {
            Criteria cr = ses.createCriteria(Db.VehicleMake.class);
            List<Db.VehicleMake> makes = cr.list();

            for (VehicleMake make : makes) {
                html += "<option value=\"" + make.getVehicleMakeId() + "\">" + make.getVehicleMake() + "</option>";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
//            close();
        }

        return html;
    }
;

}
