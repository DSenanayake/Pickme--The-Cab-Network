package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Administrator generated by hbm2java
 */
public class Administrator  implements java.io.Serializable {


     private Integer administratorId;
     private ProfilePicture profilePicture;
     private LoginDetails loginDetails;
     private AdminDetails adminDetails;
     private Set outsidePayouts = new HashSet(0);

    public Administrator() {
    }

	
    public Administrator(ProfilePicture profilePicture, LoginDetails loginDetails, AdminDetails adminDetails) {
        this.profilePicture = profilePicture;
        this.loginDetails = loginDetails;
        this.adminDetails = adminDetails;
    }
    public Administrator(ProfilePicture profilePicture, LoginDetails loginDetails, AdminDetails adminDetails, Set outsidePayouts) {
       this.profilePicture = profilePicture;
       this.loginDetails = loginDetails;
       this.adminDetails = adminDetails;
       this.outsidePayouts = outsidePayouts;
    }
   
    public Integer getAdministratorId() {
        return this.administratorId;
    }
    
    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }
    public ProfilePicture getProfilePicture() {
        return this.profilePicture;
    }
    
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }
    public LoginDetails getLoginDetails() {
        return this.loginDetails;
    }
    
    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }
    public AdminDetails getAdminDetails() {
        return this.adminDetails;
    }
    
    public void setAdminDetails(AdminDetails adminDetails) {
        this.adminDetails = adminDetails;
    }
    public Set getOutsidePayouts() {
        return this.outsidePayouts;
    }
    
    public void setOutsidePayouts(Set outsidePayouts) {
        this.outsidePayouts = outsidePayouts;
    }




}

