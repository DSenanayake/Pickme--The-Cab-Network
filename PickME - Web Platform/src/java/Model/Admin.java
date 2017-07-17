package Model;

import Db.UserPrivillege;
import Db.UserTypeHasUserPrivillege;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;
import javax.servlet.ServletContext;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class Admin {

    private static Session ses;

    public static String getUserPrivileges() throws Exception {
        refresh();

        Criteria cr = ses.createCriteria(Db.UserTypeHasUserPrivillege.class);
        List<Db.UserTypeHasUserPrivillege> privilleges = cr.list();

        String guest = "<ul class='list-group'>";
        String client = "<ul class='list-group'>";
        String driver = "<ul class='list-group'>";
        String service = "<ul class='list-group'>";

        for (UserTypeHasUserPrivillege privillege : privilleges) {
            int ut = privillege.getUserType().getUserTypeId();
            if (ut == UserType.GUEST) {
                guest += "<li class='list-group-item'><a href='#' onclick='toggleAllow(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='text glyphicon glyphicon-" + (privillege.getAllow() ? "plus text-success" : "minus text-warning") + "'></span></a> " + privillege.getUserPrivillege().getUserPrivillegeName() + "<a href='#remove' title='Remove' onclick='removePrivilege(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='glyphicon glyphicon-remove text text-danger pull-right' aria-hidden='true' ></span></a></li>";
            } else if (ut == UserType.CLIENT) {
                client += "<li class='list-group-item'><a href='#' onclick='toggleAllow(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='text glyphicon glyphicon-" + (privillege.getAllow() ? "plus text-success" : "minus text-warning") + "'></span></a> " + privillege.getUserPrivillege().getUserPrivillegeName() + "<a href='#remove' title='Remove' onclick='removePrivilege(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='glyphicon glyphicon-remove text text-danger pull-right' aria-hidden='true' ></span></a></li>";
            } else if (ut == UserType.CAB_DRIVER) {
                driver += "<li class='list-group-item'><a href='#' onclick='toggleAllow(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='text glyphicon glyphicon-" + (privillege.getAllow() ? "plus text-success" : "minus text-warning") + "'></span></a> " + privillege.getUserPrivillege().getUserPrivillegeName() + "<a href='#remove' title='Remove' onclick='removePrivilege(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='glyphicon glyphicon-remove text text-danger pull-right' aria-hidden='true' ></span></a></li>";
            } else if (ut == UserType.SERVICE_PROVIDER) {
                service += "<li class='list-group-item'><a href='#' onclick='toggleAllow(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='text glyphicon glyphicon-" + (privillege.getAllow() ? "plus text-success" : "minus text-warning") + "'></span></a> " + privillege.getUserPrivillege().getUserPrivillegeName() + "<a href='#remove' title='Remove' onclick='removePrivilege(" + privillege.getUserTypeHasUserPrivillegeId() + ")'><span class='glyphicon glyphicon-remove text text-danger pull-right' aria-hidden='true' ></span></a></li>";
            }
        }

        guest += "<a href='#add' onclick='addPrivilege(" + UserType.GUEST + ")' class='list-group-item active'><small><span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Add</small></a></ul>";
        client += "<a href='#add' onclick='addPrivilege(" + UserType.CLIENT + ")' class='list-group-item active'><small><span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Add</small></a></ul>";
        driver += "<a href='#add' onclick='addPrivilege(" + UserType.CAB_DRIVER + ")' class='list-group-item active'><small><span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Add</small></a></ul>";
        service += "<a href='#add' onclick='addPrivilege(" + UserType.SERVICE_PROVIDER + ")' class='list-group-item active'><small><span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Add</small></a></ul>";

        String HTML = "<table class='table'>"
                + "<thead>"
                + "<th>Anyone</th>"
                + "<th>Client</th>"
                + "<th>Cab Driver</th>"
                + "<th>Service Provider</th>"
                + "</thead>"
                + "<tr>"
                + "<td>" + guest + "</td>"
                + "<td>" + client + "</td>"
                + "<td>" + driver + "</td>"
                + "<td>" + service + "</td>"
                + "</tr>"
                + "</table>"
                + "<div class='well'>"
                + "<span class='text text-success'><span class='glyphicon glyphicon-plus'></span> Allowed Pages </span>"
                + "<span class='text text-warning'><span class='glyphicon glyphicon-minus'></span> Disallowed Pages</span>"
                + "</div>";

        return HTML;
    }

    public static void removePrivilege(int privilege) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();
        Db.UserTypeHasUserPrivillege p = (Db.UserTypeHasUserPrivillege) ses.load(Db.UserTypeHasUserPrivillege.class, privilege);

        ses.delete(p);

        tr.commit();
    }

    public static String getSelectivePrivileges() throws Exception {
        refresh();
        String html = "";
        Criteria cr = ses.createCriteria(Db.UserPrivillege.class);
        List<Db.UserPrivillege> privileges = cr.list();
        for (UserPrivillege privillege : privileges) {
            html += "<option value='" + privillege.getUserPrivillegeUri() + "' class='text text-danger'>" + privillege.getUserPrivillegeName() + "</option>";
        }
        return html;
    }

    public static String addPrivilege(String uri, int usertype, boolean allow, String allowed, String disallowed) {
        refresh();
        Transaction tr = ses.beginTransaction();
        Db.UserTypeHasUserPrivillege pr = (Db.UserTypeHasUserPrivillege) ses.createQuery("FROM UserTypeHasUserPrivillege pr WHERE pr.userPrivillege='" + uri + "' AND pr.userType='" + usertype + "'").uniqueResult();
        if (pr == null) {
            Db.UserTypeHasUserPrivillege privilege = new Db.UserTypeHasUserPrivillege();

            privilege.setAllow(allow);
            privilege.setAllowedPage(allowed);
            privilege.setDisallowedPage(disallowed);
            privilege.setUserPrivillege((UserPrivillege) ses.load(Db.UserPrivillege.class, uri));
            privilege.setUserType((Db.UserType) ses.load(Db.UserType.class, usertype));

            ses.save(privilege);
            tr.commit();
            return "OK";
        } else {
            return "EXIST";
        }
    }

    public static void toggleAllow(int privilege) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.UserTypeHasUserPrivillege p = (Db.UserTypeHasUserPrivillege) ses.load(Db.UserTypeHasUserPrivillege.class, privilege);
        p.setAllow(!p.getAllow());
        ses.update(p);

        tr.commit();
    }

    public static String getPrivilegesTable(int start) throws Exception {
        refresh();
        String html = "<div class='table-responsive'><table class='table table-hover'>"
                + "<thead>"
                + "<th>Privilege Name</th>"
                + "<th>Privilege URI</th>"
                + "<th>Options</th>"
                + "</thead>";
        Criteria cr = ses.createCriteria(Db.UserPrivillege.class);

        System.out.println(cr.list().size());
        int rip = (cr.list().size() % 5) > 0 ? (cr.list().size() / 5) + 1 : cr.list().size() / 5;

        System.out.println(rip);

        cr.setMaxResults(5);
        cr.setFirstResult(start);

        List<Db.UserPrivillege> list = cr.list();

        for (UserPrivillege privilege : list) {
            html += "<tr>"
                    + "<td>" + privilege.getUserPrivillegeName() + "</td>"
                    + "<td>" + privilege.getUserPrivillegeUri() + "</td>"
                    + "<td style='text-align:center;'>"
                    + "<a title=\"Edit\" href=\"#edit\" onclick=\"editPrivilege('" + privilege.getUserPrivillegeUri() + "','" + privilege.getUserPrivillegeName() + "')\" class=\"text text-info\"><span class=\"glyphicon glyphicon-pencil\"></span></a>"
                    + " <a title=\"Remove\" href=\"#delete\" onclick=\"deletePrivilege('" + privilege.getUserPrivillegeUri() + "')\" class=\"text text-danger\"><span class=\"glyphicon glyphicon-remove\"></span></a>"
                    + "</td>"
                    + "</tr>";
        }

        html += "</table></div>"
                + "<nav>"
                + "  <ul class=\"pagination\">";
        for (int i = 0; i < rip; i++) {
            html += "    <li><a onclick='getPrivilegesTable(" + (i * 5) + ")' href=\"#\">" + (i + 1) + "</a></li>";
        }
        html += "  </ul>"
                + "</nav>";

        return html;
    }

    public static void addNewPrivilege(String uri, String name) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.UserPrivillege p = new Db.UserPrivillege(uri);
        p.setUserPrivillegeName(name);
        ses.saveOrUpdate(p);

        tr.commit();
    }

    public static void updatePrivilege(String id, String uri, String name) throws Exception {
        refresh();
        Transaction tr = ses.beginTransaction();

        Db.UserPrivillege p = (Db.UserPrivillege) ses.createQuery("FROM UserPrivillege up WHERE up='" + id + "'").uniqueResult();
        if (p != null) {
            p.setUserPrivillegeName(name);

            ses.update(p);
            tr.commit();
        }

    }

    public static void updateTermsAndConditions(String text, ServletContext context) throws Exception {
        File doc = new File(context.getRealPath("/") + "docs/terms_and_conditions.txt");
        try (FileWriter writer = new FileWriter(doc)) {
            writer.write(text);
            writer.flush();
        }
    }

    public static void main(String[] args) {

    }

    private Admin() {
        refresh();
    }

    private static void refresh() {
        try {
            ses = Controller.Connection.getSessionFactory().openSession();
        } catch (NoClassDefFoundError e) {
            refresh();
        }
    }

    public static void deleteUserPrivileges(String args) throws Exception {
        refresh();

        Transaction tr = ses.beginTransaction();
        Db.UserPrivillege p = (Db.UserPrivillege) ses.load(Db.UserPrivillege.class, args);
        ses.delete(p);

        tr.commit();
    }
}
