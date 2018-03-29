package com.dapp.dapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dapp.dapplication.admin_module.AddAttendance;
import com.dapp.dapplication.R;
import com.dapp.dapplication.admin_module.StudentView;
import com.dapp.dapplication.model.StudentModel;

import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */


public class StudentAdapter
        extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    private List<StudentModel.Datum> arraylist;

    Context context;
    AlertDialog mDialog;

    public StudentAdapter(List<StudentModel.Datum> paramList, Context context) {
        this.arraylist = paramList;

        this.context = context;
    }

    public int getItemCount() {
        return this.arraylist.size();
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, final int paramInt) {
        final StudentModel.Datum data = arraylist.get(paramInt);
        paramMyViewHolder.name.setText(data.getStName());
        paramMyViewHolder.regId.setText(data.getStRegid());
        paramMyViewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean attendance = true;
                if (context instanceof AddAttendance) {
                    attendance = true;
                } else {
                    attendance = false;
                }

                Intent intent = new Intent(context, StudentView.class);


                intent.putExtra("name", data.getStName());
                intent.putExtra("email", data.getStEmail());
                intent.putExtra("regid", data.getStRegid());
                intent.putExtra("studid", data.getStId() + "");
                intent.putExtra("br_id", data.getStBranchid() + "");
                intent.putExtra("se_id", data.getStSemid() + "");
                intent.putExtra("attendance", attendance);
                ((Activity) context).startActivity(intent);
            }
        });


    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.studnet_singlelist, paramViewGroup, false));
    }

    public class MyViewHolder
            extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView regId;

        public LinearLayout click;


        public MyViewHolder(View paramView) {
            super(paramView);
            this.name = ((TextView) paramView.findViewById(R.id.name));

            this.regId = ((TextView) paramView.findViewById(R.id.regId));
            this.click = ((LinearLayout) paramView.findViewById(R.id.cardid));


        }
    }
}
