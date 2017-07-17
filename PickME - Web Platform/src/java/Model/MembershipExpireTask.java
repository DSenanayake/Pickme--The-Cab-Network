package Model;

import Controller.MembershipExpiration;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MembershipExpireTask implements ServletContextListener {

    private static final long PERIOD = (60 * 60 * 12) * 1000;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    new MembershipExpiration().doExpire();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.gc();
                }
            }
        }, 0, PERIOD);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
