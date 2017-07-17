package Model;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class KeepLogged extends SimpleTagSupport {

    private HttpServletRequest request;

    @Override
    public void doTag() throws JspException, IOException {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            String id = null, password = null;
            for (Cookie cookie : cookies) {
                String value = Controller.Services.decrypt(cookie.getValue());

                switch (cookie.getName()) {
                    case "l_key":
                        id = value;
                        break;
                    case "l_value":
                        password = value;
                        break;
                }
            }

            if (id != null & password != null) {
                try {
                    Model.User.keepLoggedIn(Integer.parseInt(id), password, request.getSession());
                    System.out.println("[ KEEP LOGG ] - OK");
                } catch (Exception e) {
                    System.err.println("[ KEEP LOGG ] - Error : " + e);
                }
            }
        }
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

}
