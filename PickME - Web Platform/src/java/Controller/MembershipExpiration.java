package Controller;

import Db.MembershipStatus;
import Model.MembershipPlan;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class MembershipExpiration {

    private static SessionFactory sf;
    private Session ses;
    private Transaction tr;

    public MembershipExpiration() {
        if (sf == null) {
            sf = Connection.getSessionFactory();
        }
        ses = sf.openSession();
    }

    public void doExpire() throws Exception {
        tr = ses.beginTransaction();
        try {
            List<Integer> integers = ses.createQuery("SELECT MAX(h.membershipUpgradeId) FROM MembershipUpgradeHistory h WHERE h.expiresDatetime<NOW() AND h.expiresDatetime IS NOT NULL AND h.serviceProvider.membershipStatus='" + MembershipPlan.ACTIVATED + "' GROUP BY(h.serviceProvider)").list();

            for (Integer i : integers) {
                Db.MembershipUpgradeHistory history = (Db.MembershipUpgradeHistory) ses.load(Db.MembershipUpgradeHistory.class, i);
                Db.ServiceProvider provider = history.getServiceProvider();
                provider.setMembershipStatus((MembershipStatus) ses.load(Db.MembershipStatus.class, Model.MembershipPlan.EXPIRED));
                ses.update(provider);
            }

            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            throw e;
        } finally {
            ses.close();
        }
    }

    public static void main(String[] args) throws Exception {
        new MembershipExpiration().doExpire();
    }
}
