package uoc.edu.easyorderbackend.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.model.PaymentResponse;

@Deprecated
@RestController
@RequestMapping(UrlEasyOrderConstants.paymentUrl)
public class PaymentController {

    @PostMapping(UrlEasyOrderConstants.checkoutPayment)
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody Order order) throws StripeException {
        ResponseEntity<PaymentResponse> response;

        CustomerCreateParams customerParams = CustomerCreateParams.builder().build();
        Customer customer = Customer.create(customerParams);

        EphemeralKeyCreateParams ephemeralKeyParams =
                EphemeralKeyCreateParams.builder()
                        .setCustomer(customer.getId())
                        .build();

        RequestOptions ephemeralKeyOptions =
                new RequestOptions.RequestOptionsBuilder()
                .setStripeVersionOverride("2020-08-27")
                .build();

        EphemeralKey ephemeralKey =
                EphemeralKey.create(
                        ephemeralKeyParams,
                        ephemeralKeyOptions
                );

        Float price = order.getPrice() * 100;
        Long priceCent = price.longValue();

        PaymentIntentCreateParams paymenyIntentParams =
                PaymentIntentCreateParams.builder()
                        .setAmount(priceCent)
                        .setCurrency("eur")
                        .setCustomer(customer.getId())
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymenyIntentParams);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentIntent(paymentIntent.getClientSecret());
        paymentResponse.setEphemeralKey(ephemeralKey.getSecret());
        paymentResponse.setCustomer(customer.getId());

        response = new ResponseEntity<>(paymentResponse, HttpStatus.OK);

        return response;
    }
}
