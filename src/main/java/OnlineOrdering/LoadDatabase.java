package OnlineOrdering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(CustomerRepository custRepository, ContactRepository contRepository, ProductRepository prodRepository) {
    //String companyName, String address, String country
    return args -> {
      log.info("Preloading " + custRepository.save(new Customer("Fake Company", "123 Fake Street", "Australia")));
      log.info("Preloading " + custRepository.save(new Customer("Not Real Company", "456 Not Real Avenue", "America")));
      log.info("Preloading " + contRepository.save(new Contact("Test Name", 412345678L, "fake@email.com", "Role")));
      log.info("Preloading " + contRepository.save(new Contact("Tester Namer", 412345678L, "not_real@email.com", "Position")));
      log.info("Preloading " + prodRepository.save(new Product("Food", "KekW Cereal", 3.45, 24)));
      log.info("Preloading " + prodRepository.save(new Product("Drink", "Mountain Dew", 2, 120)));
    };
  }
}