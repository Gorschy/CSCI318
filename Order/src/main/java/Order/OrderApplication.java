package OrderEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {

	private static final Logger log = LoggerFactory.getLogger(OrderEntity.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// in here I want to create a bunch of order objects every 2 seconds
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, StreamBridge streamBridge) throws Exception {
		return args -> {
			try {
				while (!Thread.currentThread().isInterrupted()){
					Long custId = 1l;
					Long prodId = 1l;
					Long quantity = 10l;
					OrderEntity order = new OrderEntity(custId, prodId, quantity);
					assert order != null;
					log.info(order.toString());
					//The binder name "order-outbound" is defined in the application.yml.
					streamBridge.send("order-outbound", order);
					Thread.sleep(2000);
				}
			}
			catch(InterruptedException ignored){}
		};
	}
}
