package Model;

import Db.Administrator;
import Db.Gender;
import Db.LoginDetails;
import Db.LoginStatus;
import Db.ProfilePicture;
import Db.UserDetails;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class User {

    private static Session ses;
    public static final int DEFAULT_PROFILE_PIC = 1;

    public static final int SUCCESS = 0;
    public static final int ALREADY_CONFIRMED = 1;
    public static final int INVALID_REQUEST = 2;
    public static final int LINK_EXPIRED = 3;
    public static final int ERROR = -1;
    public static final int USER_NOT_EXIST = 2;

    public static void refresh() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    public static String getFirstNameByUser(Db.LoginDetails details) {
        String fname = "Guest";
        if (details != null) {
            if (!details.getUsers().isEmpty()) {
                Db.User user = (Db.User) details.getUsers().toArray()[0];
                fname = user.getUserDetails().getFirstname();

            } else if (!details.getServiceProviders().isEmpty()) {
                Db.ServiceProvider provider = (Db.ServiceProvider) details.getServiceProviders().toArray()[0];
                fname = provider.getServiceProviderName();

            } else if (!details.getCabDrivers().isEmpty()) {
                Db.CabDriver cabDriver = (Db.CabDriver) details.getCabDrivers().toArray()[0];
                fname = cabDriver.getCabDriverDetails().getFirstName();

            } else if (!details.getAdministrators().isEmpty()) {
                fname = "Pickme.lk";
            }
        }
        return fname;
    }

    public static int confirmUserEmail(String string, String string0, String mils) {
        refresh();
        try {

            Calendar c = Calendar.getInstance();
            Date d = new Date(Long.parseLong(mils));
            c.setTime(d);
            c.add(Calendar.DATE, 2);

            if (c.after(Calendar.getInstance())) {
                Transaction t = ses.beginTransaction();

                Criteria cr = ses.createCriteria(Db.LoginDetails.class);
                cr.add(Restrictions.eq("loginDetailsId", Integer.parseInt(string)));
                cr.add(Restrictions.eq("email", string0));

                Db.LoginDetails details = (Db.LoginDetails) cr.uniqueResult();
                if (details != null) {
                    int current_status = details.getLoginStatus().getLoginStatusId();

                    if (current_status == UserStatus.NOT_CONFIRMED) {

                        Db.LoginStatus status;

                        if (!details.getServiceProviders().isEmpty()) {
                            status = (Db.LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.INACTIVATED);
                        } else {
                            status = (Db.LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.ACTIVATED);
                        }

                        details.setLoginStatus(status);

                        ses.update(details);

                        t.commit();
                        return SUCCESS;
                    } else if (current_status == UserStatus.ACTIVATED) {
                        return ALREADY_CONFIRMED;
                    } else {
                        return INVALID_REQUEST;
                    }
                } else {
                    return INVALID_REQUEST;
                }
            } else {
                return LINK_EXPIRED;
            }
        } catch (LazyInitializationException e) {
            refresh();
            return ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public static Db.User getClientByEmail(String email) {
        refresh();
        try {
            Criteria cr = ses.createCriteria(Db.LoginDetails.class);

            cr.add(Restrictions.eq("email", email));
            LoginDetails details = (LoginDetails) cr.uniqueResult();

            return (Db.User) details.getUsers().toArray()[0];
        } catch (Exception e) {
            return null;
        }
    }

    public static LoginDetails loginUser(String email, String pword) {
        refresh();
        try {
            Criteria cr = ses.createCriteria(Db.LoginDetails.class);
            cr.add(Restrictions.eq("email", email));
            cr.add(Restrictions.eq("password", pword));

            if (cr.uniqueResult() != null) {
                System.out.println(cr.uniqueResult());
                Db.LoginDetails details = (Db.LoginDetails) cr.uniqueResult();
                if (details.getEmail().equals(email) & details.getPassword().equals(pword)) {
                    return details;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int sendResetPassword(String email) {
        refresh();
        try {
            boolean found = alreadyExist(email);
            if (found) {
                Db.LoginDetails details = getUserByEmail(email);
                System.out.println("[ RESET ] - Found User : " + details.getLoginDetailsId());
                String password = details.getPassword();
                Controller.Services.sendPasswordResetEmail(email, password);
                System.out.println("[ RESET ] - Reset Email Sent.");
                return SUCCESS;
            } else {
                return USER_NOT_EXIST;
            }
        } catch (Exception e) {
            System.out.println("[ RESET ] - Error.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public static Db.LoginDetails getUserByEmail(String email) {
        refresh();
        try {
            Criteria cr = ses.createCriteria(Db.LoginDetails.class);
            cr.add(Restrictions.eq("email", email));
            return (Db.LoginDetails) cr.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    public static void keepLoggedIn(int id, String password, HttpSession session) throws Exception {
        refresh();
        if (session.getAttribute("CURRENT_USER") == null) {
            Criteria cr = ses.createCriteria(Db.LoginDetails.class);

            cr.add(Restrictions.eq("loginDetailsId", id));
            cr.add(Restrictions.eq("password", password));

            Db.LoginDetails details = (Db.LoginDetails) cr.uniqueResult();

            if (details != null) {
                session.setAttribute("CURRENT_USER", details);
                session.setAttribute("fname", getFirstNameByUser(details));
            }
        }
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        Cookie l_key = new Cookie("l_key", null);
        Cookie l_value = new Cookie("l_value", null);

        l_key.setMaxAge(0);
        l_value.setMaxAge(0);

        response.addCookie(l_key);
        response.addCookie(l_value);

    }

    static String getAllowedPage(String uri, HttpSession session) {
        refresh();

        String page;
        try {
            int uType;
            Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");

            if (details != null) {
                uType = details.getUserType().getUserTypeId();
            } else {
                uType = UserType.GUEST;
            }

            Db.UserPrivillege privillege = (Db.UserPrivillege) ses.createQuery("FROM UserPrivillege up WHERE up='" + uri + "'").uniqueResult();

            if (privillege == null) {
                page = "404.jsp";
            } else {
                Criteria cr = ses.createCriteria(Db.UserTypeHasUserPrivillege.class);

                cr.add(Restrictions.eq("userType", (Db.UserType) ses.load(Db.UserType.class, uType)));
                cr.add(Restrictions.eq("userPrivillege", privillege));

                Db.UserTypeHasUserPrivillege userPrivillege = (Db.UserTypeHasUserPrivillege) cr.uniqueResult();

                if (userPrivillege != null) {
                    if (userPrivillege.getAllow()) {
                        page = userPrivillege.getAllowedPage();
                    } else {
                        page = userPrivillege.getDisallowedPage();
                    }
                } else {
                    page = "restricted.jsp";
                }

            }
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            page = "404.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            page = "index.jsp";
        }
        return page;
    }

    public User() {
        ses = Controller.Connection.getSessionFactory().openSession();
    }

    public Administrator loginAdmin(String uname, String pword) throws HibernateException {
        Criteria cr = ses.createCriteria(LoginDetails.class);
        cr.add(Restrictions.eq("userType", (Db.UserType) ses.load(Db.UserType.class, UserType.ADMINISTRATOR)));
        cr.add(Restrictions.eq("loginStatus", (Db.LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.ACTIVATED)));
        cr.add(Restrictions.eq("email", uname));
        cr.add(Restrictions.eq("password", pword));

        LoginDetails lg = (LoginDetails) cr.uniqueResult();
        if (lg != null) {
            try {
                if (lg.getEmail().equals(uname) & lg.getPassword().equals(pword)) {
                    return (Administrator) lg.getAdministrators().toArray()[0];
                } else {
                    return null;
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void registerClient(String fname, String lname, int gender, String city, String mobile, String email, String password) throws HibernateException, UnsupportedEncodingException, MessagingException {
        Transaction tr = ses.beginTransaction();
        LoginDetails log = new LoginDetails();

        log.setEmail(email);
        log.setPassword(password);
        log.setUserType((Db.UserType) ses.load(Db.UserType.class, UserType.CLIENT));
        log.setLoginStatus((LoginStatus) ses.load(Db.LoginStatus.class, UserStatus.NOT_CONFIRMED));
        log.setLastUpdate(new Date());

        ses.save(log);

        UserDetails ud = new UserDetails();

        ud.setFirstname(fname);
        ud.setLastname(lname);

        ud.setCity(Model.City.saveOrGetCity(city));

        ud.setGender((Gender) ses.load(Db.Gender.class, gender));
        ud.setMobile(mobile);
        ud.setProfilePicture((ProfilePicture) ses.load(Db.ProfilePicture.class, Model.User.DEFAULT_PROFILE_PIC));
        ud.setLastUpdated(new Date());

        ses.save(ud);

        Db.User user = new Db.User();

        user.setLoginDetails(log);
        user.setUserDetails(ud);

        ses.save(user);
        tr.commit();
        Controller.Services.sendConfirmationEmail(String.valueOf(log.getLoginDetailsId()), ud.getFirstname(), log.getEmail());
    }

    public static boolean alreadyExist(String email) {
        refresh();
        Criteria cr = ses.createCriteria(Db.LoginDetails.class);
        cr.add(Restrictions.eq("email", email));
        return cr.uniqueResult() != null;
    }

    public static String getAllUserNames(Db.LoginDetails log, String element, String attribute, String css_class) {
        refresh();
        String html = "";
        try {

            int type = log.getUserType().getUserTypeId();

            Criteria cr;

            cr = ses.createCriteria(Db.ServiceProvider.class);
            List<Db.ServiceProvider> list = cr.list();

            html += "<optgroup label='Cab Services'>";
            for (Db.ServiceProvider sp : list) {
                html += ("<" + element + " " + attribute + "='" + sp.getLoginDetails().getLoginDetailsId() + "' class='" + css_class + "' >" + sp.getServiceProviderName() + "</" + element + ">");
            }
            html += "</optgroup>";
            cr = ses.createCriteria(Db.CabDriver.class);
            List<Db.CabDriver> drivers = cr.list();

            html += "<optgroup label='Cab Drivers'>";
            for (Db.CabDriver d : drivers) {
                html += ("<" + element + " " + attribute + "='" + d.getLoginDetails().getLoginDetailsId() + "' class='" + css_class + "' >" + d.getCabDriverDetails().getFirstName() + " " + d.getCabDriverDetails().getLastName() + "</" + element + ">");
            }
            html += "</optgroup>";

            cr = ses.createCriteria(Db.User.class);
            List<Db.User> users = cr.list();

            html += "<optgroup label='Clients'>";
            for (Db.User u : users) {
                html += ("<" + element + " " + attribute + "='" + u.getLoginDetails().getLoginDetailsId() + "' class='" + css_class + "' >" + u.getUserDetails().getFirstname() + " " + u.getUserDetails().getLastname() + "</" + element + ">");
            }
            html += "</optgroup>";

        } catch (Exception e) {
            System.err.println("[ ERROR HTML GENR ] " + e);
        }
        return html;
    }
}
