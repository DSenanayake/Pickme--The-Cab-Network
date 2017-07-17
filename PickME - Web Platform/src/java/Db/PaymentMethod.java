package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * PaymentMethod generated by hbm2java
 */
public class PaymentMethod  implements java.io.Serializable {


     private Integer paymentMethodId;
     private String paymentMethod;
     private Set paymentOptions = new HashSet(0);
     private Set servicePaymentHistories = new HashSet(0);
     private Set paidHistories = new HashSet(0);

    public PaymentMethod() {
    }

    public PaymentMethod(String paymentMethod, Set paymentOptions, Set servicePaymentHistories, Set paidHistories) {
       this.paymentMethod = paymentMethod;
       this.paymentOptions = paymentOptions;
       this.servicePaymentHistories = servicePaymentHistories;
       this.paidHistories = paidHistories;
    }
   
    public Integer getPaymentMethodId() {
        return this.paymentMethodId;
    }
    
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    public String getPaymentMethod() {
        return this.paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public Set getPaymentOptions() {
        return this.paymentOptions;
    }
    
    public void setPaymentOptions(Set paymentOptions) {
        this.paymentOptions = paymentOptions;
    }
    public Set getServicePaymentHistories() {
        return this.servicePaymentHistories;
    }
    
    public void setServicePaymentHistories(Set servicePaymentHistories) {
        this.servicePaymentHistories = servicePaymentHistories;
    }
    public Set getPaidHistories() {
        return this.paidHistories;
    }
    
    public void setPaidHistories(Set paidHistories) {
        this.paidHistories = paidHistories;
    }




}

