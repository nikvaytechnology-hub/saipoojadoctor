package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.view.activity.AppointmentListActivity;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<AppoinmentListModel> appoinmentListModelArrayList;

    public AppointmentListAdapter(Context mContext, ArrayList<AppoinmentListModel> appoinmentListModelArrayList) {
        this.mContext = mContext;
        this.appoinmentListModelArrayList = appoinmentListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        AppoinmentListModel appoinmentListModel= appoinmentListModelArrayList.get(position);


        holder.week_of_apppoinment.setText(appoinmentListModel.getWeek_of_apppoinment());
        holder.date_of_Time.setText(String.valueOf(appoinmentListModel.getDate_of_Time()));
        holder.doctor_name.setText(String.valueOf(appoinmentListModel .getDoctor_name()));

        holder.appoinment_Duration.setText(String.valueOf(appoinmentListModel.getAppoinment_Duration()));
        holder.appoinment_service.setText(String.valueOf(appoinmentListModel .getAppoinment_service()));
        holder.appoinment_coast.setText(String.valueOf(appoinmentListModel.getAppoinment_coast()));


    }

    @Override
    public int getItemCount() {
        return appoinmentListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView week_of_apppoinment, day_of_month, date_of_Time, doctor_name, appoinment_Duration, appoinment_service, appoinment_coast;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            week_of_apppoinment = itemView.findViewById(R.id.week_of_apppoinment);
            // day_of_month = itemView.findViewById(R.id.day_of_month);
            date_of_Time = itemView.findViewById(R.id.date_of_Time);
            doctor_name = itemView.findViewById(R.id.doctor_name);
            appoinment_Duration = itemView.findViewById(R.id.appoinment_Duration);
            appoinment_service = itemView.findViewById(R.id.appoinment_service);
            appoinment_coast = itemView.findViewById(R.id.appoinment_coast);
        }
    }
}
