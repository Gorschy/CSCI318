package com.example.demo.controller;

public class CustomerContactNotFoundException extends RuntimeException {

    public CustomerContactNotFoundException(Long id) {
    super("Could not find customer contact with following id " + id);
  }
}