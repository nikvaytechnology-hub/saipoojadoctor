package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.PatientAppointmentHistoryModel;

import java.util.ArrayList;

public class PatientAppointmentHistoryAdapter extends RecyclerView.Adapter<PatientAppointmentHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PatientAppointmentHistoryModel> patientAppointmentHistoryModelArrayList;

    public PatientAppointmentHistoryAdapter(Context mContext, ArrayList<PatientAppointmentHistoryModel> patientAppointmentHistoryModelArrayList) {
        this.mContext=mContext;
        this.patientAppointmentHistoryModelArrayList=patientAppointmentHistoryModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment_history_adpter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final PatientAppointmentHistoryModel patientAppointmentHistoryModel=patientAppointmentHistoryModelArrayList.get(position);
        final String label=patientAppointmentHistoryModel.getLabel();
        holder.txtPatientName.setText(patientAppointmentHistoryModel.getName());
        holder.txtServiceName.setText(patientAppointmentHistoryModel.getService_name());
        holder.txtCost.setText(patientAppointmentHistoryModel.getService_cost());
        holder.txtDate.setText(patientAppointmentHistoryModel.getDate());

        if(label.equalsIgnoreCase("0")) {
            holder.textLabel.setText("Pending");
            holder.textLabel.setTextColor(mContext.getResources().getColor(R.color.pending));
        }

        if(label.equalsIgnoreCase("1")) {
            holder.textLabel.setText("Confirm");
            holder.textLabel.setTextColor(mContext.getResources().getColor(R.color.confirm));
        }

        if(label.equalsIgnoreCase("2")) {
            holder.textLabel.setText("Cancel");
            holder.textLabel.setTextColor(mContext.getResources().getColor(R.color.cancel));
        }

        if(label.equalsIgnoreCase("3")) {
            holder.textLabel.setText("Completed");
            holder.textLabel.setTextColor(mContext.getResources().getColor(R.color.complete));
        }

        holder.cardViewAppointmentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog selectAppointment = new Dialog(mContext);
                selectAppointment.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectAppointment.setContentView(R.layout.dialog_appointment_history);
                selectAppointment.setCancelable(true);
                selectAppointment.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView textPatientName = selectAppointment.findViewById(R.id.textPatientName);
                TextView textEmail = selectAppointment.findViewById(R.id.textEmail);
                TextView textAddress = selectAppointment.findViewById(R.id.textAddress);
                TextView textPhone = selectAppointment.findViewById(R.id.textPhone);
                TextView textServiceName = selectAppointment.findViewById(R.id.textServiceName);
                TextView textServiceCost = selectAppointment.findViewById(R.id.textServiceCost);

                TextView textStatus = selectAppointment.findViewById(R.id.textStatus);
                TextView textTime = selectAppointment.findViewById(R.id.textTime);
                TextView textDate = selectAppointment.findViewById(R.id.textDate);
                TextView textComment = selectAppointment.findViewById(R.id.textComment);

                textPatientName.setText(patientAppointmentHistoryModel.getName());
                textEmail.setText(patientAppointmentHistoryModel.getEmail());
                textAddress.setText(patientAppointmentHistoryModel.getAddress());
                textPhone.setText(patientAppointmentHistoryModel.getPhone_no());
                textServiceName.setText(patientAppointmentHistoryModel.getService_name());
                textServiceCost.setText(patientAppointmentHistoryModel.getService_cost());

                if(label.equalsIgnoreCase("0")) {
                    textStatus.setText("Pending");
                    textStatus.setTextColor(mContext.getResources().getColor(R.color.pending));
                }

                if(label.equalsIgnoreCase("1")) {
                    textStatus.setText("Confirm");
                    textStatus.setTextColor(mContext.getResources().getColor(R.color.confirm));
                }

                if(label.equalsIgnoreCase("2")) {
                    textStatus.setText("Cancel");
                    textStatus.setTextColor(mContext.getResources().getColor(R.color.cancel));
                }

                if(label.equalsIgnoreCase("3")) {
                    textStatus.setText("Completed");
                    textStatus.setTextColor(mContext.getResources().getColor(R.color.complete));
                }


                textTime.setText(patientAppointmentHistoryModel.getTime());
                textDate.setText(patientAppointmentHistoryModel.getDate());
                textComment.setText(patientAppointmentHistoryModel.getComment());


                selectAppointment.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientAppointmentHistoryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtPatientName,txtServiceName,textLabel,txtCost,txtDate;
        CardView cardViewAppointmentHistory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName=itemView.findViewById(R.id.txtPatientName);
            txtServiceName=itemView.findViewById(R.id.txtServiceName);
            textLabel=itemView.findViewById(R.id.textLabel);
            txtCost=itemView.findViewById(R.id.txtCost);
            txtDate=itemView.findViewById(R.id.txtDate);
            cardViewAppointmentHistory=itemView.findViewById(R.id.cardViewAppointmentHistory);

        }
    }
}
