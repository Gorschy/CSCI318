package com.example.demo.repository;

import com.example.demo.model.CustomerContact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {

}
