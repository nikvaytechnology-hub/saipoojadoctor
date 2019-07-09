package com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.interfaceutils.SelectAllPatientInterface;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.doctor_activity.AppointmentHistoryActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.PatientDocumentListActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.PaymentHistoryActivity;

import java.util.ArrayList;

public class AllPatientListAdapter extends RecyclerView.Adapter<AllPatientListAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<PatientModel> patientModelArrayList;
    private ArrayList<PatientModel> arrayListFiltered;
    private boolean isDialog;
    private  SelectAllPatientInterface selectAllPatientInterface;

    public AllPatientListAdapter(Context mContext, ArrayList<PatientModel> patientModelArrayList) {
        this.mContext = mContext;
        this.patientModelArrayList = patientModelArrayList;
        this.arrayListFiltered = patientModelArrayList;

    }


    public AllPatientListAdapter(Context mContext, ArrayList<PatientModel> patientModelArrayList, boolean isDialog, SelectAllPatientInterface selectAllPatientInterface) {
        this.mContext = mContext;
        this.patientModelArrayList = patientModelArrayList;
        this.arrayListFiltered = patientModelArrayList;
        this.isDialog = isDialog;
        this.selectAllPatientInterface=selectAllPatientInterface;
    }


    @NonNull
    @Override
    public AllPatientListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient_adapter, parent, false);
        return new AllPatientListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPatientListAdapter.MyViewHolder holder, final int position) {
        PatientModel patientModel = patientModelArrayList.get(position);

        if (patientModelArrayList.get(position).isSelected()) {
            holder.linearLayoutPatient.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.linearLayoutPatient.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }


        final String doctor_id = patientModel.getPatient_id();
        String name = patientModel.getName();
        String contact = patientModel.getPhone_no();
        String email = patientModel.getEmail();

        holder.textName.setText(name);
        holder.textContact.setText(contact);
        holder.textEmail.setText(email);
        holder.textFirstName.setText(name);


       /* holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatientDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
                mContext.startActivity(intent);
            }
        });*/


        if (isDialog) {
            holder.linearLayoutActionHide.setVisibility(View.GONE);
            holder.linearLayoutPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!patientModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < patientModelArrayList.size(); i++) {
                            patientModelArrayList.get(i).setSelected(false);
                        }
                        patientModelArrayList.get(position).setSelected(true);
                        selectAllPatientInterface.getPatientDetail(patientModelArrayList.get(position));

                    } else {
                        patientModelArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                }

            });
        }




        holder.textPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PaymentHistoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, doctor_id);
                mContext.startActivity(intent);
            }
        });

        holder.textDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatientDocumentListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, doctor_id);
                mContext.startActivity(intent);
            }
        });
        holder.textAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointmentHistoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, doctor_id);
                mContext.startActivity(intent);
            }
        });
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
                String charString = charSequence.toString().replaceAll("\\s", "").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    patientModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<PatientModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < patientModelArrayList.size(); i++) {

                        String patientName = patientModelArrayList.get(i).getName().replaceAll("\\s", "").toLowerCase().trim();
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

        private TextView textName, textContact, textEmail, textPayment, textDocument, textAppointment, textFirstName;
        private RelativeLayout relativeLayoutPatient;
        private ImageView iv_contact, iv_message, iv_mail;
        LinearLayout linearLayoutPatient,linearLayoutActionHide;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textContact = itemView.findViewById(R.id.textContact);
            textEmail = itemView.findViewById(R.id.textEmail);
            relativeLayoutPatient = itemView.findViewById(R.id.relativeLayoutPatient);
            iv_contact = itemView.findViewById(R.id.iv_contact);
            iv_message = itemView.findViewById(R.id.iv_message);
            iv_mail = itemView.findViewById(R.id.iv_mail);

            textPayment = itemView.findViewById(R.id.textPayment);
            textDocument = itemView.findViewById(R.id.textDocument);
            textAppointment = itemView.findViewById(R.id.textAppointment);
            textFirstName = itemView.findViewById(R.id.textFirstName);
            linearLayoutPatient = itemView.findViewById(R.id.linearLayoutPatient);
            linearLayoutActionHide = itemView.findViewById(R.id.linearLayoutActionHide);
        }
    }

}
