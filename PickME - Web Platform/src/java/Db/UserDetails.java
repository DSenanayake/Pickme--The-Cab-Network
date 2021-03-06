package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UserDetails generated by hbm2java
 */
public class UserDetails  implements java.io.Serializable {


     private Integer userDetailsId;
     private Gender gender;
     private City city;
     private ProfilePicture profilePicture;
     private String firstname;
     private String lastname;
     private Date dob;
     private String address1;
     private String address2;
     private String mobile;
     private Date lastUpdated;
     private Set users = new HashSet(0);

    public UserDetails() {
    }

	
    public UserDetails(Gender gender, City city, ProfilePicture profilePicture) {
        this.gender = gender;
        this.city = city;
        this.profilePicture = profilePicture;
    }
    public UserDetails(Gender gender, City city, ProfilePicture profilePicture, String firstname, String lastname, Date dob, String address1, String address2, String mobile, Date lastUpdated, Set users) {
       this.gender = gender;
       this.city = city;
       this.profilePicture = profilePicture;
       this.firstname = firstname;
       this.lastname = lastname;
       this.dob = dob;
       this.address1 = address1;
       this.address2 = address2;
       this.mobile = mobile;
       this.lastUpdated = lastUpdated;
       this.users = users;
    }
   
    public Integer getUserDetailsId() {
        return this.userDetailsId;
    }
    
    public void setUserDetailsId(Integer userDetailsId) {
        this.userDetailsId = userDetailsId;
    }
    public Gender getGender() {
        return this.gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public City getCity() {
        return this.city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    public ProfilePicture getProfilePicture() {
        return this.profilePicture;
    }
    
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Date getDob() {
        return this.dob;
    }
    
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getAddress1() {
        return this.address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return this.address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Date getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }




}


