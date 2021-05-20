package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentResponse {
    private String paymentIntent;
    private String ephemeralKey;
    private String customer;

    public PaymentResponse() {
    }

    public PaymentResponse(String paymentIntent, String ephemeralKey, String customer) {
        this.paymentIntent = paymentIntent;
        this.ephemeralKey = ephemeralKey;
        this.customer = customer;
    }

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public String getEphemeralKey() {
        return ephemeralKey;
    }

    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
