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
import android.widget.TextView;

import com.jeevan.finediner.R;
import com.jeevan.finediner.Request;
import com.jeevan.finediner.Session;
import com.jeevan.finediner.activity.OrderListAdapter;

public class OrderTab extends Fragment{

    TextView total;
    public OrderTab() {
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
        View v = inflater.inflate(R.layout.order_tab, container, false);
        // Inflate the layout for this fragment
        ExpandableListView exl = (ExpandableListView) v.findViewById(R.id.menu_list);
        exl.setAdapter(new OrderListAdapter(getActivity()));
        final Button bo = (Button) v.findViewById(R.id.btnOrder);
        Session.getSession().time = (TextView) v.findViewById(R.id.time);
        final TextView ntotal = (TextView) v.findViewById(R.id.newTotal);
        final TextView ntime = (TextView) v.findViewById(R.id.newTime);

        Session.getSession().time.setText("");
        total  = (TextView) v.findViewById(R.id.total);

        total.setText("Total = £" + Session.getSession().getFormattedDouble(Session.getSession().getTotal()));

        Session.getSession().srk = (ProgressBar)v.findViewById(R.id.seekBar);

        if(Session.getSession().getTempOrder().isEmpty()){
            bo.setEnabled(false);
        }
        final Button btRefresh = (Button) v.findViewById(R.id.btnRefresh);



        bo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setEnabled(false);
                Session.getSession().sendRequest(new Request(Request.NEW_ORDER, Session.getSession().getTempOrder()));
                Session.getSession().setOrder();
                total.setText("Total = £" + Session.getSession().getFormattedDouble(Session.getSession().getTotal()));
                ntime.setText("");
                ntotal.setText("");

                Session.getSession().sendRequest(new Request(Request.PROGRESS_UPDATE_CUSTOMER));
                OrderListAdapter.getOrderListAdapter().update();
                OrderListAdapter.getOrderListAdapter().notifyDataSetChanged();
            }


        });
        btRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                v.setEnabled(false);
                CountDownTimer t = new CountDownTimer(5000, 5000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        v.setEnabled(true);

                    }
                }.start();
                Session.getSession().sendRequest(new Request(Request.PROGRESS_UPDATE_CUSTOMER));
                Session.getSession().sendRequest(new Request(Request.PROGRESS_UPDATE_CUSTOMER));

            }


        });

        return v;  }

}
