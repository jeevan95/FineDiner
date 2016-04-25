package com.jeevan.finediner;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Session implements Serializable{
    private Activity context;

    private ArrayList<Item> menuItems;
    private ArrayList<Item> tempOrder;
    private ArrayList<Item> placedOrder;
    private String tablecode;
    private long totaltime;
    private Socket soc;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private static Session instance; //the only instance of the class
    private CountDownTimer countTimer;
    public static ProgressBar srk;
    public  TextView time;
    private Session(String n, Activity a){
        tablecode = n;
        context =a;

        try{
            soc = new Socket("10.0.3.2", 3223);
            outStream = new ObjectOutputStream(soc.getOutputStream());
            inStream = new ObjectInputStream(soc.getInputStream());
            sendRequest(new Request(Request.NEW_CUSTOMER, tablecode));
            Request req = receive();
            if (req.getContent() instanceof String){
                Toast.makeText(a,""+req.getContent(),Toast.LENGTH_SHORT).show();
                instance = null;
            }else {
                setMenu((ArrayList<Item>) req.getContent());
                tempOrder = new ArrayList<>();
                placedOrder = new ArrayList<>();
                req = receive();
                placedOrder = (ArrayList<Item>) req.getContent();
                instance = this;
                Toast.makeText(a,"sd",Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getMenu(){ return menuItems; }
    public static Session getSession(){ return instance;  }
    public static Session getSession(String n,Activity a){ return new Session(n,a); }
    public CountDownTimer getTimer(){
        return countTimer;
    }
    public void setTimer(CountDownTimer d){
        countTimer=d;
    }

    public Request receive(){
        try {
            return (Request) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void sendRequest(Request req) {
        try {
            outStream.writeObject(req);
            outStream.flush();
            outStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Item> getTempOrder(){
        return tempOrder;
    }
    public ArrayList<Item> getPlacedOrder(){
        return placedOrder;
    }
    public void addItemtoOrder(Item it) {
        for (int i=0; i<tempOrder.size();i++) {
            if (it.getName().equals( tempOrder.get(i).getName())) {
                tempOrder.get(i).setQuantity(it.getQuantity());
                return;
            }
        }
        tempOrder.add(it);
    }
    public String getTableCode(){
        return tablecode;
    }
    public void removeItemOrder(int it) {
        it = it - placedOrder.size();
        tempOrder.remove(it);
    }
    public void changeProgress(long ee, long e2){

        if(getTimer()!=null){
            getTimer().cancel();
        }
        srk.setProgress((int) ee);
        srk.setMax((int)e2);

        setTimer(new CountDownTimer(srk.getMax() - srk.getProgress(), 100) {
            public void onTick(long millisUntilFinished) {
                srk.setProgress(srk.getProgress() + 100);
                time.setText("" + (srk.getMax() - srk.getProgress()) / 60000 + "m");

            }

            public void onFinish() {
                setTimer(null);
                time.setText("");

                //   srk.setProgress(0);
            }
        }.start());
    }
    public void setOrder(){
/*
        long remaining;
        if(getTimer()==null) {
            remaining = compareTime(tempOrder, 0);
        }
        else {
            getTimer().cancel();
            remaining = compareTime(tempOrder, srk.getMax() - srk.getProgress());
        }
        srk.setMax(srk.getProgress()+(int)remaining);

*/
        placedOrder.addAll(tempOrder);
        tempOrder = new ArrayList<>();
/*
        setTimer(new CountDownTimer(remaining, 100) {
            public void onTick(long millisUntilFinished) {
                srk.setProgress(srk.getProgress() + 100);
            }
            public void onFinish() {
                setTimer(null);
                srk.setProgress(0);
            }
        }.start());
*/
    }
    public double getTotal() {
        double total = 0;
        for (Item item: placedOrder)
            total+=item.getPrice()*item.getQuantity();
        return total;
    }
    public String getFormattedDouble(double d) {

        DecimalFormat df=new DecimalFormat("0.00");
        return df.format(d);
    }
    public double getNewTotal() {
        double total = getTotal();
        for (Item item: tempOrder)
            total+=item.getPrice()*item.getQuantity();
        return total;
    }
    public long compareTime(ArrayList<Item> a, long total) {
        for (Item item: a) {
            if (total < item.getTime()) {
                total = item.getTime();
            }
        }

        return total;
    }
    public long compareTimeNewOrder(){
        long remaining;
        if( getTimer()==null) {
            remaining =  compareTime( getTempOrder(), 0);
        }
        else {
            remaining =  compareTime(getTempOrder(), srk.getMax() - srk.getProgress());
        }
        return remaining;
    }
    public void setMenu(ArrayList<Item> ai){ menuItems = ai; }

}
