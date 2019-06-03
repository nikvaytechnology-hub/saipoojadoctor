package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.view.activity.AppointmentListActivity;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<AppoinmentListModel> appoinmentListModelArrayList;
    private ArrayList<AppoinmentListModel> arrayListFiltered;
    public AppointmentListAdapter(Context mContext, ArrayList<AppoinmentListModel> appoinmentListModelArrayList) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        AppoinmentListModel appoinmentListModel= appoinmentListModelArrayList.get(position);


        holder.textDay.setText(appoinmentListModel.getDate());
        holder.textTime.setText(String.valueOf(appoinmentListModel.getTime()));
        holder.textPatientName.setText(String.valueOf(appoinmentListModel.getName()));
        holder.textService.setText(String.valueOf(appoinmentListModel.getS_name()));
        holder.textServiceCost.setText(String.valueOf(appoinmentListModel.getService_cost())+" "+"RS");
        holder.textServiceTime.setText(String.valueOf(appoinmentListModel.getService_time()));


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
                    ArrayList<AppoinmentListModel> filteredList = new ArrayList<>();
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
                appoinmentListModelArrayList = (ArrayList<AppoinmentListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textDay, textTime, textPatientName, textServiceTime, textService, textServiceCost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDay = itemView.findViewById(R.id.textDay);
            textTime = itemView.findViewById(R.id.textTime);
            textPatientName = itemView.findViewById(R.id.textPatientName);
            textServiceTime = itemView.findViewById(R.id.textServiceTime);
            textService = itemView.findViewById(R.id.textService);
            textServiceCost = itemView.findViewById(R.id.textServiceCost);
        }
    }
}
