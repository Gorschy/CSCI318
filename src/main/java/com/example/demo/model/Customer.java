package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String companyName;
    private String address;
    private String country;

    // build a one-to-one relationship between
    // library and address
    // other relationships: @ManyToOne, @OneToMany
    @OneToOne
    @JoinColumn(name = "customerContact_id")
    @JsonIgnore
    private CustomerContact customerContact;

    public Customer() {

    }

    public Customer(String companyName, String address, String country, CustomerContact customerContact) {
        this.companyName = companyName;
        this.address = address; 
        this.country = country;
        this.customerContact = customerContact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return companyName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CustomerContact getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(CustomerContact customerContact) {
        this.customerContact = customerContact;
    }

}