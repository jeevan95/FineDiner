package com.jeevan.finediner.fragments;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.Request;
import com.jeevan.finediner.R;
import com.jeevan.finediner.activity.OrderListAdapter;
import com.jeevan.finediner.Session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class TwoFragment extends Fragment{

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        // Inflate the layout for this fragment
        ExpandableListView exl = (ExpandableListView) v.findViewById(R.id.menu_list);
        exl.setAdapter(new OrderListAdapter(getActivity()));
        final Button bo = (Button) v.findViewById(R.id.btnOrder);

        if(Session.getSession().getTempOrder().isEmpty()){
            bo.setEnabled(false);
        }

        bo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setEnabled(false);
                bo.setText("AMEND ORDER");
                Session.getSession().sendRequest(new Request(3, Session.getSession().getTempOrder()));
                Session.getSession().setOrder();

                OrderListAdapter.getOrderListAdapter().update();
                OrderListAdapter.getOrderListAdapter().notifyDataSetChanged();
            }


        });
        return v;  }

}
