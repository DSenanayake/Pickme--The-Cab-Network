package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * CabDriver generated by hbm2java
 */
public class CabDriver  implements java.io.Serializable {


     private Integer cabDriverId;
     private ServiceProvider serviceProvider;
     private CabDriverLocation cabDriverLocation;
     private LoginDetails loginDetails;
     private ProfilePicture profilePicture;
     private CabDriverDetails cabDriverDetails;
     private Set serviceOrders = new HashSet(0);
     private Set driverContactNos = new HashSet(0);
     private Set leavingrequests = new HashSet(0);

    public CabDriver() {
    }

	
    public CabDriver(ServiceProvider serviceProvider, CabDriverLocation cabDriverLocation, LoginDetails loginDetails, ProfilePicture profilePicture, CabDriverDetails cabDriverDetails) {
        this.serviceProvider = serviceProvider;
        this.cabDriverLocation = cabDriverLocation;
        this.loginDetails = loginDetails;
        this.profilePicture = profilePicture;
        this.cabDriverDetails = cabDriverDetails;
    }
    public CabDriver(ServiceProvider serviceProvider, CabDriverLocation cabDriverLocation, LoginDetails loginDetails, ProfilePicture profilePicture, CabDriverDetails cabDriverDetails, Set serviceOrders, Set driverContactNos, Set leavingrequests) {
       this.serviceProvider = serviceProvider;
       this.cabDriverLocation = cabDriverLocation;
       this.loginDetails = loginDetails;
       this.profilePicture = profilePicture;
       this.cabDriverDetails = cabDriverDetails;
       this.serviceOrders = serviceOrders;
       this.driverContactNos = driverContactNos;
       this.leavingrequests = leavingrequests;
    }
   
    public Integer getCabDriverId() {
        return this.cabDriverId;
    }
    
    public void setCabDriverId(Integer cabDriverId) {
        this.cabDriverId = cabDriverId;
    }
    public ServiceProvider getServiceProvider() {
        return this.serviceProvider;
    }
    
    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    public CabDriverLocation getCabDriverLocation() {
        return this.cabDriverLocation;
    }
    
    public void setCabDriverLocation(CabDriverLocation cabDriverLocation) {
        this.cabDriverLocation = cabDriverLocation;
    }
    public LoginDetails getLoginDetails() {
        return this.loginDetails;
    }
    
    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }
    public ProfilePicture getProfilePicture() {
        return this.profilePicture;
    }
    
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }
    public CabDriverDetails getCabDriverDetails() {
        return this.cabDriverDetails;
    }
    
    public void setCabDriverDetails(CabDriverDetails cabDriverDetails) {
        this.cabDriverDetails = cabDriverDetails;
    }
    public Set getServiceOrders() {
        return this.serviceOrders;
    }
    
    public void setServiceOrders(Set serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
    public Set getDriverContactNos() {
        return this.driverContactNos;
    }
    
    public void setDriverContactNos(Set driverContactNos) {
        this.driverContactNos = driverContactNos;
    }
    public Set getLeavingrequests() {
        return this.leavingrequests;
    }
    
    public void setLeavingrequests(Set leavingrequests) {
        this.leavingrequests = leavingrequests;
    }




}

