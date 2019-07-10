package com.nikvay.doctorapplication.view.adapter.doctor_adapter;

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
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SessionListModel;
import com.nikvay.doctorapplication.view.activity.doctor_activity.SessionListActivity;

import java.util.ArrayList;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<SessionListModel> sessionListModelArrayList;
    private ArrayList<SessionListModel> arrayListFiltered;

    public SessionListAdapter(Context mContext, ArrayList<SessionListModel> sessionListModelArrayList) {
        this.mContext = mContext;
        this.sessionListModelArrayList = sessionListModelArrayList;
        this.arrayListFiltered = sessionListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_session_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SessionListModel sessionListModel=sessionListModelArrayList.get(position);
        String date_time=sessionListModel.getDate()+"/"+sessionListModel.getTime();
        holder.textDate_time.setText(date_time);
        holder.textClassName.setText(sessionListModel.getClass_name());
    }

    @Override
    public int getItemCount() {
        return sessionListModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    sessionListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<SessionListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < sessionListModelArrayList.size(); i++) {

                        String serviceName=sessionListModelArrayList.get(i).getClass_name().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(sessionListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        sessionListModelArrayList = filteredList;
                    } else {
                        sessionListModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sessionListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sessionListModelArrayList = (ArrayList<SessionListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textDate_time, textClassName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDate_time = itemView.findViewById(R.id.textDate_time);
            textClassName = itemView.findViewById(R.id.textClassName);
        }
    }
}
