package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.model.SessionListModel;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.SessionEditActivity;

import java.util.ArrayList;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<SessionListModel> sessionListModelArrayList;
    private ArrayList<SessionListModel> arrayListFiltered;
    SharedPreferences sharedPreferences;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final SessionListModel sessionListModel=sessionListModelArrayList.get(position);
        String date_time=sessionListModel.getDate()+"/"+sessionListModel.getTime();
        holder.textDate_time.setText(date_time);
        holder.textClassName.setText(sessionListModel.getName_class());
        SharedPreferences sharedPreferences;
        sharedPreferences=mContext.getSharedPreferences("className",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("className",sessionListModel.getName_class());
        editor.apply();
        editor.commit();
        holder.cardViewSessionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SessionEditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.SESSION__DETAIL, sessionListModelArrayList.get(position));
                mContext.startActivity(intent);
            }
        });
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

                        String date=sessionListModelArrayList.get(i).getDate().replaceAll("\\s","").toLowerCase().trim();
                        if (date.contains(charString)) {
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
        CardView cardViewSessionList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDate_time = itemView.findViewById(R.id.textDate_time);
            textClassName = itemView.findViewById(R.id.textClassName);
            cardViewSessionList = itemView.findViewById(R.id.cardViewSessionList);

        }
    }
}
