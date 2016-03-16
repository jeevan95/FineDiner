package com.jeevan.finediner.fragments;

import com.jeevan.finediner.Message;
import com.jeevan.finediner.R;
import com.jeevan.finediner.activity.ExpandableListAdapter;
import com.jeevan.finediner.Session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        exl.setAdapter(new ExpandableListAdapter(getActivity()));
        Button bo = (Button) v.findViewById(R.id.btnOrder);
        bo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Socket s = new Socket("10.0.3.2", 3223);
                    ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                    os.writeObject(new Message(1, Session.instance));
                    os.flush();
                    os.reset();


                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        return v;  }

}
