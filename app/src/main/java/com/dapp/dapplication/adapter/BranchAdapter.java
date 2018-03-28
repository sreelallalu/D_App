package com.dapp.dapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dapp.dapplication.R;
import com.dapp.dapplication.model.BatchModel;

import java.util.List;

/**
 * Created by sreelal on 27/9/17.
 */


public class BranchAdapter extends BaseAdapter {


    List<BatchModel.Datum> arraylist;
    Context context;
    boolean state;
    private int langType;

    public BranchAdapter(Context context,
                         List<BatchModel.Datum> arrayofUsers) {

        arraylist=arrayofUsers;
        this.context=context;

    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int positon, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.spinner_item_view, parent,false);

        BatchModel.Datum users = arraylist.get(positon);

        TextView textView=view.findViewById(R.id.spinneride);
        textView.setText(users.getBrName());




        return view;
    }


}
