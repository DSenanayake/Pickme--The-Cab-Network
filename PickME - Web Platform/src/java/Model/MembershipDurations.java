package Model;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MembershipDurations extends SimpleTagSupport {

    private int plan;

    @Override
    public void doTag() throws JspException, IOException {
        try {
            List<Db.MembershipDuration> durations = Model.MembershipPlan.getDurationsById(plan);
            if (!durations.isEmpty()) {
                for (Db.MembershipDuration duration : durations) {
                    getJspContext().setAttribute("id", duration.getMembershipDurationId());
                    getJspContext().setAttribute("months", duration.getDurationMonths().getMonths());
                    getJspContext().setAttribute("price", duration.getDurationFee());
                    getJspBody().invoke(null);
                }
            } else {
                getJspContext().getOut().print("<option value='NO_DUR'>No Duration Plans Available !</option>");
            }
        } catch (Exception e) {
            getJspContext().getOut().print("<option value='NO_DUR'>Error While Loading Durations</option>");
        }
    }

    /**
     * @return the plan
     */
    public int getPlan() {
        return plan;
    }

    /**
     * @param plan the plan to set
     */
    public void setPlan(int plan) {
        this.plan = plan;
    }

}
