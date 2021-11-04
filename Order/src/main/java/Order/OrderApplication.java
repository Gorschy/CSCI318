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
					OrderEntity order = restTemplate.getForObject("http://localhost:8080/orders/random", OrderEntity.class);
					assert order != null;
					
					double price = order.getQuantity() * restTemplate.getForObject("http://localhost:8082/products/" + Long.toString(order.getProdId()), Product.class).getPrice();
					OrderEvent orderEvent = new OrderEvent(order.getCustId(), order.getProdId(), order.getQuantity(), price);

					log.info(orderEvent.toString());
					//The binder name "order-outbound" is defined in the application.yml.
					streamBridge.send("order-outbound", orderEvent);
					Thread.sleep(2000);
				}
			}
			catch(InterruptedException ignored){}
		};
	}
}
