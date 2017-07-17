package Controller;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Services {

//<editor-fold defaultstate="collapsed" desc="Send Client Confirmation Email">
    public static void sendConfirmationEmail(String userId, String fname, String eMail) throws UnsupportedEncodingException, MessagingException {

        String confirmId = generateConfirmId(userId, eMail);

        sendEmail(eMail, "Email Cofirmation - Pickme.lk", "<html>"
                + "  <head>"
                + "    <title>Pickme.lk</title>"
                + "  </head>"
                + "  <body style=\"font-family: Segoe UI;color: #333\">"
                + "        <div class=\"container-div\" style=\"padding: 5px;border-radius: 5px;border: 1px solid darkgray;background-color: #eee\">"
                + "            <div class=\"header-div\" style=\"background-color: #fff;border-radius: 4px;padding: 5px;box-shadow: inset 0 0 2px 0;-moz-box-shadow: inset 0 0 2px 0;-webkit-box-shadow: inset 0 0 2px 0;-o-box-shadow: inset 0 0 2px 0\">"
                + "                <h2>Pickme.lk <small style=\"color: darkgray\">anytime.anywhere</small></h2>"
                + "            </div>"
                + "            <h4>Hello " + fname + " ,</h4>"
                + "            <p>Thank you for Connecting with Us, Your User Account has been created successfully.</p>"
                + "            <p>To Continue Registration Please confirm your Email by Clicking on Confirmation Link.</p>"
                + "            <a target=\"_blank\" class=\"btn\" href=\"http://localhost:8080/Pickme.lk/UserConfirmation.jsp?confirm=" + confirmId + "\" style=\"padding: 5px;background-color: #41BC5E;border-radius: 4px;border: 1px solid #41BC5E;font-size: 14px;color: white\">Confirm Email</a>"
                + "            <p>If your Browser dosen't support this URL, Please copy this ID : <b><i>" + confirmId + "</i></b> </p><p>And Confirm in <a href=\"http://localhost:8080/Pickme.lk/UserConfirmation.jsp\">Confirmation Page</a></p>"
                + "            <p><small>Note : This Link will expire in 2 Days. If this link is Expired Please <a target=\"_blank\" href=\"http://localhost:8080/Pickme.lk/UserConfirmation.jsp\">Resend</a> Confirmation Link.</small></p>"
                + "            <p>"
                + "            </p>"
                + "            <p>Stay with Us - <i>Thank you !</i></p>"
                + "            <h4>Pickme.lk - <small>anytime.anywhere.</small></h4>"
                + "            <h5 style=\"background-color: rgb(180, 40, 40);text-align: center;border-radius: 4px;color: white\">-Do not reply-</h5>"
                + "        </div>"
                + "    </body>"
                + "</html>", null);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Encrypt Service">
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static KeySpec ks;
    private static SecretKeyFactory skf;
    private static Cipher cipher;
    static byte[] arrayBytes;
    private static String myEncryptionKey;
    private static String myEncryptionScheme;
    static SecretKey key;

    private static void refresh() {
        try {
            myEncryptionKey = "PickmeDotLkTheCabNetwork";
            myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
            arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
            ks = new DESedeKeySpec(arrayBytes);
            skf = SecretKeyFactory.getInstance(myEncryptionScheme);
            cipher = Cipher.getInstance(myEncryptionScheme);
            key = skf.generateSecret(ks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String unencryptedString) throws UnsupportedEncodingException {
        refresh();
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = Base64.encode(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
//        return encryptedString;
    }

    public static String decrypt(String encryptedString) throws UnsupportedEncodingException {
        refresh();
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("NO MINIMUM BLOCKS.");
        }catch(Exception e){
            
        }
        return decryptedText;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Email Service">
    public static void sendEmail(String email, String subject, String Message, String pat) throws javax.mail.MessagingException {

        String[] guy = {email};
        String fnm = pat;                              // attatching file path............
        String from = "pickme.lk@gmail.com";
        String pass = "pickme.lk2015";
        String host = "smtp.gmail.com";

        Properties prop = new Properties();
        prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.socketFactory.port", 465);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.user", from);
        prop.put("mail.smtp.password", pass);
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");

        Session ses = Session.getDefaultInstance(prop, null);

        javax.mail.internet.MimeMessage mm = new MimeMessage(ses);

        mm.setFrom(new InternetAddress(from));

        InternetAddress[] ias = new InternetAddress[guy.length];

        for (int i = 0; i < guy.length; i++) {
            ias[i] = new InternetAddress(guy[i]);
        }

        for (int i = 0; i < ias.length; i++) {
            mm.addRecipients(RecipientType.TO, ias);
        }
        mm.setSubject(subject);
//            mm.setContent("<img src=" + "<h1>" + Message + "</h1>", "text/html");
        mm.setContent(Message, "text/html");

        //MimeBodyPart mbp=new MimeBodyPart();
        //mbp.attachFile(fnm);
        //Multipart mp=new MimeMultipart();
        //mp.addBodyPart(mbp);
        //mm.setContent(mp);
        SMTPTransport tran = (SMTPTransport) ses.getTransport("smtp");

        System.out.print("[SERVICES - EMAIL SERVICE] - Attempting to Login.");
        tran.connect(host, 465, from, pass);
        System.out.println(".");
        tran.sendMessage(mm, mm.getAllRecipients());

        tran.close();

        System.out.println("[SERVICES - EMAIL SERVICE] - Sent.");

    }
//</editor-fold>

    public static void main(String[] args) throws Exception {
        sendServiceConfirmationEmail(5 + "", "Deeptha", "chalithadeeptha@gmail.com", "AjKUKnsoNIAS");
    }

    public static String generateConfirmId(String userId, String eMail) throws UnsupportedEncodingException {
        return encrypt(userId + "#" + eMail + "#" + System.currentTimeMillis());
    }

    public static void sendServiceConfirmationEmail(String userId, String name, String email, String pass) throws UnsupportedEncodingException, MessagingException {
        String confirmId = generateConfirmId(userId, email);

        sendEmail(email, "Cab Service Provider Account Confirmation - Pickme.lk", "<html>"
                + "    <head>"
                + "        <title>Pickme.lk</title>"
                + "        <meta charset=\"UTF-8\">"
                + "        <meta name=\"viewport\" content=\"width=device-width\">"
                + "    </head>"
                + "    <body style=\"font-family: Segoe UI;color: #222222;margin: 5px;padding: 0\">"
                + "        <div style=\"background-color: #eeeeee;border: 2px solid #006699;box-shadow: 3px 3px 0px gray;-webkit-box-shadow: 3px 3px 0px gray;-moz-box-shadow: 3px 3px 0px gray;-o-box-shadow: 3px 3px 0px gray;\">"
                + "            <div style=\"background-color: #006699;color: #ffffff;padding: 2px 25px;\">"
                + "                <h2>Pickme.lk <small>anytime.anywhere</small></h2>"
                + "            </div>"
                + "            <div style=\"margin: 15px 25px;\">"
                + "                <p><b>Hello, <i>" + name + "</i></b></p>"
                + "                <p>We'r glad to associate with you. You are successfully subscribed as a Cab Service Provider. We'l inform you about the confirmation of the <b>Membership Upgrade</b> within 4 hour after your registration. Now you can confirm your email by clicking on Confirm.</p> <a style=\"border: 2px solid #006699;padding: 5px 15px;background-color: #006699;text-decoration: none;color: #ffffff;font-size: 18px;\" target=\"_blank\" href=\"http://localhost:8080/Pickme.lk/UserConfirmation.jsp?confirm=" + confirmId + "\">Confirm</a>"
                + "                <p>If this Link does not works in your browser, Please goto <a style=\"font-size: 18px;color: #006699;\" target=\"_blank\" href=\"http://localhost:8080/Pickme.lk/UserConfirmation.jsp\">confirmation page</a> and paste this confirmation key : <span style=\"line-height: 40px;margin: 0 auto;border: 2px solid #006699;border-radius: 4px;padding: 10px;display:block;\"><b>" + confirmId + "</b></span></p>"
                + "                <p>After Confirmation of your email use this password to Sign in. <span style=\"line-height: 40px;margin: 0 auto;border: 2px solid #006699;border-radius: 4px;padding: 10px;\"><b>Password : " + pass + "</b></span></p>"
                + "                <p><i>Thank you for choosing Pickme.lk to make suceess your Business career.</i></p>"
                + "                <p>Stay with Us.</p>"
                + "                <p><b>Pickme.lk</b></p>"
                + "            </div>"
                + "            <div style=\"background-color: #006699;color: #ffffff;padding: 2px 25px;\">"
                + "                <!--<span><span>Pickme.lk <small>anytime.anywhere</small></span></span>-->"
                + "                <span><small>Do not reply !</small></span>"
                + "            </div>"
                + "        </div>"
                + "    </body>"
                + "</html>"
                + "", null);
    }

    public static void sendPasswordResetEmail(String email, String password) throws MessagingException {
        String html = "<html>"
                + "    <head>"
                + "        <title>Pickme.lk</title>"
                + "        <meta charset=\"UTF-8\">"
                + "        <meta name=\"viewport\" content=\"width=device-width\">"
                + "    </head>"
                + "    <body style=\"font-family: Segoe UI;color: #222222;margin: 5px;padding: 0\">"
                + "        <div style=\"background-color: #eeeeee;border: 2px solid #006699;box-shadow: 3px 3px 0px gray;-webkit-box-shadow: 3px 3px 0px gray;-moz-box-shadow: 3px 3px 0px gray;-o-box-shadow: 3px 3px 0px gray;\">"
                + "            <div style=\"background-color: #006699;color: #ffffff;padding: 2px 25px;\">"
                + "                <h2>Pickme.lk <small>anytime.anywhere</small></h2>"
                + "            </div>"
                + "            <div style=\"margin: 15px 25px;\">"
                + "                <p><b>Hello, <i>" + email + "</i></b></p>"
                + "                <p>You have received your Current Password Successfully. You can Sign into your Account using this Password.</p>"
                + "                <p>Current Password : <span style=\"display: inline;line-height: 40px;margin: 0 auto;border: 2px solid #006699;border-radius: 4px;padding: 10px;\"><b>" + password + "</b></span></p>"
                + "                <p>But you better be change the current password because of the Security Issues.</p>"
                + "                <p></p>"
                + "            </div>"
                + "            <div style=\"background-color: #006699;color: #ffffff;padding: 2px 25px;\">"
                + "                <!--<span><span>Pickme.lk <small>anytime.anywhere</small></span></span>-->"
                + "                <span><small>Do not reply !</small></span>"
                + "            </div>"
                + "        </div>"
                + "    </body>"
                + "</html>";

        sendEmail(email, "Password Reset - Pickme.lk", html, null);
    }
}
