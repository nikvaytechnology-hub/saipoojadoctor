package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

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

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.interfaceutils.SelectAllPatientInterface;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.SessionPatientAddedModel;

import java.util.ArrayList;

public class PatientMultipleSelecationAdapter  extends RecyclerView.Adapter<PatientMultipleSelecationAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<PatientModel> patientArrayList;
    private ArrayList<PatientModel> arrayListFiltered;
    private SelectAllPatientInterface selectAllPatientInterface;
    private ArrayList<SessionPatientAddedModel> sessionPatientAddedModelArrayList=new ArrayList<>();
ArrayList<String>arrayList=new ArrayList<>();
    View view;
    private   boolean isDialog;
    public PatientMultipleSelecationAdapter(Context context, ArrayList<PatientModel> patientModelArrayList,ArrayList<SessionPatientAddedModel> sessionPatientAddedModelArrayList, boolean isDialog, SelectAllPatientInterface selectAllPatientInterface)
    {
        this.mContext = context;
        this.patientArrayList = patientModelArrayList;
        this.arrayListFiltered = patientModelArrayList;
        this.selectAllPatientInterface = selectAllPatientInterface;
        this.isDialog= isDialog;
        this.sessionPatientAddedModelArrayList=sessionPatientAddedModelArrayList;
    }


    @NonNull
    @Override
    public PatientMultipleSelecationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_patient, parent, false);
        return new PatientMultipleSelecationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientMultipleSelecationAdapter.ViewHolder holder, final int position)
    {
        PatientModel patientModel = patientArrayList.get(position);
        if(isDialog)
        {
            if (patientArrayList.get(position).isSelected())
            {
                holder.relativeLayoutPatient.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
            } else {
                holder.relativeLayoutPatient.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
            }
        }

        String name = patientModel.getName();
        String contact = patientModel.getPhone_no();
        String email = patientModel.getEmail();

        holder.textName.setText(name);
        holder.textContact.setText(contact);
        holder.textEmail.setText(email);

        holder.relativeLayoutPatient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isDialog) {
                    if (!patientArrayList.get(position).isSelected())
                    {
                        patientArrayList.get(position).setSelected(true);
                       // selectAllPatientInterface.getPatientDetail(patientArrayList.get(position));
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
