package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SelectDateTimeModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.PatientActivity;


import java.util.ArrayList;

public class SelectDateTimeAdapter extends RecyclerView.Adapter<SelectDateTimeAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList;
    private ServiceModel serviceModel;
    private String date;

    public SelectDateTimeAdapter(Context context, ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList, ServiceModel serviceModel, String date) {
        this.mContext=context;
        this.selectDateTimeModelArrayList=selectDateTimeModelArrayList;
        this.serviceModel=serviceModel;
        this.date=date;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_adapter,parent,false);
        return new SelectDateTimeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final SelectDateTimeModel selectDateTimeModel=selectDateTimeModelArrayList.get(position);

        if (selectDateTimeModelArrayList.get(position).isSelected()) {
            holder.linearLayoutTime.setBackgroundColor(mContext.getResources().getColor(R.color.app_color));
        } else {
            holder.linearLayoutTime.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.textTime.setText(selectDateTimeModel.getTime());
        holder.linearLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,PatientActivity.class);
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,StaticContent.IntentValue.APPOINTMENT);
                intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL,serviceModel);
                intent.putExtra(StaticContent.IntentKey.DATE,date);
                intent.putExtra(StaticContent.IntentKey.TIME,selectDateTimeModel.getTime());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return selectDateTimeModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private LinearLayout linearLayoutTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime=itemView.findViewById(R.id.textTime);
            linearLayoutTime=itemView.findViewById(R.id.linearLayoutTime);
        }
    }
}
