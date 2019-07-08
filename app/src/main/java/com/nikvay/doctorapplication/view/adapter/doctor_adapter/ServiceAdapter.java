package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ServiceListModel;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<ServiceListModel> serviceListModelArrayList;

    public ServiceAdapter(Context mContext, ArrayList<ServiceListModel> serviceListModelArrayList)
    {
        this.mContext = mContext;
        this.serviceListModelArrayList = serviceListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.super_admin_service_list_adapter, parent, false);
        return new ServiceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServiceListModel serviceListModel=serviceListModelArrayList.get(position);

        holder.txtServiceName.setText(serviceListModel.getS_name());
        holder.txtDoctorName.setText(serviceListModel.getName());
        holder.txtServiceCost.setText(serviceListModel.getService_cost());
        holder.txtPhoneNo.setText(serviceListModel.getPhone_no());

    }

    @Override
    public int getItemCount() {
        return serviceListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtServiceName,txtDoctorName,txtServiceCost,txtPhoneNo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtServiceCost = itemView.findViewById(R.id.txtServiceCost);
            txtPhoneNo = itemView.findViewById(R.id.txtPhoneNo);

        }
    }
}
