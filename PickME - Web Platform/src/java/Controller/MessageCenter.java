package Controller;

import Db.LoginDetails;
import Db.Message;
import Db.MsgPriority;
import Db.MsgStatus;
import Db.MsgType;
import Model.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;

public class MessageCenter {

    private static SessionFactory factory;
    private Session session;
    private Transaction transaction;

    public static final int TYPE_MESSAGE = 1;
    public static final int TYPE_WARNING = 2;
    public static final int TYPE_FEEDBACK = 3;
    public static final int TYPE_COMPLAINT = 4;

    public static final int STATUS_UNREAD = 1;
    public static final int STATUS_READ = 2;
    public static final int STATUS_DELETED_BY_RECEIVER = 3;
    public static final int STATUS_DELETED_BY_SENDER = 4;
    public static final int STATUS_DELETED_BOTH = 5;

    public static final int PRIOR_IMPORTANT = 1;
    public static final int PRIOR_MEDIUM = 2;
    public static final int PRIOR_LOW = 3;

    public MessageCenter() {
        if (factory == null) {
            factory = Controller.Connection.getSessionFactory();
        }
        session = factory.openSession();
    }

    public void sendMsg(int to[], int from, int reply_for, String subject, String content, int type, int priority) throws Exception {
        try {
            transaction = session.beginTransaction();
            for (int t : to) {
                Db.Message message = new Db.Message();

                message.setContent(content);
                message.setLoginDetailsByMsgFrom((LoginDetails) session.load(Db.LoginDetails.class, from));
                message.setLoginDetailsByMsgTo((LoginDetails) session.load(Db.LoginDetails.class, t));
                message.setMessage((Message) (reply_for != -1 ? session.load(Db.Message.class, reply_for) : null));
                message.setMsgPriority((MsgPriority) session.load(Db.MsgPriority.class, priority));
                message.setMsgStatus((MsgStatus) session.load(Db.MsgStatus.class, STATUS_UNREAD));
                message.setMsgType((MsgType) session.load(Db.MsgType.class, type));
                message.setSubject(subject);
                message.setDateTime(new Date());

                session.save(message);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
            System.gc();
        }
    }

    public String getAllMessages(Db.LoginDetails log, int msgS, int warnS, int feedS, int comp, int sentS) {

        String status = "OK";
        String default_text = "<p class='text text-center'>You don't have any %type% right now.</p>", messages = default_text.replaceFirst("%type%", "messages"), warnings = default_text.replaceAll("%type%", "warnings"), feedbacks = default_text.replaceAll("%type%", "feedbacks"), complaints = default_text.replaceAll("%type%", "complaints");
        String sent = "<p class='text text-center'>No Sent Messages.</p>";
        try {
            if (log != null) {

                messages = getInbox(log.getLoginDetailsId(), TYPE_MESSAGE, msgS);
                warnings = getInbox(log.getLoginDetailsId(), TYPE_WARNING, warnS);
                feedbacks = getInbox(log.getLoginDetailsId(), TYPE_FEEDBACK, feedS);
                complaints = getInbox(log.getLoginDetailsId(), TYPE_COMPLAINT, comp);
                sent = getSentItems(log.getLoginDetailsId(), sentS);

            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "ERROR";
        } finally {
            session.close();
        }

        JSONArray array = new JSONArray();

        HashMap map = new HashMap();

        map.put("status", status);
        map.put("messages", messages);
        map.put("warnings", warnings);
        map.put("feedbacks", feedbacks);
        map.put("complaints", complaints);
        map.put("sent", sent);

        array.add(map);
        return array.toJSONString();
    }

    private String getInbox(int log, int type, int first) {

        Criteria cr = session.createCriteria(Db.Message.class);
        cr.add(Restrictions.eq("msgType", (Db.MsgType) session.load(Db.MsgType.class, type)));
        cr.add(Restrictions.eq("loginDetailsByMsgTo", (Db.LoginDetails) session.load(Db.LoginDetails.class, log)));
        cr.add(Restrictions.or((Restrictions.ne("msgStatus", (Db.MsgStatus) session.load(Db.MsgStatus.class, STATUS_DELETED_BOTH))), (Restrictions.ne("msgStatus", (Db.MsgStatus) session.load(Db.MsgStatus.class, STATUS_DELETED_BY_RECEIVER)))));
        cr.addOrder(Order.desc("dateTime"));

        int rip = cr.list().size() % 5 == 0 ? cr.list().size() / 5 : (cr.list().size() / 5) + 1;

        cr.setFirstResult(first);
        cr.setMaxResults(5);

        List<Db.Message> list = cr.list();
        String html = "<table class='table'><thead><th>Date/Time</th><th>From</th><th>Content</th><th></th></thead>";
        html += list.isEmpty() ? "<p class='text text-center'>There is no Messages.</p>" : "";
        for (Message message : list) {
            int prior = message.getMsgPriority().getMsgPriorityId();
            String label = prior == PRIOR_IMPORTANT ? "danger" : prior == PRIOR_MEDIUM ? "info" : "success";
            String title = (message.getMessage() != null ? "Reply For : " + message.getMessage().getSubject().substring(0, message.getMessage().getSubject().length() > 20 ? 20 : message.getMessage().getSubject().length()).trim() + "... | " : "") + "<b>" + message.getSubject() + "</b> - " + message.getContent();
            html += "<tr class='bg-" + label + "'>"
                    + "<td>" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(message.getDateTime()) + "</td>"
                    + "<td><a><i>" + User.getFirstNameByUser(message.getLoginDetailsByMsgFrom()) + "</i></a></td>"
                    + "<td><a>" + (title.substring(0, title.length() < 100 ? title.length() : 100).trim() + "...") + "</a></td>"
                    + "<td>" + (message.getMsgStatus().getMsgStatusId() == STATUS_UNREAD ? "<label class='label label-success'>New</label>" : "") + "</td>"
                    + "</tr>";
        }
        html += "</table>";

        html += "<div class='well well-sm'><nav>"
                + "  <ul class=\"pagination\">";

        for (int i = 0; i < rip; i++) {
            html += "<li><a href=\"#\" onclick='nextResult(" + i * 5 + "," + type + ")'>" + (i + 1) + "</a></li>";
        }

        html += "  </ul>"
                + "</nav>"
                + "</div>";

        return html;
    }

    private String getSentItems(int log, int first) {
        Criteria cr = session.createCriteria(Db.Message.class);
        cr.add(Restrictions.eq("loginDetailsByMsgFrom", (Db.LoginDetails) session.load(Db.LoginDetails.class, log)));
        cr.add(Restrictions.or((Restrictions.ne("msgStatus", (Db.MsgStatus) session.load(Db.MsgStatus.class, STATUS_DELETED_BOTH))), (Restrictions.ne("msgStatus", (Db.MsgStatus) session.load(Db.MsgStatus.class, STATUS_DELETED_BY_SENDER)))));
        cr.addOrder(Order.desc("dateTime"));

        int rip = cr.list().size() % 5 == 0 ? cr.list().size() / 5 : (cr.list().size() / 5) + 1;

        cr.setFirstResult(first);
        cr.setMaxResults(5);

        List<Db.Message> list = cr.list();
        String html = "<table class='table'><thead><th>Date/Time</th><th>To</th><th>Content</th><th>Type</th><th></th></thead>";
        html += list.isEmpty() ? "<p class='text text-center'>There is no Messages.</p>" : "";
        for (Message message : list) {
            int prior = message.getMsgPriority().getMsgPriorityId();
            String label = prior == PRIOR_IMPORTANT ? "danger" : prior == PRIOR_MEDIUM ? "info" : "success";
            String title = (message.getMessage() != null ? "Reply For : " + message.getMessage().getSubject().substring(0, message.getMessage().getSubject().length() > 20 ? 20 : message.getMessage().getSubject().length()).trim() + "... | " : "") + "<b>" + message.getSubject() + "</b> - " + message.getContent();
            html += "<tr class='bg-" + label + "'>"
                    + "<td>" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(message.getDateTime()) + "</td>"
                    + "<td><a><i>" + User.getFirstNameByUser(message.getLoginDetailsByMsgTo()) + "</i></a></td>"
                    + "<td><a>" + (title.substring(0, title.length() < 100 ? title.length() : 100).trim() + "...") + "</a></td>"
                    + "<td><b>" + message.getMsgType().getMsgType() + "</b></td>"
                    + "<td>" + (message.getMsgStatus().getMsgStatusId() != STATUS_UNREAD ? "<label class='label label-success'>Seen</label>" : "") + "</td>"
                    + "</tr>";
        }
        html += "</table>";

        html += "<div class='well well-sm'><nav>"
                + "  <ul class=\"pagination\">";

        for (int i = 0; i < rip; i++) {
            html += "<li><a href=\"#\" onclick='nextResult(" + i * 5 + ",5)'>" + (i + 1) + "</a></li>";
        }

        html += "  </ul>"
                + "</nav>"
                + "</div>";

        return html;
    }

    public String sendNewMessage(HttpServletRequest request) throws Exception {
        String ok_text = "OK";
        int type = Integer.parseInt(request.getParameter("type"));
        String tos[] = request.getParameterValues("to");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        int priority = Integer.parseInt(request.getParameter("priority"));
        if (tos != null) {

            int[] to = new int[tos.length];
            for (int i = 0; i < tos.length; i++) {
                to[i] = Integer.parseInt(tos[i]);
            }

            if (!subject.isEmpty()) {
                Db.LoginDetails log = (Db.LoginDetails) request.getSession().getAttribute("CURRENT_USER");
                if (log != null) {
                    int from = log.getLoginDetailsId();
                    sendMsg(to, from, -1, subject, content == null ? "" : content, type, priority);
                } else {
                    ok_text = "NO_LOG";
                }
            } else {
                ok_text = "NO_SUB";
            }
        } else {
            ok_text = "NO_REC";
        }

        return ok_text;
    }

}
