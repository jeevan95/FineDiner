package com.jeevan.finediner.activity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Order {
    ArrayList<Item>[] arrOrder;
    Date ordertakendate;
    int tableno;

    public Order(ArrayList<Item>[] ar, Date odd, int t) {
        this.arrOrder = ar;
        this.ordertakendate = odd;
        this.tableno = t;
    }

    /**
     * Created by JEEVAN on 12/03/2016.
     */



}
