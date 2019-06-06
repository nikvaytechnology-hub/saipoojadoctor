package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.ClassDetailsActivity;
import com.nikvay.doctorapplication.view.activity.ServiceDetailsActivity;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    private ArrayList<ClassModel> classModelArrayList;
    private ArrayList<ClassModel> arrayListFiltered;

    public ClassAdapter(Context mContext, ArrayList<ClassModel> classModelArrayList) {

        this.mContext=mContext;
        this.classModelArrayList=classModelArrayList;
        this.arrayListFiltered=classModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_class_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ClassModel classModel=classModelArrayList.get(position);
        holder.textClassName.setText(classModel.getName());
        holder.textClassDate.setText(classModel.getDate());

        holder.relativeLayoutClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL, classModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return classModelArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    classModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<ClassModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < classModelArrayList.size(); i++) {

                        String serviceName=classModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(classModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        classModelArrayList = filteredList;
                    } else {
                        classModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = classModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                classModelArrayList = (ArrayList<ClassModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textClassName,textClassDate;
        private RelativeLayout relativeLayoutClass;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textClassName=itemView.findViewById(R.id.textClassName);
            textClassDate=itemView.findViewById(R.id.textClassDate);
            relativeLayoutClass=itemView.findViewById(R.id.relativeLayoutClass);
        }
    }
}
