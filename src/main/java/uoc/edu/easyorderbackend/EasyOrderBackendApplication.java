package uoc.edu.easyorderbackend;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;

@SpringBootApplication
public class EasyOrderBackendApplication {

	public static void main(String[] args) {
		Stripe.apiKey = EasyOrderConstants.STRIPE_API_KEY;
		SpringApplication.run(EasyOrderBackendApplication.class, args);
	}
}
