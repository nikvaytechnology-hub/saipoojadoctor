package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.PrescriptionDocumentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PrescriptionDocumentAdapter extends RecyclerView.Adapter<PrescriptionDocumentAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PrescriptionDocumentModel> prescriptionDocumentModelArrayList;
    private String document_url,imageUrl,baseURl;
    public PrescriptionDocumentAdapter(Context mContext, ArrayList<PrescriptionDocumentModel> prescriptionDocumentModelArrayList, String document_url) {
        this.mContext=mContext;
        this.prescriptionDocumentModelArrayList=prescriptionDocumentModelArrayList;
        this.document_url=document_url;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient_document_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PrescriptionDocumentModel prescriptionDocumentModel=prescriptionDocumentModelArrayList.get(position);

        holder.textTitle.setText(prescriptionDocumentModel.getTitle());
        imageUrl=prescriptionDocumentModel.getDocument_name();
        baseURl=document_url+imageUrl;
        Picasso.get().load(baseURl).into(holder.iv_document_image);

    }

    @Override
    public int getItemCount() {
        return prescriptionDocumentModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        ImageView iv_document_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle=itemView.findViewById(R.id.textTitle);
            iv_document_image=itemView.findViewById(R.id.iv_document_image);
        }
    }
}
