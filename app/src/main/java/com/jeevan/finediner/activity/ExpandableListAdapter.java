package com.jeevan.finediner.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.R;
import com.jeevan.finediner.Session;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Item> menuItems;
    static ExpandableListAdapter orderadp;
    public ExpandableListAdapter(Activity context) {
        this.context = context;
        this.menuItems = Session.instance.arrOrder;
        orderadp = this;
    }
    public ExpandableListAdapter(Activity context, ArrayList<Item> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }


    public Object getChild(int groupPosition, int childPosition) {
        return menuItems.get(groupPosition).desc;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item selected =  menuItems.get(groupPosition);
        final String itemDesc=  selected.desc;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.item_name);
        final TextView total = (TextView) context.findViewById(R.id.total);


        Button delete = (Button) convertView.findViewById(R.id.btnAddItem);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Session.instance.addItem(selected);
                notifyDataSetChanged();
                orderadp.notifyDataSetChanged();
                double totall = Double.parseDouble(total.getText().toString());
                total.setText(String.valueOf(totall + selected.price));
                /*
                try {



                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
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
        Item i = menuItems.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.item_name);
        TextView itempr = (TextView) convertView.findViewById(R.id.item_price);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(i.name);
        itempr.setText("£"+i.price);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}