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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeevan.finediner.Item;
import com.jeevan.finediner.R;
import com.jeevan.finediner.Session;

import java.util.ArrayList;

public class MenuListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Item> item_list;

    private static MenuListAdapter expAdpMenu;

    public void update(ArrayList<Item> i)
    {
        this.item_list = i;
        notifyDataSetChanged();

    }
    public ArrayList<Item> getList(){
        return item_list;
    }
    public MenuListAdapter(Activity context, ArrayList<Item> menuItems) {
        this.context = context;
        this.item_list = menuItems;
        expAdpMenu = this;
    }

    public static MenuListAdapter getMenuListAdapter()    {
        return expAdpMenu;
    }


    public Object getChild(int groupPosition, int childPosition) {
        return item_list.get(groupPosition).getDesc();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Item selected = item_list.get(groupPosition);
        final String itemDesc=  selected.getDesc();
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_child, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.item_name);

        final EditText eq = (EditText) convertView.findViewById(R.id.quan);
        eq.setText(String.valueOf(selected.getQuantity()));
        ImageButton plus = (ImageButton) convertView.findViewById(R.id.btnPlus);
        plus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selected.setQuantity(selected.getQuantity() + 1);
                eq.setText(String.valueOf(selected.getQuantity()));

            }
        });
        ImageButton minus = (ImageButton) convertView.findViewById(R.id.btnMinus);
        minus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int qa =  selected.getQuantity();
                if (qa != 1) {
                    selected.setQuantity(qa- 1);
                    eq.setText(String.valueOf(qa -1));
                }
            }
        });
        Button add = (Button) convertView.findViewById(R.id.btnAddItem);
        final TextView total = (TextView) context.findViewById(R.id.total);
        final Button bo = (Button) context.findViewById(R.id.btnOrder);

        add.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                bo.setEnabled(true);
                Session.getSession().addItemtoOrder(new Item(selected));

                OrderListAdapter.getOrderListAdapter().update();
                OrderListAdapter.getOrderListAdapter().notifyDataSetChanged();
                total.setText("Total = £" + String.valueOf(Session.getSession().getTotal()));

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
            convertView = infalInflater.inflate(R.layout.menu_group,
                    null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.item_name);
        TextView itempr = (TextView) convertView.findViewById(R.id.item_price);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(i.getName());
        itempr.setText("£"+i.getPrice());
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}