package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientPrescriptionHistoryModel;

import java.util.ArrayList;

public class PrescriptionHistoryAdapter extends RecyclerView.Adapter<PrescriptionHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private  ArrayList<PatientPrescriptionHistoryModel> patientPrescriptionHistoryModelArrayList;

    public PrescriptionHistoryAdapter(Context mContext, ArrayList<PatientPrescriptionHistoryModel> patientPrescriptionHistoryModelArrayList) {
        this.mContext=mContext;
        this.patientPrescriptionHistoryModelArrayList=patientPrescriptionHistoryModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_prescription_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PatientPrescriptionHistoryModel patientPrescriptionHistoryModel=patientPrescriptionHistoryModelArrayList.get(position);

        holder.txtPatientName.setText(patientPrescriptionHistoryModel.getName());
        holder.txtServiceName.setText(patientPrescriptionHistoryModel.getS_name());
        holder.txtCost.setText(patientPrescriptionHistoryModel.getService_cost());
        holder.txtDate.setText(patientPrescriptionHistoryModel.getDate());
    }

    @Override
    public int getItemCount() {
        return patientPrescriptionHistoryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPatientName, txtServiceName, txtCost, txtDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtCost = itemView.findViewById(R.id.txtCost);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
