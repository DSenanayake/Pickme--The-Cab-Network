package Db;
// Generated Mar 9, 2015 10:30:38 PM by Hibernate Tools 3.6.0



/**
 * PaymentOption generated by hbm2java
 */
public class PaymentOption  implements java.io.Serializable {


     private Integer paymentOptionId;
     private PaymentMethod paymentMethod;
     private PaymentInfoStatus paymentInfoStatus;
     private LoginDetails loginDetails;
     private PriorityOrder priorityOrder;
     private String accountId;

    public PaymentOption() {
    }

	
    public PaymentOption(PaymentMethod paymentMethod, PaymentInfoStatus paymentInfoStatus, LoginDetails loginDetails, PriorityOrder priorityOrder) {
        this.paymentMethod = paymentMethod;
        this.paymentInfoStatus = paymentInfoStatus;
        this.loginDetails = loginDetails;
        this.priorityOrder = priorityOrder;
    }
    public PaymentOption(PaymentMethod paymentMethod, PaymentInfoStatus paymentInfoStatus, LoginDetails loginDetails, PriorityOrder priorityOrder, String accountId) {
       this.paymentMethod = paymentMethod;
       this.paymentInfoStatus = paymentInfoStatus;
       this.loginDetails = loginDetails;
       this.priorityOrder = priorityOrder;
       this.accountId = accountId;
    }
   
    public Integer getPaymentOptionId() {
        return this.paymentOptionId;
    }
    
    public void setPaymentOptionId(Integer paymentOptionId) {
        this.paymentOptionId = paymentOptionId;
    }
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public PaymentInfoStatus getPaymentInfoStatus() {
        return this.paymentInfoStatus;
    }
    
    public void setPaymentInfoStatus(PaymentInfoStatus paymentInfoStatus) {
        this.paymentInfoStatus = paymentInfoStatus;
    }
    public LoginDetails getLoginDetails() {
        return this.loginDetails;
    }
    
    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }
    public PriorityOrder getPriorityOrder() {
        return this.priorityOrder;
    }
    
    public void setPriorityOrder(PriorityOrder priorityOrder) {
        this.priorityOrder = priorityOrder;
    }
    public String getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }




}


