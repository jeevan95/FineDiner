package com.jeevan.finediner;

import java.io.Serializable;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Item implements Serializable {

    private String name;
    private String desc;
    private double price;
    private int quantity =1;
    public Item(Item i ){
        name = i.name;
        desc = i.desc;
        price = i.price;
        quantity = i.quantity;

    }
    public Item(String n, String ds, double pr) {
        this.name = n;
        this.desc = ds;
        this.price = pr;
    }
    public void setQuantity(int q){
        quantity = q;
    }
    public String getName(){return name;}
    public String getDesc(){return desc;}
    public double getPrice(){return price;}
    public int getQuantity(){return quantity;}

}

