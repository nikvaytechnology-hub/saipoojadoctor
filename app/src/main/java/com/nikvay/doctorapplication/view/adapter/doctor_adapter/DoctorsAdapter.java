package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.DoctorListModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.doctor_activity.ClassTimeSlotActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.SessionDetailsActivity;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.MyViewHolder>
{
    Context context;
    ArrayList<DoctorListModel>doctorListModelArrayList;
    ClassModel classModel;
    public DoctorsAdapter(Context context, ArrayList<DoctorListModel> doctorListModelArrayList)
    {
        this.context = context;
        this.doctorListModelArrayList = doctorListModelArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_doctors_layout,viewGroup,false);
        return new DoctorsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
    {
        final DoctorModel doctorModel;
        final DoctorListModel txtDoctorName=doctorListModelArrayList.get(i);
        myViewHolder.textView.setText(txtDoctorName.getName());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, ClassTimeSlotActivity.class);
                intent.putExtra("doctor_id",txtDoctorName.getDoctor_id());
                intent.putExtra("user_id",txtDoctorName.getUser_id());

               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView textView;
    CardView cardView;
    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        textView=itemView.findViewById(R.id.tv_doctorName);
        cardView=itemView.findViewById(R.id.card);
    }
}
}
