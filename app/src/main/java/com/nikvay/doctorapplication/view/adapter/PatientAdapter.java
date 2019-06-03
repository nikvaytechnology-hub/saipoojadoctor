package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.NewAppointmentActivity;
import com.nikvay.doctorapplication.view.activity.PatientDetailsActivity;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<PatientModel> patientModelArrayList;
    private String appointmentName;
    private ServiceModel serviceModel;
    private String date,time;
    private ArrayList<PatientModel> arrayListFiltered;

    public PatientAdapter(Context mContext, ArrayList<PatientModel> patientModelArrayList, String appointmentName, ServiceModel serviceModel,String date,String time) {
        this.mContext = mContext;
        this.patientModelArrayList = patientModelArrayList;
        this.arrayListFiltered = patientModelArrayList;
        this.appointmentName = appointmentName;
        this.serviceModel=serviceModel;
        this.date=date;
        this.time=time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        PatientModel patientModel = patientModelArrayList.get(position);

        String name = patientModel.getName();
        String contact = patientModel.getPhone_no();
        String email = patientModel.getEmail();


        holder.textName.setText(name);
        holder.textContact.setText(contact);
        holder.textEmail.setText(email);

        if (appointmentName.equalsIgnoreCase(StaticContent.IntentValue.APPOINTMENT)) {

            holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewAppointmentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModelArrayList.get(position));
                    intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL,serviceModel);
                    intent.putExtra(StaticContent.IntentKey.DATE,date);
                    intent.putExtra(StaticContent.IntentKey.TIME,time);
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
                    mContext.startActivity(intent);

                }
            });

        } else {
            holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PatientDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModelArrayList.get(position));
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
                    mContext.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    patientModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<PatientModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < patientModelArrayList.size(); i++) {

                        String patientName=patientModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (patientName.contains(charString)) {
                            filteredList.add(patientModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        patientModelArrayList = filteredList;
                    } else {
                        patientModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = patientModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                patientModelArrayList = (ArrayList<PatientModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textName, textContact,textEmail;
        private RelativeLayout relativeLayoutPatient;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textContact = itemView.findViewById(R.id.textContact);
            textEmail = itemView.findViewById(R.id.textEmail);
            relativeLayoutPatient = itemView.findViewById(R.id.relativeLayoutPatient);
        }
    }
}
