package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SessionPatientAddedModel;
import com.nikvay.doctorapplication.view.activity.doctor_activity.PatientDetailsActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.SessionDetailsActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.SessionPatientActivity;

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
        holder.email.setText(sessionPatientAddedModel.getEmail());
        holder.mobile_no.setText(sessionPatientAddedModel.getPhone_no());
        final String mno=sessionPatientAddedModel.getPhone_no();
        final String email=sessionPatientAddedModel.getEmail();
        final String name=sessionPatientAddedModel.getName();
        final String patient_id=sessionPatientAddedModel.getPatient_id();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(mContext, SessionPatientActivity.class);
                intent.putExtra("patient_id",patient_id);
                intent.putExtra("mno",mno);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionPatientAddedModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textPatientName,email,mobile_no;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textPatientName=itemView.findViewById(R.id.textPatientName);
            linearLayout=itemView.findViewById(R.id.ll_patient);
            email=itemView.findViewById(R.id.email);
            mobile_no=itemView.findViewById(R.id.mobile_no);
        }
    }
}
