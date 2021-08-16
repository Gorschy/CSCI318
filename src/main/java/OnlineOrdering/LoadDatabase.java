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
  CommandLineRunner initDatabase(CustomerRepository repository) {

    //String companyName, String address, String country
    return args -> {
      log.info("Preloading " + repository.save(new Customer("Fake Company", "123 Fake Street", "Australia", new CustomerContact("Bilbo Baggins", "0474597599", "bilbo@email.com", "Team Lead"))));
      log.info("Preloading " + repository.save(new Customer("Not Real Company", "456 Not Real Avenue", "America", new CustomerContact("Frodo Baggins", "64942096", "frodo@email.com", "Not Team Lead"))));
    };
  }
}