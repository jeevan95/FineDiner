package com.jeevan.finediner.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jeevan.finediner.R;
import com.jeevan.finediner.Request;
import com.jeevan.finediner.Session;
import com.jeevan.finediner.activity.OrderListAdapter;

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
        final ProgressBar sk = (ProgressBar) v.findViewById(R.id.seekBar);

        if(Session.getSession().getTempOrder().isEmpty()){
            bo.setEnabled(false);
        }
        if(!Session.getSession().getPlacedOrder().isEmpty()){
            sk.setVisibility(View.INVISIBLE);
        }
        else {
            sk.setVisibility(View.VISIBLE);

        }


        bo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setEnabled(false);
                Session.getSession().sendRequest(new Request(Request.NEW_ORDER, Session.getSession().getTempOrder()));
                sk.setVisibility(View.VISIBLE);
                Session.getSession().setOrder(sk);

                OrderListAdapter.getOrderListAdapter().update();
                OrderListAdapter.getOrderListAdapter().notifyDataSetChanged();
            }


        });

        return v;  }

}
