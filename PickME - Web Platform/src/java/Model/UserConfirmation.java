package Model;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Deeptha
 */
public class UserConfirmation extends SimpleTagSupport {

    private String confirm;

    @Override
    public void doTag() throws JspException, IOException {

        try {
            System.out.println(confirm);
            if (getConfirm() != null & !getConfirm().equals("")) {
                String decrypt = Controller.Services.decrypt(confirm);
                String msg = "";
                String type = "";
                String icon = "";

                String[] details = decrypt.split("#");

                int confirmResponse = Model.User.confirmUserEmail(details[0], details[1], details[2]);

                if (confirmResponse == Model.User.SUCCESS) {
                    msg = "Your Email is Successfully Confirmed ! To Keep Connect with us Please Sign into Your Account.";
                    type = "info";
                    icon = "ok";
                } else if (confirmResponse == Model.User.ALREADY_CONFIRMED) {
                    msg = "Your Email is Already Confirmed.";
                    type = "warning";
                    icon = "exclamation-sign";
                } else if (confirmResponse == Model.User.INVALID_REQUEST) {
                    msg = "Invalid Confirmation Request ! Please try again by Resending Email Confirmation.";
                    type = "danger";
                    icon = "remove";
                } else if (confirmResponse == Model.User.LINK_EXPIRED) {
                    msg = "Sorry, Expired Link.";
                    type = "danger";
                    icon = "time";
                } else if (confirmResponse == Model.User.ERROR) {
                    msg = "Error while Confirming Your Email, Please try again Later or Contact for Support.";
                    type = "danger";
                    icon = "warning-sign";
                }

                getJspContext().getOut().print("<div class='alert alert-" + type + "'><h4><span class='glyphicon glyphicon-" + icon + "' aria-hidden='true'></span> " + msg + "</h4><br><a href='http://localhost:8080/Pickme.lk' style='display:inline' class='btn btn-info btn-sm'>Home</a></div>");

            } else {
                getJspBody().invoke(null);
            }
        } catch (Exception e) {
            getJspBody().invoke(null);
        }
    }

    /**
     * @return the confirm
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

}
