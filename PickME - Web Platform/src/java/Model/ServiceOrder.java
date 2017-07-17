package Model;

import Db.Cab;
import Db.CabDriver;
import Db.LoginStatus;
import Db.OrderStatus;
import Db.VehicleStatus;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;

public class ServiceOrder {

    private static Session ses;

    public static String referOrder(int order, int driver, String cab) {
        refresh();
        String status = "OK";
        try {
            Transaction tr = ses.beginTransaction();

            Db.ServiceOrder so = (Db.ServiceOrder) ses.load(Db.ServiceOrder.class, order);

            so.setCab((Cab) ses.load(Db.Cab.class, cab));
            so.setCabDriver((CabDriver) ses.load(Db.CabDriver.class, driver));
            so.setOrderStatus((OrderStatus) ses.load(Db.OrderStatus.class , Model.Invoice.ORDER_STATUS_REFERED));
            ses.update(so);

            Db.Cab c = (Db.Cab) ses.load(Db.Cab.class, cab);
            c.setVehicleStatus((VehicleStatus) ses.load(Db.VehicleStatus.class, Model.Vehicle.IN_A_SERVICE));
            ses.update(c);
            
            Db.CabDriver cabDriver = (Db.CabDriver) ses.load(Db.CabDriver.class, driver);
            Db.LoginDetails details = cabDriver.getLoginDetails();
            details.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class , UserStatus.IN_A_SERVICE));
            ses.update(details);
            
            tr.commit();
        } catch (Exception e) {
            status = "ERROR";
        }
        return status;
    }

    private ServiceOrder() {
        refresh();
    }

    public static String getOrderDetails(int order) {
        refresh();
        JSONArray array = new JSONArray();
        HashMap orderMap = new HashMap();
        String status;

        try {
            Db.ServiceOrder so = (Db.ServiceOrder) ses.load(Db.ServiceOrder.class, order);
            if (so != null) {

                orderMap.put("id", so.getServiceOrderId());
                orderMap.put("cost_per_km", so.getCostPerKm());
                orderMap.put("km", so.getKm());
                orderMap.put("order_status", so.getOrderStatus().getOrderStatus());
                orderMap.put("ordered_at", new SimpleDateFormat("hh:mm a").format(so.getOrderedAt()));
                orderMap.put("invoice_id", so.getServiceInvoice().getServiceInvoiceId());

                HashMap startpoint = new HashMap();

                startpoint.put("latitude", so.getStartPoint().getLatitude());
                startpoint.put("longitude", so.getStartPoint().getLongitude());

                orderMap.put("start_point", startpoint);

                HashMap destination = new HashMap();

                destination.put("latitude", so.getDestination().getLatitude());
                destination.put("longitude", so.getDestination().getLongitude());

                orderMap.put("destination", destination);
                orderMap.put("total", so.getTotal());
                orderMap.put("service_provider_id", so.getServiceProvider().getServiceProviderId());

                if (so.getCab() != null) {
                    orderMap.put("cab_id", so.getCab().getCabId());
                }

                if (so.getCabDriver() != null) {
                    orderMap.put("cab_driver_id", so.getCabDriver().getCabDriverId());
                }
                boolean empty = so.getServiceInvoice().getServicePaymentHistories().isEmpty();

                if (!empty) {
                    Db.ServicePaymentHistory history = (Db.ServicePaymentHistory) so.getServiceInvoice().getServicePaymentHistories().toArray()[0];
                    orderMap.put("payment_history_id", history.getServicePaymentId());
                    orderMap.put("payment_status", history.getPaymentStatus().getPaymentStatus());
                } else {
                    orderMap.put("payment_history_id", "-1");
                    orderMap.put("payment_status", "No Payment");
                }

                HashMap client = new HashMap();

                Db.User user = so.getServiceInvoice().getUser();

                client.put("firstname", user.getUserDetails().getFirstname());
                client.put("lastname", user.getUserDetails().getLastname());
                client.put("email", user.getLoginDetails().getEmail());
                client.put("profile_pic", user.getUserDetails().getProfilePicture().getProfilePicUrl());
                client.put("mobile", user.getUserDetails().getMobile());

                orderMap.put("client", client);

                status = "OK";
            } else {
                status = "NOT_FOUND";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "ERROR";
        }

        HashMap map = new HashMap();

        map.put("status", status);
        map.put("order", orderMap);

        array.add(map);
        return array.toJSONString();
    }

    private static void refresh() {
//        if (ses == null) {
            ses = Controller.Connection.getSessionFactory().openSession();
//        }
    }

}
