package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.interfaceutils.SelectPatientInterface;
import com.nikvay.saipoojadoctor.model.PatientModel;
import com.nikvay.saipoojadoctor.view.activity.doctor_activity.PaymentActivity;
import com.nikvay.saipoojadoctor.view.activity.doctor_activity.PrescriptionActivity;

import java.util.ArrayList;

public class MyPatientDialogAdapter extends RecyclerView.Adapter<MyPatientDialogAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<PatientModel> patientArrayList;
    private ArrayList<PatientModel> arrayListFiltered;
    private boolean isDialog;
    private boolean isFirstLoad;
    private PatientModel shareModel;
    private SelectPatientInterface selectPatientInterface;
    View view;
    private boolean isNameSelect = false;
    public MyPatientDialogAdapter(Context context, ArrayList<PatientModel> patientModelArrayList, boolean isDialog, SelectPatientInterface selectPatientInterface)
    {

        this.mContext = context;
        this.patientArrayList = patientModelArrayList;
        this.arrayListFiltered = patientModelArrayList;
        this.isFirstLoad = true;
        this.isDialog = isDialog;
        this.selectPatientInterface = selectPatientInterface;
        this.isNameSelect = false;
        if (isDialog) {
            this.shareModel = new PatientModel();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_patient, parent, false);
        return new MyPatientDialogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        PatientModel patientModel = patientArrayList.get(position);
        if (patientArrayList.get(position).isSelected()) {
            holder.relativeLayoutPatient.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.relativeLayoutPatient.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }

        String name = patientModel.getName();
        String contact = patientModel.getPhone_no();
        String email = patientModel.getEmail();

        holder.textName.setText(name);
        holder.textContact.setText(contact);
        holder.textEmail.setText(email);

        holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNameSelect) {
                    if (!patientArrayList.get(position).isSelected()) {
                        for (int i = 0; i < patientArrayList.size(); i++) {
                            patientArrayList.get(i).setSelected(false);
                        }
                        patientArrayList.get(position).setSelected(true);
                        shareModel = patientArrayList.get(position);
                        PaymentActivity.patientModel = shareModel;
                        PrescriptionActivity.patientModel = shareModel;
                    } else {
                        PaymentActivity.patientModel =  null;
                        patientArrayList.get(position).setSelected(false);
                        PrescriptionActivity.patientModel = shareModel;
                    }
                    notifyDataSetChanged();
                } else {
                    if (!patientArrayList.get(position).isSelected()) {
                        for (int i = 0; i < patientArrayList.size(); i++) {
                            patientArrayList.get(i).setSelected(false);
                        }
                        patientArrayList.get(position).setSelected(true);
                        selectPatientInterface.getPatientDetail(patientArrayList.get(position));
                    } else {
                        patientArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s", "").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    patientArrayList = arrayListFiltered;
                } else {
                    ArrayList<PatientModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < patientArrayList.size(); i++) {
                        String patientName = patientArrayList.get(i).getName().replaceAll("\\s", "").toLowerCase().trim();
                        if (patientName.contains(charString) )
                        {
                            filteredList.add(patientArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        patientArrayList = filteredList;
                    } else {
                        patientArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = patientArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                patientArrayList = (ArrayList<PatientModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textContact,textEmail;
        RelativeLayout relativeLayoutPatient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textContact = itemView.findViewById(R.id.textContact);
            textEmail = itemView.findViewById(R.id.textEmail);
            relativeLayoutPatient = itemView.findViewById(R.id.relativeLayoutPatient);
        }
    }
}
