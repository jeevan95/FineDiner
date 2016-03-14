package com.jeevan.finediner.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeevan.finediner.R;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Item> menuItems;

    public ExpandableListAdapter(Activity context, ArrayList<Item> m) {
        this.context = context;
        this.menuItems = m;
    }


    public Object getChild(int groupPosition, int childPosition) {
        return menuItems.get(groupPosition).desc;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String itemDesc=  menuItems.get(groupPosition).desc;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);

        ImageView delete = (ImageView) convertView.findViewById(R.id.btnAddItem);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //
                try {
                    Socket s = new Socket("10.0.3.2", 3223);
                    ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                    os.writeObject(new Message(1, itemDesc));
                    os.flush();
                    os.reset();


                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        item.setText(itemDesc);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    public Object getGroup(int groupPosition) {
          return menuItems.get(groupPosition).name;
    }

    public int getGroupCount() {
        return menuItems.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String itemName = menuItems.get(groupPosition).name;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(itemName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}