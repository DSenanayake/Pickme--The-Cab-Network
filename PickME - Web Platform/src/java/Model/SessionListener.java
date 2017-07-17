package Model;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 *
 * @author Deeptha
 */
public class SessionListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals("CURRENT_USER")) {
            System.out.println("[ ATEMPT TO ONLINE ]");
            Model.Service.setOnline(true, (Db.LoginDetails) event.getValue());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals("CURRENT_USER")) {
            System.out.println("[ ATEMPT TO OFFLINE ]");
            Model.Service.setOnline(false, (Db.LoginDetails) event.getValue());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.err.println("[ SESSION ] - ATTRIB REPLACED : " + event.getName() + " - " + event.getValue());
    }

}
