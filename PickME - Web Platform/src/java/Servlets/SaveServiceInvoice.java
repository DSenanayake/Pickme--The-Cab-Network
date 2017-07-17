package Servlets;

import Db.LoginDetails;
import Model.Cart;
import Model.Invoice;
import Model.Task;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Deeptha
 */
public class SaveServiceInvoice extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            Cart cart = (Cart) session.getAttribute("CURRENT_CART");
            LoginDetails details = (LoginDetails) session.getAttribute("CURRENT_USER");

            if (cart != null) {
                if (details != null) {

                    if (!details.getUsers().isEmpty()) {
                        Db.User u = (Db.User) details.getUsers().toArray()[0];
                        int invoice = Model.Invoice.saveCurrentInvoice(cart.getItems(), u.getUserId());

                        if (invoice != Invoice.STATUS_ERROR) {
                            session.setAttribute("CURRENT_CART", null);

                            session.setAttribute("CURRENT_PLAN", null);
                            session.setAttribute("CURRENT_INVOICE", invoice);
                            out.print("OK");
                        } else {
                            out.print("ERROR");
                        }

                        System.out.println("[ SAVE INV ] - Invoice ID : " + invoice);
                    } else {
                        System.err.println("[ SAVE INV ] - No Client Logged ");
                        out.print("NO_CLIENT");
                        session.setAttribute("CURRENT_TASK", new Task("shoppingCart.jsp"));
                    }
                } else {
                    session.setAttribute("CURRENT_TASK", new Task("shoppingCart.jsp"));
                    out.print("NOT_LOGGED");
                }
            } else {
                out.print("EMPTY_CART");
                System.err.println("[ SAVE INV ] - Empty Cart ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ SAVE INV ] - Error : " + e);
            out.print("ERROR");
        } finally {
            System.gc();
        }
    }
}
