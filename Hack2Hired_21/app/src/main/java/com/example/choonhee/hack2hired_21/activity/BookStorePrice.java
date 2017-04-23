package com.example.choonhee.hack2hired_21.activity;

import java.util.Comparator;

/**
 * Created by calvinlow on 22/04/2017.
 */

public class BookStorePrice implements Comparator<BookStorePrice> {

    private String name;
    private Double price;
    private String url;
    private boolean recomended;

    public BookStorePrice() {
    }

    public BookStorePrice(String name, Double price, String url, boolean recomended) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.recomended = recomended;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRecomended() {
        return recomended;
    }

    public void setRecomended(boolean recomended) {
        this.recomended = recomended;
    }

    @Override
    public int compare(BookStorePrice o1, BookStorePrice o2) {
        if (o1.getPrice() < o2.getPrice()) {
            return 1;
        } else if (o1.getPrice() > o2.getPrice()) {
            return -1;
        }
        return 0;
    }
}
