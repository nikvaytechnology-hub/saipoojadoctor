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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.interfaceutils.SelectDoctorInterface;
import com.nikvay.doctorapplication.model.DoctorListModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.DoctorPatientListActivity;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.DoctorServiceListActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    ArrayList<DoctorListModel> doctorListModelArrayList;
    ArrayList<DoctorListModel> arrayListFiltered;
    private boolean isDialog;
    private SelectDoctorInterface selectDoctorInterface;

    public DoctorListAdapter(Context mContext, ArrayList<DoctorListModel> doctorListModelArrayList, boolean isDialog) {
        this.doctorListModelArrayList = doctorListModelArrayList;
        this.mContext = mContext;
        this.arrayListFiltered = doctorListModelArrayList;
        this.isDialog = isDialog;
    }

    public DoctorListAdapter(Context mContext, ArrayList<DoctorListModel> doctorListModelArrayList, boolean isDialog, SelectDoctorInterface selectDoctorInterface) {
        this.doctorListModelArrayList = doctorListModelArrayList;
        this.mContext = mContext;
        this.arrayListFiltered = doctorListModelArrayList;
        this.isDialog = isDialog;
        this.selectDoctorInterface = selectDoctorInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctor_adapter, parent, false);
        return new DoctorListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DoctorListModel txtDoctorName = doctorListModelArrayList.get(position);


        if (doctorListModelArrayList.get(position).isSelected()) {
            holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }

        final String doctor_id;
        holder.txtDoctorName.setText(txtDoctorName.getName());
        holder.txtEmail.setText(txtDoctorName.getEmail());
        holder.txthospitalName.setText(txtDoctorName.getHospital_name());
        holder.txtPhoneNo.setText(txtDoctorName.getPhone_no());
        holder.txtProfile.setText(txtDoctorName.getProfile());
        holder.txtAddress.setText(txtDoctorName.getAddress());
        doctor_id = txtDoctorName.getDoctor_id();
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(txtDoctorName.getName(), ".");
            stringTokenizer.nextToken().trim();
            String first_name = stringTokenizer.nextToken().trim();
            holder.textFirstName.setText(first_name);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.textService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DoctorServiceListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL, doctor_id);
                mContext.startActivity(intent);

            }
        });

        holder.textAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Appointment", Toast.LENGTH_SHORT).show();
            }
        });

        holder.textPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DoctorPatientListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, doctor_id);
                mContext.startActivity(intent);
            }
        });

        if (isDialog) {
            holder.linearLayoutHideAction.setVisibility(View.GONE);
            holder.ll_doctorDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!doctorListModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < doctorListModelArrayList.size(); i++) {
                            doctorListModelArrayList.get(i).setSelected(false);
                        }
                        doctorListModelArrayList.get(position).setSelected(true);
                        selectDoctorInterface.getDoctor(doctorListModelArrayList.get(position));

                    } else {
                        doctorListModelArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();


                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return doctorListModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s", "").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    doctorListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<DoctorListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < doctorListModelArrayList.size(); i++) {

                        String serviceName = doctorListModelArrayList.get(i).getName().replaceAll("\\s", "").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(doctorListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        doctorListModelArrayList = filteredList;
                    } else {
                        doctorListModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = doctorListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorListModelArrayList = (ArrayList<DoctorListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName, txtEmail, txthospitalName, txtProfile, txtAddress, txtPhoneNo, textFirstName, textService, textAppointment, textPatient;
        LinearLayout ll_doctorDetail, linearLayoutHideAction;

        public MyViewHolder(@NonNull View view) {
            super(view);


            txtDoctorName = view.findViewById(R.id.txtDoctorName);
            txtEmail = view.findViewById(R.id.txtEmail);
            txthospitalName = view.findViewById(R.id.txthospitalName);
            txtProfile = view.findViewById(R.id.txtProfile);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
            textFirstName = view.findViewById(R.id.textFirstName);
            textService = view.findViewById(R.id.textService);
            textAppointment = view.findViewById(R.id.textAppointment);
            textPatient = view.findViewById(R.id.textPatient);
            ll_doctorDetail = view.findViewById(R.id.ll_doctorDetail);
            linearLayoutHideAction = view.findViewById(R.id.linearLayoutHideAction);
        }
    }
}
