package com.example.choonhee.hack2hired_21.activity;

/**
 * Created by calvinlow on 22/04/2017.
 */

public class BookStorePrice {

    private String name;
    private Double price;

    public BookStorePrice(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
