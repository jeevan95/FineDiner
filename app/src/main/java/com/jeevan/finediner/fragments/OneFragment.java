package com.jeevan.finediner.fragments;

import com.jeevan.finediner.R;
import com.jeevan.finediner.activity.Item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class OneFragment extends Fragment{
    ArrayList<Item> menuItems;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        // Inflate the layout for this fragment
        menuItems = (ArrayList<Item>)getArguments().getSerializable("menuItems");

        return v;
    }

}
