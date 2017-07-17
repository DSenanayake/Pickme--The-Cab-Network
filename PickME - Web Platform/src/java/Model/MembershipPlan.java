package Model;

import Db.MembershipDuration;
import Db.MembershipStatus;
import Db.MembershipUpgradeHistory;
import Db.PaymentStatus;
import Db.ServiceProvider;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class MembershipPlan {

    private static Session ses;
    public static final int NO_CURRENT_MEMBERSHIP = -1;
    public static final int SAVED = 1;
    public static final int NOT_SAVED = 2;

    public static final int ACTIVATED = 1;
    public static final int SUSPENDED = 2;
    public static final int PENDING = 3;
    public static final int EXPIRED = 4;
    public static final int REJECTED = 5;

    private static void refresh() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    public static MembershipDuration getDurationById(int dura) {
        refresh();
        return (MembershipDuration) ses.load(Db.MembershipDuration.class, dura);
    }

    public static Db.MembershipPlan getPlanById(int plan) {
        refresh();
        return (Db.MembershipPlan) ses.load(Db.MembershipPlan.class, plan);
    }

    public static MembershipUpgradeHistory upgradeMembershipPlan(int plan, int duration, int sp) {
        refresh();
        throw new UnsupportedOperationException("Method not Completed!");
    }

    public static String getNewMemberhsipRequests() throws Exception {
        refresh();
        String html = "<ul class='list-group'>";
        Criteria cr = ses.createCriteria(Db.MembershipUpgradeHistory.class);
        cr.add(Restrictions.in("serviceProvider", ses.createQuery("FROM ServiceProvider sp WHERE sp.membershipStatus='" + MembershipPlan.PENDING + "'").list()));
        cr.add(Restrictions.isNull("activatedDatetime"));
        List<Db.MembershipUpgradeHistory> list = cr.list();

        for (MembershipUpgradeHistory history : list) {
            Db.MembershipPaymentHistory h = (Db.MembershipPaymentHistory) (!history.getMembershipPaymentHistories().isEmpty() ? history.getMembershipPaymentHistories().toArray()[0] : null);

            html += "<li class='list-group-item' style='font-size:16px;'>"
                    + "<div class='row'>"
                    + "<div class='col-md-12 page-header'>" + history.getServiceProvider().getServiceProviderName() + " <label class='label label-danger'>SUBSCRIBED FOR</label> " + history.getMembershipDuration().getDurationMonths().getMonths() + " Month(s) <b>FROM</b> " + history.getMembershipPlan().getMembershipPlanName() + " Plan </div>"
                    + "<div>"
                    + "<div class='col-lg-8'>"
                    + "<label>Payment Status : </label> " + (h != null ? h.getPaymentStatus().getPaymentStatus() : "No Payment.")
                    + "<label>Received Payment : </label> <a onclick='viewPayment(" + (h != null ? h.getMembershipPaymentId() : -1) + ")'><b>" + (h != null ? h.getCurrencyRate() * h.getPaidAmount() : 0.0) + "</b></a>"
                    + "</div>"
                    + "<div class='col-lg-4'>"
                    + "<button class='btn btn-success' onclick='activateMembership(\"" + history.getMembershipUpgradeId() + "\")' data-toggle='tooltip' title='Activate Membership'>Activate</button>"
                    + "<a style='margin-left:5px;cursor:pointer' onclick='rejectMembership()' data-toggle='tooltip' title='Reject Membership Request'>Reject</a>"
                    + "</div>"
                    + "</li>";
        }

        return (list.isEmpty() ? "<li class='list-group-item' style='text-align:center:'>You Don't have any Membership Requests.</li>" : "") + html + "</ul>";
    }

    public static void activateMembership(int id) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.MembershipUpgradeHistory history = (Db.MembershipUpgradeHistory) ses.load(Db.MembershipUpgradeHistory.class, id);
        history.setActivatedDatetime(new Date());

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, history.getMembershipDuration().getDurationMonths().getMonths());
        history.setExpiresDatetime(now.getTime());
        ses.update(history);

        Db.ServiceProvider provider = history.getServiceProvider();
        provider.setMembershipStatus((MembershipStatus) ses.load(Db.MembershipStatus.class, ACTIVATED));
        ses.update(provider);

        tr.commit();
    }

    public void rejectecMembershipRequest(int id, String reason) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.MembershipUpgradeHistory history = (Db.MembershipUpgradeHistory) ses.load(Db.MembershipUpgradeHistory.class, id);

        ServiceProvider sp = history.getServiceProvider();
        sp.setMembershipStatus((MembershipStatus) ses.load(Db.MembershipStatus.class, REJECTED));
        ses.update(sp);

        Date d = new Date();
        history.setActivatedDatetime(d);
        history.setExpiresDatetime(d);
        ses.update(history);

        tr.commit();
    }

    public MembershipPlan() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    public java.util.List<Db.MembershipPlan> getMembershipPlans() {
        Criteria cr = ses.createCriteria(Db.MembershipPlan.class);
        cr.add(Restrictions.isNotEmpty("membershipDurations"));
        return cr.list();
    }

    public static List<MembershipDuration> getDurationsById(int plan_id) {
        refresh();
        Criteria cr = ses.createCriteria(Db.MembershipDuration.class);
        cr.addOrder(Order.asc("durationMonths"));
        cr.add(Restrictions.eq("membershipPlan", (Db.MembershipPlan) ses.load(Db.MembershipPlan.class, plan_id)));
        return cr.list();
    }

    public static int getCurrentMembershipPlan(int serviceProvider) {
        refresh();
        try {
            Criteria cr = ses.createCriteria(Db.MembershipUpgradeHistory.class);
            cr.add(Restrictions.eq("serviceProvider", (Db.ServiceProvider) ses.load(Db.ServiceProvider.class, serviceProvider)));
            cr.setProjection(Projections.max("membershipUpgradeId"));

            int c_m = (int) cr.uniqueResult();
            return c_m;
        } catch (Exception e) {
            return NO_CURRENT_MEMBERSHIP;
        }
    }

    public static int savePaymentHistory(String transactionId, double paidAmount,double paypalAmt, Date dateTime, int upgradeId, int status) {
        try {
            refresh();
            Transaction tr = ses.beginTransaction();
            Db.MembershipPaymentHistory paymentHistory = new Db.MembershipPaymentHistory();

            paymentHistory.setPaypalTransactionId(transactionId);
            paymentHistory.setPaidAmount(paidAmount);
            paymentHistory.setDateTime(dateTime);
            paymentHistory.setMembershipUpgradeHistory((MembershipUpgradeHistory) ses.load(Db.MembershipUpgradeHistory.class, upgradeId));
            paymentHistory.setPaymentStatus((PaymentStatus) ses.load(Db.PaymentStatus.class, status));
            paymentHistory.setCurrencyRate(Controller.CurrencyConverter.convertUSDtoLKR(1));
            paymentHistory.setPaypalFee(paypalAmt);
            ses.save(paymentHistory);

            tr.commit();
            return SAVED;
        } catch (Exception e) {
            return NOT_SAVED;
        }
    }

}
