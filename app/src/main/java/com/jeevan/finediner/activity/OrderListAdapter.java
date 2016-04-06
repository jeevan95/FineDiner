package com.jeevan.finediner.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.R;
import com.jeevan.finediner.Session;

import java.util.ArrayList;

public class OrderListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Item> item_list;

    private static OrderListAdapter expAdpOrder;

    public OrderListAdapter(Activity context) {
        this.context = context;
        update();
        expAdpOrder = this;
    }
    public void update(){
        ArrayList<Item> i = new ArrayList<>();
         i.addAll(Session.getSession().getPlacedOrder());
        i.addAll(Session.getSession().getTempOrder());
        item_list = i;
    }

    public static OrderListAdapter getOrderListAdapter()    {
        return expAdpOrder;
    }


    public Object getChild(int groupPosition, int childPosition) {
        return item_list.get(groupPosition).getDesc();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item selected =  item_list.get(groupPosition);
        final String itemDesc=  selected.getDesc();
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_child, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.item_nae);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.imageButton);
        final TextView total = (TextView) context.findViewById(R.id.total);

        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Session.getSession().removeItemOrder(groupPosition);
                update();
                notifyDataSetChanged();
           //     total.setText("Total = £" + String.valueOf(Session.getSession().getTotal()));
            }
        });

        item.setText(itemDesc);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    public Object getGroup(int groupPosition) {
        return item_list.get(groupPosition).getName();
    }

    public int getGroupCount() {
        return item_list.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Item i = item_list.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.order_group,
                    null);
        }
        if(Session.getSession().getPlacedOrder().contains(i)){
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

        }
        TextView item = (TextView) convertView.findViewById(R.id.item_name);
        TextView itempr = (TextView) convertView.findViewById(R.id.item_price);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(i.getQuantity() + " " + i.getName());
        itempr.setText("£" + i.getPrice());

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}