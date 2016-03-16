package com.jeevan.finediner.fragments;

import com.jeevan.finediner.R;
import com.jeevan.finediner.activity.ExpandableListAdapter;
import com.jeevan.finediner.Item;
import com.jeevan.finediner.Session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
        ExpandableListView exl = (ExpandableListView) v.findViewById(R.id.menu_list);
        exl.setAdapter(new ExpandableListAdapter(getActivity(), Session.menuItems));

        return v;
    }

}
