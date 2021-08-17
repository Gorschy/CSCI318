package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerContact;
import com.example.demo.repository.CustomerContactRepository;
import com.example.demo.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(CustomerRepository custRepository, CustomerContactRepository contRepository) {
    //String companyName, String address, String country
    return args -> {
      log.info("Preloading " + custRepository.save(new Customer("Fake Company", "123 Fake Street", "Australia", null)));
      log.info("Preloading " + custRepository.save(new Customer("Not Real Company", "456 Not Real Avenue", "America", null)));
      log.info("Preloading " + contRepository.save(new CustomerContact("Test Name", 2, "fake@email.com", "Role")));
      log.info("Preloading " + contRepository.save(new CustomerContact("Tester Namer", 1, "not_real@email.com", "Position")));
    };
  }
}