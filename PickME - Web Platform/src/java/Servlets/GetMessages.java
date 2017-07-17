package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetMessages", urlPatterns = {"/GetMessages"})
public class GetMessages extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int msgS = Integer.parseInt(request.getParameter("msg"));
        int warnS = Integer.parseInt(request.getParameter("warn"));
        int feedS = Integer.parseInt(request.getParameter("feed"));
        int compS = Integer.parseInt(request.getParameter("comp"));
        int sent = Integer.parseInt(request.getParameter("sent"));

        String json_out = new Controller.MessageCenter().getAllMessages((Db.LoginDetails) request.getSession().getAttribute("CURRENT_USER"), msgS, warnS, feedS, compS,sent);
        System.out.println("[ MSG ] - " + json_out);
        out.print(json_out);
        System.gc();
    }
}
