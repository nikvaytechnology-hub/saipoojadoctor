package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.NotificationListModel;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<NotificationListModel> notificationListModelArrayList;


    public NotificationListAdapter(Context mContext, ArrayList<NotificationListModel> notificationListModelArrayList) {
        this.mContext = mContext;
        this.notificationListModelArrayList = notificationListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationListModel notificationListModel=notificationListModelArrayList.get(position);

        holder.textTitle.setText(notificationListModel.getName());
        holder.textDate.setText(notificationListModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return notificationListModelArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}
