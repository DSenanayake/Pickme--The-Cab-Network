package Servlets;

import Db.ServiceOrder;
import Model.PaymentStatus;
import Model.Task;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetServiceOrders extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
            if (details != null) {
                int result = Model.Service.checkMembership(details.getEmail());
                if (result == Model.MembershipPlan.ACTIVATED) {
                    String html = "";
                    
                    List<ServiceOrder> orders = Model.Service.getCurrentServiceOrders(details.getEmail());
                    
                    for (ServiceOrder order : orders) {
                        Db.UserDetails u_details = order.getServiceInvoice().getUser().getUserDetails();
                        if (!order.getServiceInvoice().getServicePaymentHistories().isEmpty()) {
                            Db.ServicePaymentHistory history = (Db.ServicePaymentHistory) order.getServiceInvoice().getServicePaymentHistories().toArray()[0];
                            
                            if (history.getPaymentStatus().getPaymentStatusId() == PaymentStatus.COMPLETED) {
                                
                                html += "<a href='#order' onclick=\"viewServiceOrder(" + order.getServiceOrderId() + ")\" class=\"list-group-item list-group-item-info\">"
                                        + "<h5 class=\"list-group-item-heading\"><b>" + u_details.getFirstname() + " " + u_details.getLastname() + "</b></h5>"
                                        + "<p class=\"list-group-item-text text-capitalize text-muted\">"
                                        + "Distance : " + order.getKm() + " Km"
                                        + "<span class=\"pull-right label label-primary\">" + new SimpleDateFormat("hh:mm a").format(order.getOrderedAt()) + "</span>"
                                        + "</p>"
                                        + "</a>";
                                
                            }
                        }
                    }
                    
                    System.out.println("[ GET SERV ORDR ] - Orders Found : " + orders.size());
                    out.print(html.equals("") ? "<div class='alert alert-info'>You don't have any orders right now..!</div>" : html);
                } else {
                    out.print("NO_MEMBERSHIP");
                }
            } else {
                session.setAttribute("CURRENT_TASK", new Task("serviceDashboard.jsp"));
                out.print("NOT_LOGGED");
            }
        } catch (Exception e) {
            System.err.println("[ GET SERV ORDR ] - Error : " + e);
            out.print("<div class='alert alert-danger'>Error while getting current Orders.</div>");
        } finally {
            System.out.println("[ MEMORY ] Usage - " + ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000));
            System.gc();
        }
    }
}
