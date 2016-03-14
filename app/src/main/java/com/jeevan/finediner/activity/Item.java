package com.jeevan.finediner.activity;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Item {
    String name;
    String desc;
    Double price;
    Boolean isVegan;
    Boolean hasNuts;
    int quantity;

    public Item(String n, String ds, Double pr) {
        this.name = n;
        this.desc = ds;
        this.price = pr;
    }

}