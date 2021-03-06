package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * ServiceStatus generated by hbm2java
 */
public class ServiceStatus  implements java.io.Serializable {


     private Integer serviceStatusId;
     private String serviceStatus;
     private Set payLaterServices = new HashSet(0);

    public ServiceStatus() {
    }

    public ServiceStatus(String serviceStatus, Set payLaterServices) {
       this.serviceStatus = serviceStatus;
       this.payLaterServices = payLaterServices;
    }
   
    public Integer getServiceStatusId() {
        return this.serviceStatusId;
    }
    
    public void setServiceStatusId(Integer serviceStatusId) {
        this.serviceStatusId = serviceStatusId;
    }
    public String getServiceStatus() {
        return this.serviceStatus;
    }
    
    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    public Set getPayLaterServices() {
        return this.payLaterServices;
    }
    
    public void setPayLaterServices(Set payLaterServices) {
        this.payLaterServices = payLaterServices;
    }




}


