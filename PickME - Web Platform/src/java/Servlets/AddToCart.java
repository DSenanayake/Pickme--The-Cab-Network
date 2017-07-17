package Servlets;

import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Deeptha
 */
public class AddToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int service = Integer.parseInt(request.getParameter("service"));
            HttpSession hs = request.getSession();
            CabOrder cabOrder = (CabOrder) hs.getAttribute("CURRENT_ORDER");
            if (cabOrder != null) {
                CartItem item = new CartItem();
                double cost_per_km = Model.Service.getServiceById(service).getServiceDetails().getCostPerKm();

                item.setLocation(cabOrder.getLocation());
                item.setDestination(cabOrder.getDestination());
                item.setKm(cabOrder.getDistance());
                item.setCost_per_km(cost_per_km);
                item.setAmount(cost_per_km * cabOrder.getDistance());
                item.setService_provider_id(service);
                item.setStatus(CartItem.STATUS_OK);

                Cart cart = (Cart) hs.getAttribute("CURRENT_CART");

                if (cart == null) {
                    cart = new Cart();
                }
                cart.addToCart(item);

                hs.setAttribute("CURRENT_CART", cart);
                hs.setAttribute("CURRENT_ORDER", null);
                out.print("OK");
            } else {
                out.print("NO_ORDER");
            }
        } catch (Exception e) {
            out.print("ERROR");
        }
    }

}
