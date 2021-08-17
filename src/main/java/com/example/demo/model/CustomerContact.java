package com.example.demo.model;

import javax.persistence.*;

@Entity
public class CustomerContact {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;
    private int phone;
    private String email;
    private String position;

    // this one-to-one relationship
    // is bidirectional
    @OneToOne(mappedBy = "customerContact")
    private Customer customer;

    public CustomerContact() {
    }

    public CustomerContact(String name, int phone, String email, String position) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
