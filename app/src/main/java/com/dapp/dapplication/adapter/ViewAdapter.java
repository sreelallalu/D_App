package com.dapp.dapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dapp.dapplication.R;
import com.dapp.dapplication.model.ViewModel;
import com.dapp.dapplication.student.TrailVideo_Web;

import java.util.List;


public class ViewAdapter
        extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
    private List<ViewModel> arraylist;

    Context context;
    AlertDialog mDialog;
    private int type;




    public ViewAdapter(List<ViewModel> paramList, Context context, int type) {
        this.arraylist = paramList;
        this.type = type;
        this.context = context;
    }

    public int getItemCount() {
        return this.arraylist.size();
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, final int paramInt) {
        final ViewModel data = arraylist.get(paramInt);
        paramMyViewHolder.title.setText(data.getTitle());
        paramMyViewHolder.date.setText(data.getDate());

        paramMyViewHolder.content.setText(data.getContent());
        if (type == 1) {
            paramMyViewHolder.view.setText("Download");
            paramMyViewHolder.view.setClickable(true);
            paramMyViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, TrailVideo_Web.class);
                    intent.putExtra("linkurl",data.getLink());
                    intent.putExtra("type",1);
                    context.startActivity(intent);
                }
            });

        } else if (type == 2) {
            paramMyViewHolder.view.setText("Link");
            paramMyViewHolder.view.setClickable(true);
            paramMyViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, TrailVideo_Web.class);
                    intent.putExtra("linkurl",data.getLink());
                    intent.putExtra("type",2);

                    context.startActivity(intent);
                }
            });


        } else if (type == 3) {

            paramMyViewHolder.view.setClickable(false);
            paramMyViewHolder.view.setText(data.getLink());
        } else if (type == 4) {
            paramMyViewHolder.view.setVisibility(View.INVISIBLE);
        }




    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.viewcommon, paramViewGroup, false));
    }

    public class MyViewHolder
            extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView view;
        public TextView date;
        public TextView content;



        public MyViewHolder(View paramView) {
            super(paramView);
            this.title = ((TextView) paramView.findViewById(R.id.title_c));

            this.view = ((TextView) paramView.findViewById(R.id.view_c));
            this.content = ((TextView) paramView.findViewById(R.id.content_c));
            this.date = ((TextView) paramView.findViewById(R.id.date_c));
           if(type==1)
           {
               view.setText("Download");
               view.setClickable(true);
           }else
               if(type==2){
                   view.setText("Link");
                   view.setClickable(true);
               }
               else
                   if(type==3)
                   {

                       view.setClickable(false);
                   }

                   else
                       if(type==4)
                       {
                           view.setVisibility(View.INVISIBLE);
                       }



        }
    }
}
