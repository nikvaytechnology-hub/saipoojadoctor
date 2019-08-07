package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.EnquiryListModel;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.view.activity.doctor_activity.EnquiryReplyActivity;

import java.util.ArrayList;

public class EnquiryListAdapter extends RecyclerView.Adapter<EnquiryListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<EnquiryListModel> enquiryListModelArrayList;
    private ArrayList<EnquiryListModel> arrayListFiltered;

    public EnquiryListAdapter(Context mContext, ArrayList<EnquiryListModel> enquiryListModelArrayList) {
        this.mContext = mContext;
        this.enquiryListModelArrayList = enquiryListModelArrayList;
        this.arrayListFiltered=enquiryListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_enquiry_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final EnquiryListModel enquiryListModel = enquiryListModelArrayList.get(position);


        holder.textEnquiryId.setText("Enquiry ID:-"+enquiryListModel.getEnquiry_id());
        holder.textEnquiryDate.setText(enquiryListModel.getEnquiry_date());
        holder.textName.setText(enquiryListModel.getName());
        holder.textPhone_no.setText(enquiryListModel.getPhone_no());
        holder.textEmail.setText(enquiryListModel.getEmail());
        holder.textReply.setText(enquiryListModel.getReply());

        holder.linearLayoutEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EnquiryReplyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.ENQUIRY_REPLY, enquiryListModelArrayList.get(position));
                mContext.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return enquiryListModelArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    enquiryListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<EnquiryListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < enquiryListModelArrayList.size(); i++) {

                        String name=enquiryListModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (name.contains(charString)) {
                            filteredList.add(enquiryListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        enquiryListModelArrayList = filteredList;
                    } else {
                        enquiryListModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = enquiryListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                enquiryListModelArrayList = (ArrayList<EnquiryListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textEnquiryId, textEnquiryDate, textName, textPhone_no, textEmail,textReply;
        LinearLayout linearLayoutEnquiry;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textEnquiryId = itemView.findViewById(R.id.textEnquiryId);
            textEnquiryDate = itemView.findViewById(R.id.textEnquiryDate);
            textName = itemView.findViewById(R.id.textName);
            textPhone_no = itemView.findViewById(R.id.textPhone_no);
            textEmail = itemView.findViewById(R.id.textEmail);
            linearLayoutEnquiry = itemView.findViewById(R.id.linearLayoutEnquiry);
            textReply = itemView.findViewById(R.id.textReply);
        }
    }
}
