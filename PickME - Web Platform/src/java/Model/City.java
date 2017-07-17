package Model;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class City {

    private static void refresh() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    private static Session ses;

    public static void main(String[] args) {
        saveOrGetCity("ChIJuehrsIo54zoRUlCIHufkkB8");
    }

    static Db.City saveOrGetCity(String city) {
        refresh();
        Transaction tr = ses.beginTransaction();
        Db.City c;

        Criteria cr = ses.createCriteria(Db.City.class);
        cr.add(Restrictions.eq("googlePlaceId", city));

        if (cr.uniqueResult() != null) {
            c = (Db.City) cr.uniqueResult();
        } else {
            c = new Db.City();
            c.setGooglePlaceId(city);

            ses.save(c);
        }
        tr.commit();
        return c;
    }

    public City() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    public List<Db.City> searchCity(String keyword) {
        Criteria cr = ses.createCriteria(Db.City.class);
        cr.add(Restrictions.like("description", keyword, MatchMode.START));
        System.out.println(":EXEC");
        return cr.list();
    }

    public List<Db.City> getAllCities() {
        Criteria cr = ses.createCriteria(Db.City.class);
        cr.addOrder(Order.asc("description"));
        return cr.list();
    }
}
