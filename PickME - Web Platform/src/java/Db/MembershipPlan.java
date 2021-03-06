package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * MembershipPlan generated by hbm2java
 */
public class MembershipPlan  implements java.io.Serializable {


     private Integer membershipPlanId;
     private MembershipType membershipType;
     private String membershipPlanName;
     private Integer cabs;
     private Integer drivers;
     private Integer coOpAgreements;
     private Double perMonth;
     private Set membershipUpgradeHistories = new HashSet(0);
     private Set membershipDurations = new HashSet(0);

    public MembershipPlan() {
    }

	
    public MembershipPlan(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
    public MembershipPlan(MembershipType membershipType, String membershipPlanName, Integer cabs, Integer drivers, Integer coOpAgreements, Double perMonth, Set membershipUpgradeHistories, Set membershipDurations) {
       this.membershipType = membershipType;
       this.membershipPlanName = membershipPlanName;
       this.cabs = cabs;
       this.drivers = drivers;
       this.coOpAgreements = coOpAgreements;
       this.perMonth = perMonth;
       this.membershipUpgradeHistories = membershipUpgradeHistories;
       this.membershipDurations = membershipDurations;
    }
   
    public Integer getMembershipPlanId() {
        return this.membershipPlanId;
    }
    
    public void setMembershipPlanId(Integer membershipPlanId) {
        this.membershipPlanId = membershipPlanId;
    }
    public MembershipType getMembershipType() {
        return this.membershipType;
    }
    
    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
    public String getMembershipPlanName() {
        return this.membershipPlanName;
    }
    
    public void setMembershipPlanName(String membershipPlanName) {
        this.membershipPlanName = membershipPlanName;
    }
    public Integer getCabs() {
        return this.cabs;
    }
    
    public void setCabs(Integer cabs) {
        this.cabs = cabs;
    }
    public Integer getDrivers() {
        return this.drivers;
    }
    
    public void setDrivers(Integer drivers) {
        this.drivers = drivers;
    }
    public Integer getCoOpAgreements() {
        return this.coOpAgreements;
    }
    
    public void setCoOpAgreements(Integer coOpAgreements) {
        this.coOpAgreements = coOpAgreements;
    }
    public Double getPerMonth() {
        return this.perMonth;
    }
    
    public void setPerMonth(Double perMonth) {
        this.perMonth = perMonth;
    }
    public Set getMembershipUpgradeHistories() {
        return this.membershipUpgradeHistories;
    }
    
    public void setMembershipUpgradeHistories(Set membershipUpgradeHistories) {
        this.membershipUpgradeHistories = membershipUpgradeHistories;
    }
    public Set getMembershipDurations() {
        return this.membershipDurations;
    }
    
    public void setMembershipDurations(Set membershipDurations) {
        this.membershipDurations = membershipDurations;
    }




}


