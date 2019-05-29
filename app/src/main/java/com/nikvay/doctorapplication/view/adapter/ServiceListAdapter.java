package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.DateTimeSelectActivity;
import com.nikvay.doctorapplication.view.activity.ServiceDetailsActivity;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ServiceModel> serviceModelArrayList;
    private String appointmentName;


    public ServiceListAdapter(Context mContext, ArrayList<ServiceModel> serviceModelArrayList, String appointmentName) {
        this.mContext = mContext;
        this.serviceModelArrayList = serviceModelArrayList;
        this.appointmentName=appointmentName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ServiceModel serviceModel=serviceModelArrayList.get(position);

        holder.textServiceName.setText(serviceModel.getS_name());
        holder.textTime.setText(serviceModel.getService_time());
        holder.textCost.setText(serviceModel.getService_cost());


        if(appointmentName.equalsIgnoreCase(StaticContent.IntentValue.APPOINTMENT))
        {
            holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DateTimeSelectActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL, serviceModelArrayList.get(position));
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_SERVICE_DETAILS);
                    mContext.startActivity(intent);
                }

            });

        }
        else
        {
        holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL, serviceModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_SERVICE_DETAILS);
                mContext.startActivity(intent);
            }

        });

        }

    }

    @Override
    public int getItemCount() {
        return serviceModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textServiceName, textTime, textCost;
        private RelativeLayout relativeLayoutService;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textServiceName = itemView.findViewById(R.id.textServiceName);
            textTime = itemView.findViewById(R.id.textTime);
            textCost = itemView.findViewById(R.id.textCost);
            relativeLayoutService = itemView.findViewById(R.id.relativeLayoutService);

        }
    }
}
