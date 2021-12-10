package com.ot.vamk.oop.project.expensetracker;

import java.io.Serializable;

// Describes a product
// All expenses in this program are products

public class Product implements Serializable {
    private String name;
    private Double price;
    private String category;

    public Product(String name, Double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + "\t\t\t-\t\t\t" + price + "\t\t\t-\t\t\t" + category;
    }
}
