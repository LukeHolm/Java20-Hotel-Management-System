package com.company;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private int customerId;
    private long contactNumber;


    public Customer(String name, int customerId, long contactNumber) {
        this.name = name;
        this.customerId = customerId;
        this.contactNumber = contactNumber;
    }
}