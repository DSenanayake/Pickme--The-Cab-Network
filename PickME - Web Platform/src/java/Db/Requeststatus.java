package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Requeststatus generated by hbm2java
 */
public class Requeststatus  implements java.io.Serializable {


     private Integer requestStatusId;
     private String requestStatus;
     private Set leavingrequests = new HashSet(0);

    public Requeststatus() {
    }

    public Requeststatus(String requestStatus, Set leavingrequests) {
       this.requestStatus = requestStatus;
       this.leavingrequests = leavingrequests;
    }
   
    public Integer getRequestStatusId() {
        return this.requestStatusId;
    }
    
    public void setRequestStatusId(Integer requestStatusId) {
        this.requestStatusId = requestStatusId;
    }
    public String getRequestStatus() {
        return this.requestStatus;
    }
    
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
    public Set getLeavingrequests() {
        return this.leavingrequests;
    }
    
    public void setLeavingrequests(Set leavingrequests) {
        this.leavingrequests = leavingrequests;
    }




}


