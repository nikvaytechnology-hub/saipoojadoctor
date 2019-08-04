package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.model.MedicineListModel;

import java.util.ArrayList;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<MedicineListModel> medicineListModelArrayList;


    public MedicineListAdapter(Context mContext, ArrayList<MedicineListModel> medicineListModelArrayList) {
        this.mContext=mContext;
        this.medicineListModelArrayList = medicineListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medicine_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MedicineListModel medicineListModel=medicineListModelArrayList.get(position);
       // holder.textIndex.setText(medicineListModel.getIndex());
        holder.textMedicineName.setText(medicineListModel.getMedicineName());
        holder.textMedicineNote.setText(medicineListModel.getMedicineTest());

    }

    @Override
    public int getItemCount() {
        return medicineListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textMedicineName,textMedicineNote,textIndex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textMedicineName=itemView.findViewById(R.id.textMedicineName);
            textMedicineNote=itemView.findViewById(R.id.textMedicineNote);
            //textIndex=itemView.findViewById(R.id.textIndex);
        }
    }
}
