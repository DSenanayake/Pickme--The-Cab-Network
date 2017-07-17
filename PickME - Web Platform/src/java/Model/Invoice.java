package Model;

import java.util.ArrayList;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import Db.*;
import java.util.Date;

/**
 *
 * @author Deeptha
 */
public class Invoice {

    private static Session ses;

    public static final int STATUS_ERROR = -1;
    public static final int STATUS_OK = -2;

    public static final int PAYMENT_CONFIRMED = 1;
    public static final int AWAITING_PAYMENT = 2;

    public static final int ORDER_STATUS_ORDERED = 1;
    public static final int ORDER_STATUS_REFERED = 2;
    public static final int ORDER_STATUS_PROCESSING = 3;
    public static final int ORDER_STATUS_COMPLETED = 4;
    public static final int ORDER_STATUS_CANCELED = 5;

    public static int savePaymentHistory(String transId, int invoice, double amount,double paypalAmt, double due, Date date, int status, int method) {
        refresh();
        int rVal;
        try {
            Transaction tr = ses.beginTransaction();

            Db.ServicePaymentHistory history = new ServicePaymentHistory();

            history.setTransactionId(transId);
            history.setServiceInvoice((ServiceInvoice) ses.load(Db.ServiceInvoice.class, invoice));
            history.setPaidAmount(amount);
            history.setDueAmount(due);
            history.setDateTime(date);
            history.setPaymentStatus((Db.PaymentStatus) ses.load(Db.PaymentStatus.class, status));
            history.setPaymentMethod((PaymentMethod) ses.load(Db.PaymentMethod.class, method));
            history.setCurrencyRate(Controller.CurrencyConverter.convertUSDtoLKR(1));
            history.setPaypalFee(paypalAmt);
            
            ses.save(history);
            tr.commit();
            rVal = STATUS_OK;
        } catch (Exception e) {
            rVal = STATUS_ERROR;
        }
        return rVal;
    }

    public static int saveCurrentInvoice(ArrayList<CartItem> items, int userId) {
        refresh();
        try {
            Transaction tr = ses.beginTransaction();
            ServiceInvoice invoice = new ServiceInvoice();

            double total = calculateNetAmount(items);

            invoice.setDateTime(new Date());
            invoice.setNetAmount(total);
            invoice.setServiceInvoiceStatus((ServiceInvoiceStatus) ses.load(ServiceInvoiceStatus.class, AWAITING_PAYMENT));
            invoice.setTotalAmount(total);
            invoice.setUser((Db.User) ses.load(Db.User.class, userId));

            ses.save(invoice);

            for (CartItem item : items) {
                if (item.getStatus() == CartItem.STATUS_OK) {
                    Db.ServiceOrder order = new Db.ServiceOrder();

                    order.setKm(item.getKm());
                    order.setOrderStatus((OrderStatus) ses.load(OrderStatus.class, ORDER_STATUS_ORDERED));
                    order.setOrderedAt(new Date());
                    order.setServiceInvoice(invoice);
                    order.setServiceProvider((ServiceProvider) ses.load(ServiceProvider.class, item.getService_provider_id()));
                    order.setTotal(Math.round(item.getAmount() * 100.0) / 100.0);

                    StartPoint point = new StartPoint();

                    point.setLatitude(item.getLocation().getLattitude());
                    point.setLongitude(item.getLocation().getLongitude());
                    ses.save(point);

                    order.setStartPoint(point);

                    Db.Destination d = new Db.Destination();

                    d.setLatitude(item.getDestination().getLattitude());
                    d.setLongitude(item.getDestination().getLongitude());
                    ses.save(d);

                    order.setDestination(d);

                    order.setCostPerKm(item.getCost_per_km());

                    ses.save(order);

                }
            }

            tr.commit();
            return invoice.getServiceInvoiceId();
        } catch (Exception e) {
            return STATUS_ERROR;
        }
    }

    private static Double calculateNetAmount(ArrayList<CartItem> items) {
        double netAmount = 0;
        for (CartItem cartItem : items) {
            if (cartItem.getStatus() == CartItem.STATUS_OK) {
                netAmount += cartItem.getAmount();
            }
        }
        return Math.round(netAmount * 100.0) / 100.0;
    }

    private Invoice() {
        refresh();
    }

    private static void refresh() {
//        if (ses == null) {
            ses = Controller.Connection.getSessionFactory().openSession();
//        }
    }

}
