package com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.interfaceutils.SelectDoctorInterface;
import com.nikvay.saipooja_doctor.model.DoctorListModel;

import java.util.ArrayList;

public class MyDoctorDialogAdapter extends RecyclerView.Adapter<MyDoctorDialogAdapter.MyViewHolder> {

     Context mContext;
     ArrayList<DoctorListModel> doctorListModelArrayList;
     boolean isDialog;
     private SelectDoctorInterface selectDoctorInterface;
     View view;

    public MyDoctorDialogAdapter(Context context, ArrayList<DoctorListModel> doctorListModelArrayList, boolean isDialog, SelectDoctorInterface selectDoctorInterface)
    {
        this.doctorListModelArrayList = doctorListModelArrayList;
        this.mContext = context;
        this.selectDoctorInterface =selectDoctorInterface;
        this.isDialog = isDialog;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_doctor_adapter,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        DoctorListModel txtDoctorName = doctorListModelArrayList.get(position);
        if (isDialog) {
            if (doctorListModelArrayList.get(position).isSelected()) {
                holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
            } else {
                holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
            }
        }
        holder.txtDoctorName.setText(txtDoctorName.getName());
        holder.txtEmail.setText(txtDoctorName.getEmail());
     //   holder.txthospitalName.setText(txtDoctorName.getHospital_name());
      //  holder.txtPhoneNo.setText(txtDoctorName.getPhone_no());
        holder.txtProfile.setText(txtDoctorName.getProfile());
        holder.txtAddress.setText(txtDoctorName.getAddress());


        if (isDialog) {

        holder.ll_doctorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!doctorListModelArrayList.get(position).isSelected()) {
                    doctorListModelArrayList.get(position).setSelected(true);
                    selectDoctorInterface.getDoctor(doctorListModelArrayList.get(position));
                } else {
                    doctorListModelArrayList.get(position).setSelected(false);
                }
                notifyDataSetChanged();




        /*        if (!isNameSelect) {
                    if (!doctorListModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < doctorListModelArrayList.size(); i++) {
                            doctorListModelArrayList.get(i).setSelected(false);
                        }
                        doctorListModelArrayList.get(position).setSelected(true);
                        shareModel = doctorListModelArrayList.get(position);
                        SuperAdminAddServiceActivity.doctorListModel = shareModel;
                    } else {
                        SuperAdminAddServiceActivity.doctorListModel =  null;
                        doctorListModelArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                } else {
                    if (!doctorListModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < doctorListModelArrayList.size(); i++) {
                            doctorListModelArrayList.get(i).setSelected(false);
                        }
                        doctorListModelArrayList.get(position).setSelected(true);
                        selectDoctorInterface.selecteddoctorName(doctorListModelArrayList.get(position));
                    } else {
                        doctorListModelArrayList.get(position).setSelected(false);
                    }*/
                   // notifyDataSetChanged();

            }
        });
        }


    }

    @Override
    public int getItemCount() {
        return doctorListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName,txtEmail,txthospitalName,txtProfile,txtAddress,txtPhoneNo;
        LinearLayout ll_doctorDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txthospitalName = itemView.findViewById(R.id.txthospitalName);
            txtProfile = itemView.findViewById(R.id.txtProfile);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhoneNo = itemView.findViewById(R.id.txtPhoneNo);
            ll_doctorDetail = itemView.findViewById(R.id.ll_doctorDetail);

        }
    }
}
