package Servlets;

import Model.Task;
import Model.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
            String url = "index.jsp";
            if (details != null) {
                int uType = details.getUserType().getUserTypeId();
                if (uType == UserType.ADMINISTRATOR) {
                    url = "adminDashboard.jsp";
                } else if (uType == UserType.SERVICE_PROVIDER) {
                    url = "serviceDashboard.jsp";
                } else if (uType == UserType.CAB_DRIVER) {
                    url = "driverDashboard.jsp";
                } else if (uType == UserType.CLIENT) {
                    url = "clientProfile.jsp";
                }
            } else {
                session.setAttribute("CURRENT_TASK", new Task("Dashboard"));
                url = "userLogin.jsp";
            }
            response.sendRedirect(response.encodeRedirectURL(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
