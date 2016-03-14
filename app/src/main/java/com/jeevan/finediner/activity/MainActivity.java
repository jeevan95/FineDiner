package com.jeevan.finediner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.jeevan.finediner.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnSimpleTabs;
    ArrayList<Item> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSimpleTabs = (Button) findViewById(R.id.btnSimpleTabs);

        btnSimpleTabs.setOnClickListener(this);

    }
    void setupMenu(){
        menuItems = new ArrayList<Item>();
        menuItems.add(new Item("Sambar","bestaa",12.2));
        menuItems.add(new Item("Aviyal", "muttua", 12.2));
        menuItems.add(new Item("Pulisery","adipoli",12.2));
        menuItems.add(new Item("Uppery", "pinne prathyekam parayano", 12.2));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSimpleTabs:
                Intent in = new Intent(MainActivity.this, OrderActivity.class);
                in.putExtra("menuItems",menuItems);
                startActivity(in);
                break;

        }
    }
}