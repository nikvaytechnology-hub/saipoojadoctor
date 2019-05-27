package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.PatientDetailsActivity;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PatientModel> patientModelArrayList;

    public PatientAdapter(Context mContext, ArrayList<PatientModel> patientModelArrayList) {
        this.mContext=mContext;
        this.patientModelArrayList=patientModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        PatientModel patientModel=patientModelArrayList.get(position);

        String name=patientModel.getName();
        String contact=patientModel.getPhone_no();


        holder.textName.setText(name);
        holder.textContact.setText(contact);
        holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PatientDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textName,textContact;
        private RelativeLayout relativeLayoutPatient;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.textName);
            textContact=itemView.findViewById(R.id.textContact);
            relativeLayoutPatient=itemView.findViewById(R.id.relativeLayoutPatient);
        }
    }
}
