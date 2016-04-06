package com.jeevan.finediner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.R;
import com.jeevan.finediner.Request;
import com.jeevan.finediner.Session;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnSimpleTabs;
    private Button btnConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnSimpleTabs = (Button) findViewById(R.id.btnSimpleTabs);
        btnSimpleTabs.setOnClickListener(this);
        btnConnect = (Button) findViewById(R.id.btnConn);
        btnConnect.setOnClickListener(this);
        btnSimpleTabs.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSimpleTabs:
                Intent in = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(in);
                break;
            case R.id.btnConn:
                Session.getSession(2);
                new Listen().start();
                view.setEnabled(false);
                btnSimpleTabs.setEnabled(true);

        }
    }
    public class Listen extends Thread {
        public void run(){
            while(true){
                Request req = Session.getSession().receive();
                if (req.type==1){
                    Session.getSession().setMenu((ArrayList < Item >) req.o);
                }
            }
        }
    }
}