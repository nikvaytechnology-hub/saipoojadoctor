package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.nikvay.saipooja_doctor.model.ClassModel;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.ClassDetailsActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
    final ClassModel classModel=classModelArrayList.get(position);
    holder.textClassName.setText(classModel.getName());
    final String class_id=classModel.getClass_id();
    final String class_name=classModel.getName();
    final String date=classModel.getDate();
    final int count=classModel.getSession_count();
    final String dateString=classModel.getDate_string();
    String createdBy=classModel.getDoctor_name();
//    holder.txtcreateBy.setText("Created by"+" "+createdBy);
    // holder.textClassDate.setText(classModel.getDate());
    SharedPreferences sharedPreferences2=mContext.getSharedPreferences("login_status",MODE_PRIVATE);
    String status=sharedPreferences2.getString("login_status","");

    if (status.equals("doctor"))
    {
      holder.txtcreateBy.setVisibility(View.GONE);
    }
    holder.txtcreateBy.setText(createdBy);
    SharedPreferences sharedPreferences=mContext.getSharedPreferences("class_name", MODE_PRIVATE);
    final SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString("class_name",class_name);
    editor.putString("class_id",class_id);

    holder.relativeLayoutClass.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

        Intent intent = new Intent(mContext, ClassDetailsActivity.class);
        intent.putExtra("class_id",class_id);
        intent.putExtra("count",count);
        intent.putExtra("dateString",dateString);
        editor.putString("count", String.valueOf(count));
        editor.putString("date",date);
        editor.apply();
        editor.commit();
        intent.putExtra("class_name",class_name);
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

    private TextView textClassName,textClassDate,txtcreateBy;
    private RelativeLayout relativeLayoutClass;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      textClassName=itemView.findViewById(R.id.textClassName);
      //textClassDate=itemView.findViewById(R.id.textClassDate);
      relativeLayoutClass=itemView.findViewById(R.id.relativeLayoutClass);
      txtcreateBy=itemView.findViewById(R.id.txtcreatedBy);
    }
  }
}
