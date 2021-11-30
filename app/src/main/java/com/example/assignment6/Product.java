package com.example.assignment6;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int imgID;
    private double price;

    public Product(String name, int imgID, double price) {
        this.name = name;
        this.imgID = imgID;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}