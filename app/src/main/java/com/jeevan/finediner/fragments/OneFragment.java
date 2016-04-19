package com.jeevan.finediner.fragments;

import com.jeevan.finediner.R;
import com.jeevan.finediner.activity.MenuListAdapter;
import com.jeevan.finediner.activity.OrderListAdapter;
import com.jeevan.finediner.Item;
import com.jeevan.finediner.Session;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class OneFragment extends Fragment {
    ExpandableListView exl;
    Button btFilter;
    Button btSort;
    final ArrayList<String> mSelectedItems = new ArrayList();  // Where we track the selected items
    ArrayList<Item> filtrdmenu = new ArrayList<>();

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
        exl = (ExpandableListView) v.findViewById(R.id.expandableListView);
        btFilter = (Button) v.findViewById(R.id.btFilter);

        setupMenu(Item.STARTER);
        btSort = (Button) v.findViewById(R.id.btSort);

        Button btStrt = (Button) v.findViewById(R.id.imageButton2);
        Button btMain = (Button) v.findViewById(R.id.imageButton3);
        Button btSide = (Button) v.findViewById(R.id.imageButton4);
        Button btDess = (Button) v.findViewById(R.id.imageButton5);
        Button btDrnk = (Button) v.findViewById(R.id.imageButton6);

        btStrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupMenu(Item.STARTER);
            }
        });
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupMenu(Item.MAIN);
            }
        });
        btSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupMenu(Item.SIDE);
            }
        });
        btDess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupMenu(Item.DESSERT);
            }
        });
        btDrnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupMenu(Item.DRINK);
            }
        });

        btSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myList[] = { "Name", "Price" };
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ksjdf")
                        .setItems(myList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                ArrayList<Item> ii = MenuListAdapter.getMenuListAdapter().getList();

                                if(which==1){
                                    Collections.sort(ii,Item.ItPriceComp);
                                    MenuListAdapter.getMenuListAdapter().update(ii);

                                }
                                else {
                                    Collections.sort(ii,Item.ItNameComp);
                                    MenuListAdapter.getMenuListAdapter().update(ii);}

                            }
                        });
                builder.create().show();
            }

        });
        btFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final String myList[] = { "Vegan", "Nuts free", "Dairy free" };
                // Set the dialog title
                builder.setTitle("sddd")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(myList, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            mSelectedItems.add(myList[which]);
                                        } else if (mSelectedItems.contains(myList[which])) {
                                            // Else, if the item is already in the array, remove it
                                            mSelectedItems.remove(myList[which]);
                                        }
                                    }
                                })
                                // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                filter();

                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create().show();
            }
        });

        return v;
    }
    private void filter(){
        ArrayList<Item> aii = new ArrayList<>();
        for (Item i:filtrdmenu) {
            if(i.hasProperty(mSelectedItems)){
                aii.add(i);
            }
        }
        mSelectedItems.clear();
        MenuListAdapter.getMenuListAdapter().update(aii);
    }
    private void setupMenu(int cat){

        if(cat==Item.DRINK){
            btFilter.setEnabled(false);
        }
        else
            btFilter.setEnabled(true);
        filtrdmenu.clear();
        ArrayList<Item> fullmenu = Session.getSession().getMenu();
        for (Item item: fullmenu) {
            if (cat == item.getCategory()) {
                filtrdmenu.add(item);
            }
        }

        exl.setAdapter(new MenuListAdapter(getActivity(),filtrdmenu) );
    }


}
