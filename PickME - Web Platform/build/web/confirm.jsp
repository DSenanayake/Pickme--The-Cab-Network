<%-- 
    Document   : confirm
    Created on : Jan 30, 2015, 12:24:53 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home - Pickme.lk</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon">
    </head>
    <body>

        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>


        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->

        <!-- header start -->
        <%@include file="_/components/jsp/header-top.jsp"%>
        <!-- header end -->

        <!-- middle panel start -->
        <%@include file="_/components/jsp/middle-panel.jsp"%>
        <!-- middle panel end -->

        <div class="container-fluid custom-bg">
            <!-- desc -->
            <div class="row">
                <div class="col-lg-8">
                    <h2 class="page-header">Payment Status</h2>
                    <%            /*==================================================================
                         PayPal Express Checkout Call
                         ===================================================================
                         */
                        String token = (String) session.getAttribute("TOKEN");
                        System.out.print(token);
                        if (token != null) {
                    %>
                    <%@include file="paypalfunctions.jsp" %>
                    <%
                        try {
                            /*
                             '------------------------------------
                             ' Get the token parameter value stored in the session 
                             ' from the previous SetExpressCheckout call
                             '------------------------------------
                             */
                            //String token =  session.getAttribute("TOKEN")toString();

                            /*
                             '------------------------------------
                             ' The paymentAmount is the total value of 
                             ' the shopping cart, that was set 
                             ' earlier in a session variable 
                             ' by the shopping cart page
                             '------------------------------------
                             */
                            String finalPaymentAmount = session.getAttribute("Payment_Amount").toString();

                            System.out.println("[PAYPAL SERVICE] - setting Payer Id");
                            session.setAttribute("PAYERID", request.getParameter("PayerID"));
                            /*
                             '------------------------------------
                             ' Calls the DoExpressCheckoutPayment API call
                             '
                             ' The ConfirmPayment function is defined in the file PayPalFunctions.jsp,
                             ' that is included at the top of this file.
                             '-------------------------------------------------
                             */

                            System.out.println("[PAYPAL SERVICE] - Atempting to Confirm.");
                            HashMap nvp = ConfirmPayment(finalPaymentAmount, session, request);

                            System.out.println("[PAYPAL SERVICE] - Confirmed.");
                            String strAck = nvp.get("ACK").toString();

                            System.out.println("[PAYPAL SERVICE] - Status : " + strAck);

                            int orderStatus = Model.PaymentStatus.ERROR;
                            double amount = Double.parseDouble(finalPaymentAmount);
                            double paypalFee = 0.0;
                            java.util.Date dateTime = new java.util.Date();
                            String transId = null;

                            if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
                                /*
                                 '********************************************************************************************************************
                                 '
                                 ' THE PARTNER SHOULD SAVE THE KEY TRANSACTION RELATED INFORMATION LIKE 
                                 '                    transactionId & orderTime 
                                 '  IN THEIR OWN  DATABASE
                                 ' AND THE REST OF THE INFORMATION CAN BE USED TO UNDERSTAND THE STATUS OF THE PAYMENT 
                                 '
                                 '********************************************************************************************************************
                                 */

                                String transactionId = nvp.get("PAYMENTINFO_0_TRANSACTIONID").toString(); // ' Unique transaction ID of the payment. Note:  If the PaymentAction of the request was Authorization or Order, this value is your AuthorizationID for use with the Authorization & Capture APIs. 
                                String transactionType = nvp.get("PAYMENTINFO_0_TRANSACTIONTYPE").toString(); //' The type of transaction Possible values: l  cart l  express-checkout 
                                String paymentType = nvp.get("PAYMENTINFO_0_PAYMENTTYPE").toString();  //' Indicates whether the payment is instant or delayed. Possible values: l  none l  echeck l  instant 
                                String orderTime = nvp.get("PAYMENTINFO_0_ORDERTIME").toString();  //' Time/date stamp of payment
                                String amt = nvp.get("PAYMENTINFO_0_AMT").toString();  //' The final amount charged, including any shipping and taxes from your Merchant Profile.
                                String currencyCode = nvp.get("PAYMENTINFO_0_CURRENCYCODE").toString();  //' A three-character currency code for one of the currencies listed in PayPay-Supported Transactional Currencies. Default: USD. 
                                String feeAmt = nvp.get("PAYMENTINFO_0_FEEAMT").toString();  //' PayPal fee amount charged for the transaction
                                //                        String settleAmt = nvp.get("PAYMENTINFO_0_SETTLEAMT").toString();  //' Amount deposited in your PayPal account after a currency conversion.
                                //                        String taxAmt = nvp.get("PAYMENTINFO_0_TAXAMT").toString();  //' Tax charged on the transaction.
                                //  String exchangeRate = nvp.get("PAYMENTINFO_0_EXCHANGERATE").toString();  //' Exchange rate if a currency conversion occurred. Relevant only if your are billing in their non-primary currency. If the customer chooses to pay with a currency other than the non-primary currency, the conversion occurs in the customer’s account.

                                transId = transactionId;
                                amount = Double.parseDouble(amt);
                                paypalFee = Double.parseDouble(feeAmt);

                                System.out.println("[PAYPAL SERVICE] - Got All Variables");
                                /*
                                 ' Status of the payment: 
                                 'Completed: The payment has been completed, and the funds have been added successfully to your account balance.
                                 'Pending: The payment is pending. See the PendingReason element for more information. 
                                 */
                                String paymentStatus = nvp.get("PAYMENTINFO_0_PAYMENTSTATUS").toString();
                                if (paymentStatus.equalsIgnoreCase("Completed")) {
                                    orderStatus = Model.PaymentStatus.COMPLETED;
                    %>

                    <div class="alert alert-success">
                        <h4 class="page-header">Your Payment has been Completed Successfully.</h4>
                        <h5>Transaction ID : <i><%=transactionId%></i></h5>
                        <h5>Payment Amount : <i><%=amt%></i></h5>
                        <h5>Payment Status : <i><%=paymentStatus%></i></h5>
                        <h5>Order Time     : <i><%=orderTime%></i></h5>
                    </div>

                    <%
                    } else if (paymentStatus.equalsIgnoreCase("Pending")) {
                        orderStatus = Model.PaymentStatus.PENDING;
                        /*
                         'The reason the payment is pending:
                         '  none: No pending reason 
                         '  address: The payment is pending because your customer did not include a confirmed shipping address and your Payment Receiving Preferences is set such that you want to manually accept or deny each of these payments. To change your preference, go to the Preferences section of your Profile. 
                         '  echeck: The payment is pending because it was made by an eCheck that has not yet cleared. 
                         '  intl: The payment is pending because you hold a non-U.S. account and do not have a withdrawal mechanism. You must manually accept or deny this payment from your Account Overview. 		
                         '  multi-currency: You do not have a balance in the currency sent, and you do not have your Payment Receiving Preferences set to automatically convert and accept this payment. You must manually accept or deny this payment. 
                         '  verify: The payment is pending because you are not yet verified. You must verify your account before you can accept this payment. 
                         '  other: The payment is pending for a reason other than those listed above. For more information, contact PayPal customer service. 
                         */
                        String pendingReason = nvp.get("PAYMENTINFO_0_PENDINGREASON").toString();
                        System.out.println(pendingReason);

                    %>
                    <div class="alert alert-warning">
                        <h4 class="page-header">Your Payment is Pending. We'l inform you after Payment is Completed.</h4>
                        <h5>Pending Reason : <i><%=pendingReason%></i></h5>
                        <h5>Transaction ID : <i><%=transactionId%></i></h5>
                        <h5>Payment Amount : <i><%=amt%></i></h5>
                        <h5>Payment Status : <i><%=paymentStatus%></i></h5>
                        <h5>Order Time     : <i><%=orderTime%></i></h5>
                    </div>
                    <%
                        }

                        /*
                         'The reason for a reversal if TransactionType is reversal:
                         '  none: No reason code 
                         '  chargeback: A reversal has occurred on this transaction due to a chargeback by your customer. 
                         '  guarantee: A reversal has occurred on this transaction due to your customer triggering a money-back guarantee. 
                         '  buyer-complaint: A reversal has occurred on this transaction due to a complaint about the transaction from your customer. 
                         '  refund: A reversal has occurred on this transaction because you have given the customer a refund. 
                         '  other: A reversal has occurred on this transaction due to a reason not listed above. 
                         */
                        String reasonCode = nvp.get("PAYMENTINFO_0_REASONCODE").toString();
                        System.out.println(reasonCode);

                    } else {
                        orderStatus = Model.PaymentStatus.ERROR;
                        // Display a user friendly Error on the page using any of the following error information returned by PayPal
                        String ErrorCode = nvp.get("L_ERRORCODE0").toString();
                        String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
                        String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
                        String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
                    %>
                    <div class="alert alert-danger">
                        <h4 class="page-header">Sorry, Your Payment has not Completed.</h4>
                        <h5><%=ErrorCode%> - <%=ErrorShortMsg%></h5>
                        <h5>Description: <i><%=ErrorLongMsg%></i></h5>
                        <h5><%=ErrorSeverityCode%></i></h5>
                    </div>
                    <%
                            }

                            //Saving Payment History for Membeship Upgrade
                            if (session.getAttribute("CURRENT_PLAN") != null) {
                                int mUpgrade = Integer.parseInt(session.getAttribute("CURRENT_PLAN").toString());

                                if (mUpgrade != Model.MembershipPlan.NO_CURRENT_MEMBERSHIP) {
                                    Model.MembershipPlan.savePaymentHistory(transId, amount,paypalFee, dateTime, mUpgrade, orderStatus);
                                }
                            } else if (session.getAttribute("CURRENT_INVOICE") != null) { //saving payment for Invoice
                                int cInvoice = Integer.parseInt(session.getAttribute("CURRENT_INVOICE").toString());

                                if (cInvoice != -1) {
                                    Model.Invoice.savePaymentHistory(transId, cInvoice, amount,paypalFee, 0.0, dateTime, orderStatus, Model.PaymentStatus.METHOD_PAYPAL);
                                }
                            }

                            session.setAttribute("CURRENT_PLAN", null);
                            session.setAttribute("CURRENT_INVOICE", null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                    %>
                    <div class="alert alert-danger"><b>Sorry, Cannot Confirm your Payment Status according to Timeout.</b></div>
                    <%}%>
                </div>
                <div class="col-lg-4">
                    <%@include file="_/components/jsp/home-stats-side.jsp"%>
                    <%@include file="_/components/jsp/home-top-ten-services.jsp"%>
                    <%@include file="_/components/jsp/home-find-by-city.jsp"%>
                    <%@include file="_/components/jsp/home-customer-feedback.jsp"%>
                </div>
            </div>
        </div><!-- main container -->
    </body>
</html>
