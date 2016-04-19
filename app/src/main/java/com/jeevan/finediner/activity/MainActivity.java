package com.jeevan.finediner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.R;
import com.jeevan.finediner.Request;
import com.jeevan.finediner.Session;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnSimpleTabs;
    private Button btnConnect;
    private EditText txtcode;
    private View layy;

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
        txtcode = (EditText) findViewById(R.id.edtableno);
        layy = findViewById(R.id.relativeLayoutmain);
        btnConnect = (Button) findViewById(R.id.btnConn);
        btnConnect.setOnClickListener(this);

        setViewAndChildrenEnabled(layy,false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSimpleTabs:
                Intent in = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(in);
                break;
            case R.id.btnConn:

                Session.getSession(String.valueOf(txtcode.getText()), this);
                if(Session.getSession()!=null){
                    view.setEnabled(false);
                    setViewAndChildrenEnabled(layy,true);
                    new Listen().start();
                }



        }
    }
    private  void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }
    public class Listen extends Thread {
        public void run(){
            while(true){
                final Request rr = (Request)  Session.getSession().receive();
                switch (rr.getType()) {
                    case Request.KITCHEN_CONNECT:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(Session.getSession().getTimer()!=null) {
                                    Session.getSession().changeProgress(Long.valueOf( (int) rr.getContent()));
                                }
                            }
                        });
                        break;
                }
            }
        }
    }
}
