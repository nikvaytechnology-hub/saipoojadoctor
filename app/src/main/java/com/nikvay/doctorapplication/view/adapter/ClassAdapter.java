package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<ClassModel> classModelArrayList;

    public ClassAdapter(Context mContext, ArrayList<ClassModel> classModelArrayList) {

        this.mContext=mContext;
        this.classModelArrayList=classModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_class_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClassModel classModel=classModelArrayList.get(position);

        holder.textClassName.setText(classModel.getClass_name());

    }

    @Override
    public int getItemCount() {
        return classModelArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textClassName;
        private RelativeLayout relativeLayoutClass;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textClassName=itemView.findViewById(R.id.textClassName);
            relativeLayoutClass=itemView.findViewById(R.id.relativeLayoutClass);
        }
    }
}
