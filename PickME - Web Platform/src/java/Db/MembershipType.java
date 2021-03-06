package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * MembershipType generated by hbm2java
 */
public class MembershipType  implements java.io.Serializable {


     private Integer membershipTypeId;
     private String membership;
     private Set membershipPlans = new HashSet(0);

    public MembershipType() {
    }

    public MembershipType(String membership, Set membershipPlans) {
       this.membership = membership;
       this.membershipPlans = membershipPlans;
    }
   
    public Integer getMembershipTypeId() {
        return this.membershipTypeId;
    }
    
    public void setMembershipTypeId(Integer membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }
    public String getMembership() {
        return this.membership;
    }
    
    public void setMembership(String membership) {
        this.membership = membership;
    }
    public Set getMembershipPlans() {
        return this.membershipPlans;
    }
    
    public void setMembershipPlans(Set membershipPlans) {
        this.membershipPlans = membershipPlans;
    }




}


