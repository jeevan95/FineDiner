package com.jeevan.finediner;


import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private double total;
    private ArrayList<Item> lstOrder;
    public int tableno;
    public Boolean ready = false;
    public Order(int n){
        lstOrder = new ArrayList<>();
        tableno = n;
    }
    public void setOrder(ArrayList<Item> ar) {
        lstOrder = ar;

    }
    public double getTotal() {
        total = 0;
        for (Item item: lstOrder)
            total+=item.getPrice()*item.getQuantity();
        return total;
    }
    public ArrayList<Item> getOrderlist() {
        return lstOrder;
    }
}