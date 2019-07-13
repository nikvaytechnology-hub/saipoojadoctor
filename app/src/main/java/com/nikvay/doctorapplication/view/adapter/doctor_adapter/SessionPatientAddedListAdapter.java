package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SessionPatientAddedModel;

import java.util.ArrayList;

public class SessionPatientAddedListAdapter extends RecyclerView.Adapter<SessionPatientAddedListAdapter.MyViewHolder> {

    private Context mContext;
   private  ArrayList<SessionPatientAddedModel> sessionPatientAddedModelArrayList;

    public SessionPatientAddedListAdapter(Context mContext, ArrayList<SessionPatientAddedModel> sessionPatientAddedModelArrayList) {
        this.mContext=mContext;
        this.sessionPatientAddedModelArrayList=sessionPatientAddedModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_session_patient_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SessionPatientAddedModel sessionPatientAddedModel=sessionPatientAddedModelArrayList.get(position);

        holder.textPatientName.setText(sessionPatientAddedModel.getName());

    }

    @Override
    public int getItemCount() {
        return sessionPatientAddedModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textPatientName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textPatientName=itemView.findViewById(R.id.textPatientName);
        }
    }
}
