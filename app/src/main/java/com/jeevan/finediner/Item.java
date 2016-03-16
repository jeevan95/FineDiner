package com.jeevan.finediner;

import java.io.Serializable;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Item implements Serializable {

    public String name;
    public String desc;
    public double price;
    Boolean isVegan;
    Boolean hasNuts;
    int quantity;

    public Item(String n, String ds, double pr) {
        this.name = n;
        this.desc = ds;
        this.price = pr;
    }

}

