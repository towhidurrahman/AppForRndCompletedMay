package com.example.tuhinthegreat.appforrndcompletedmay;

/**
 * Created by TUHIN THE GREAT on 5/11/2017.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nesar on 12/5/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<ReportHeaderList> HeaderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Date,Particular,amount,type;

        public MyViewHolder(View view) {
            super(view);
//            time = (TextView) view.findViewById(R.id.date);
            Date = (TextView) view.findViewById(R.id.date);
            Particular = (TextView) view.findViewById(R.id.particular);
            amount=(TextView)view.findViewById(R.id.amount);
            type=(TextView)view.findViewById(R.id.type);
//            location = (TextView) view.findViewById(R.id.total);
        }
    }


    public ListAdapter(List<ReportHeaderList> HeaderList) {
        this.HeaderList = HeaderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReportHeaderList header = HeaderList.get(position);
//        holder.time.setText(movie.getTime());
        holder.Date.setText(header.getDate());
        holder.Particular.setText(header.getParticular());
        holder.amount.setText(header.getAmount());
        holder.type.setText(header.getType());
//        holder.location.setText(movie.getLocation());
    }

    @Override
    public int getItemCount() {
        return HeaderList.size();
    }
}
