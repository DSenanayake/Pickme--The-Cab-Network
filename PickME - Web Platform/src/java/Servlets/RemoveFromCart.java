package Servlets;

import Model.Cart;
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
public class RemoveFromCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int item = Integer.parseInt(request.getParameter("item"));
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CURRENT_CART");
            if (cart != null) {
                cart.removeFromCart(item);
            }
            System.out.println("[REMOV SHOPP CRT ] - Removed : " + item);
            out.print("OK");
        } catch (Exception e) {
            System.out.println("[REMOV SHOPP CRT ] - Error s: " + e);
            out.print("ERROR");
        } finally {
            System.gc();
        }
    }

}
