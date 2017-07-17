package Servlets;

import Model.Cart;
import Model.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

/**
 *
 * @author Deeptha
 */
public class GenerateCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONArray array = new JSONArray();

        String cart = "", checkout = "";
        double usdTotal = 0;
        HttpSession session = request.getSession();
        try {
            Cart c = (Cart) session.getAttribute("CURRENT_CART");
            if (c != null) {
                if (!c.getItems().isEmpty()) {
                    cart = "<div class=\"table-responsive\">"
                            + "<table class=\"table table-hover\">"
                            + "<thead>"
                            + "<th>Item #.</th>"
                            + "<th>Cab Service Provider</th>"
                            + "<th>Start Point</th>"
                            + "<th>Destination</th>"
                            + "<th>Distance(KM)</th>"
                            + "<th>Cost per KM.(Rs)</th>"
                            + "<th>Amount(Rs)</th>"
                            + "<th style=\"border-left: 1px solid lightgray\"></th>"
                            + "</thead>"
                            + "<tbody>";

                    double total = 0;

                    for (CartItem item : c.getItems()) {
                        total += item.getAmount();
                        String name = Model.Service.getServiceById(item.getService_provider_id()).getServiceProviderName();
                        cart += "<tr>"
                                + "<td> #" + (c.getItems().indexOf(item) + 1) + "</td>"
                                + "<td>" + name + "</td>"
                                + "<td><button onclick=\"showOnMap(" + item.getLocation().getLattitude() + "," + item.getLocation().getLongitude() + ")\" class=\"btn btn-sm btn-default\" title=\"Show on Map\" data-toggle=\"tooltip\"><span class='glyphicon glyphicon-map-marker'></span></button></td>"
                                + "<td><button onclick=\"showOnMap(" + item.getDestination().getLattitude() + "," + item.getDestination().getLongitude() + ")\" class=\"btn btn-sm btn-default\" title=\"Show on Map\" data-toggle=\"tooltip\"><span class='glyphicon glyphicon-map-marker'></span></button></td>"
                                + "<td>" + item.getKm() + "</td>"
                                + "<td>" + item.getCost_per_km() + "</td>"
                                + "<td>" + Math.round(item.getAmount() * 100.0) / 100.0 + "</td>"
                                + "<td style=\"border-left: 1px solid lightgray \"><button onclick=\"removeFromCart(" + c.getItems().indexOf(item) + ")\" class=\"btn btn-sm btn-danger\" title=\"Remove\" data-toggle=\"tooltip\" data-placement=\"bottom\"><span class='glyphicon glyphicon-trash'></span></button>"
                                + " <button onclick='viewCabService(" + item.getService_provider_id() + ")' class=\"btn btn-sm btn-success\" title=\"View Cab Service\" data-toggle=\"tooltip\" data-placement=\"bottom\"><span class='glyphicon glyphicon-th-list'></span></button></td>"
                                + "</tr>";
                    }
                    cart += "            </tbody>"
                            + "        </table>"
                            + "    </div>";

                    usdTotal = Math.round(Controller.CurrencyConverter.convertLKRtoUSD(total) * 100.0) / 100.0;

                    checkout = "<div class=\"well\">"
                            + "        <legend class=\"\">Total Amount</legend>"
                            + "        <h2 class=\"text text-success\"><small>LKR</small> " + Math.round(total * 100.0) / 100.0 + "</h2>"
                            + "        <h4 class=\"text text-info\"><small>USD</small> " + usdTotal + "</h4>"
                            + ""
                            + "        <button onclick='checkoutShoppingOrder()' title=\"Secure Checkout with PayPal\" data-toggle=\"tooltip\" class=\"btn btn-block btn-lg btn-primary\"><img src=\"images/logos/pp64.png\" width=\"32px\" alt=\"Checkout\"> Checkout</button>"
                            + "        <i class=\"text text-primary\"><b>Note:</b> All transaction are done by US dollars($).</i><br/>"
                            + "        <b class=\"\">Currency Rate : LKR 1 = $" + Controller.CurrencyConverter.convertLKRtoUSD(1) + "</b>"
                            + "    </div>";

                } else {
                    cart = "<h4 class='text text-info'>No Service Orders in your cart !</h4>";
                    checkout = "<h5 class='text text-info'>Your Shopping cart is Empty !</h5>";
                }
            } else {
                cart = "<h4 class='text text-info'>No Service Orders in your cart !</h4>";
                checkout = "<h5 class='text text-info'>Your Shopping cart is Empty !</h5>";
            }
        } catch (javax.xml.ws.WebServiceException e) {
            System.out.println("[ GENER CART ] - Error : " + e);
            cart = ("<h5 class='text text-danger'>Please check your internet connection.</h5>");
            checkout = ("<h5 class='text text-danger'>Please check your internet connection.</h5>");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ GENER CART ] - Error : " + e);
            cart = ("<h5 class='text text-danger'>Sorry, Error while getting Cart Details.</h5>");
            checkout = ("<h5 class='text text-danger'>Sorry, Error while getting Cart Summary.</h5>");
        } finally {
            session.setAttribute("Payment_Amount", String.valueOf(usdTotal));
            System.gc();
        }

        HashMap hashMap = new HashMap();

        hashMap.put("cart", cart);
        hashMap.put("checkout", checkout);

        array.add(hashMap);
        out.print(array.toJSONString());
    }
}
