package com.jeevan.finediner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by JEEVAN on 12/03/2016.
 */
public class Session implements Serializable{
    private Order order;
    private ArrayList<Item> menuItems;
    private ArrayList<Item> tempOrder;
    private ArrayList<Item> placedOrder;

    private Socket soc;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private static Session instance; //the only instance of the class

    private Session(int n){
        order = new Order(n);
        tempOrder = new ArrayList<>();
        placedOrder = new ArrayList<>();

        try{
            soc = new Socket("10.0.3.2", 3223);
            outStream = new ObjectOutputStream(soc.getOutputStream());
            inStream = new ObjectInputStream(soc.getInputStream());
            instance = this;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getMenu(){ return menuItems; }
    public Order getOrder(){ return instance.order;  }
    public static Session getSession(){ return instance;  }
    public static Session getSession(int n){ return new Session(n); }

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
    public void removeItemOrder(int it) {
        tempOrder.remove(it);
    }
    public void setOrder(){
        placedOrder.addAll(tempOrder);
       tempOrder = new ArrayList<>();
    }

    public void setMenu(ArrayList<Item> ai){ menuItems = ai; }

}
