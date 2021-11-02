package Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(ProductRepository prodRepository, ProductDetailRepository prodDetailRepository) {
    //String companyName, String address, String country
    return args -> {
      log.info("Preloading " + prodRepository.save(new Product("Food", "KekW Cereal", 3.45, 24)));
      log.info("Preloading " + prodRepository.save(new Product("Drink", "Mountain Dew", 2, 120)));
      log.info("Preloading " + prodDetailRepository.save(new ProductDetail("Food Description", "A Comment about a food")));
      log.info("Preloading " + prodDetailRepository.save(new ProductDetail("Drink Description", "A Comment about a drink")));
    };
  }
}