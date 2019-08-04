package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.model.AppointmentListModel;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.AppointmentDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<AppointmentListModel> appoinmentListModelArrayList;
    private ArrayList<AppointmentListModel> arrayListFiltered;
    public AppointmentListAdapter(Context mContext, ArrayList<AppointmentListModel> appoinmentListModelArrayList) {
        this.mContext = mContext;
        this.appoinmentListModelArrayList = appoinmentListModelArrayList;
        this.arrayListFiltered = appoinmentListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        AppointmentListModel appoinmentListModel= appoinmentListModelArrayList.get(position);
        String date=appoinmentListModel.getDate(),dayOfTheWeek = null;
       
        holder.textDay.setText(appoinmentListModel.getDate());
        holder.textTime.setText(String.valueOf(appoinmentListModel.getTime()));
        holder.textPatientName.setText(String.valueOf(appoinmentListModel.getName()));
        holder.textService.setText(String.valueOf(appoinmentListModel.getS_name()));
        holder.textServiceCost.setText(String.valueOf(appoinmentListModel.getService_cost()));
        holder.textAppointmentId.setText(String.valueOf(appoinmentListModel.getAppointment_id()));

        
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
           dayOfTheWeek = (String) DateFormat.format("EEEE", date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textDateDay.setText(dayOfTheWeek);

        //holder.textServiceTime.setText(String.valueOf("time"+" "+appoinmentListModel.getService_time()));

        holder.rl_Appointment_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointmentDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT, appoinmentListModelArrayList.get(position));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return appoinmentListModelArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    appoinmentListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<AppointmentListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < appoinmentListModelArrayList.size(); i++) {

                        String serviceName=appoinmentListModelArrayList.get(i).getDate().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(appoinmentListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        appoinmentListModelArrayList = filteredList;
                    } else {
                        appoinmentListModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = appoinmentListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                appoinmentListModelArrayList = (ArrayList<AppointmentListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textDay, textTime, textPatientName, textServiceTime, textService, textServiceCost,textAppointmentId,textDateDay;
        private RelativeLayout rl_Appointment_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDay = itemView.findViewById(R.id.textDay);
            textTime = itemView.findViewById(R.id.textTime);
            textPatientName = itemView.findViewById(R.id.textPatientName);
           // textServiceTime = itemView.findViewById(R.id.textServiceTime);
            textService = itemView.findViewById(R.id.textService);
            textServiceCost = itemView.findViewById(R.id.textServiceCost);
            rl_Appointment_list = itemView.findViewById(R.id.rl_Appointment_list);
            textAppointmentId = itemView.findViewById(R.id.textAppointmentId);
            textDateDay = itemView.findViewById(R.id.textDateDay);
        }
    }
}
