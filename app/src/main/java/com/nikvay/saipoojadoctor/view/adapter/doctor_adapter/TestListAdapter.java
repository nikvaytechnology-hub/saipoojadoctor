package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.TestListModel;

import java.util.ArrayList;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TestListModel> testListModelArrayList;
    public TestListAdapter(Context mContext, ArrayList<TestListModel> testListModelArrayList) {
        this.mContext=mContext;
        this.testListModelArrayList=testListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medicine_list,parent,false);
        return new TestListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TestListModel testListModel=testListModelArrayList.get(position);
        // holder.textIndex.setText(testListModel.getIndex());
        holder.textMedicineName.setText(testListModel.getTestName());
        holder.textMedicineNote.setText(testListModel.getTestNote());

    }

    @Override
    public int getItemCount() {
        return testListModelArrayList.size();
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
