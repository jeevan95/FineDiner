package com.jeevan.finediner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Session implements Serializable{
    public static ArrayList<Item> menuItems;
    public double total;
    public ArrayList<Item> arrOrder;
    public static Session instance; //the only instance of the class

    Date ordertakendate;
    int tableno;

    public Session(int n){
        arrOrder = new ArrayList<Item>();
        tableno = n;
        ordertakendate = Calendar.getInstance().getTime();
        instance = this;
    }

    public static Session getOrder(){
         return instance;
    }

    public void addOrder(ArrayList<Item> ar, Date odd, int t) {
        instance.arrOrder = ar;
        instance.ordertakendate = odd;
        instance.tableno = t;
    }

    public void addItem(Item i){
        instance.arrOrder.add(i);
    }



}
