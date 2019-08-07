package com.nikvay.saipoojadoctor.view.adapter.admin_doctor_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.interfaceutils.SelectDepartmentInterface;
import com.nikvay.saipoojadoctor.model.DepartmentModel;

import java.util.ArrayList;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.MyViewHolder> implements Filterable{

    private ArrayList<DepartmentModel> departmentModelArrayList;
    private ArrayList<DepartmentModel> arrayListFiltered;
    private Context mContext;
    private boolean isDialog;
    private SelectDepartmentInterface selectDepartmentInterface;

    public DepartmentListAdapter(Context mContext, ArrayList<DepartmentModel> departmentModelArrayList, boolean isDialog) {
        this.mContext =mContext;
        this.departmentModelArrayList = departmentModelArrayList;
        this.arrayListFiltered = departmentModelArrayList;
        this.isDialog = isDialog;
    }

    public DepartmentListAdapter(Context mContext, ArrayList<DepartmentModel> departmentModelArrayList, boolean isDialog, SelectDepartmentInterface selectDepartmentInterface) {
        this.mContext =mContext;
        this.departmentModelArrayList = departmentModelArrayList;
        this.arrayListFiltered = departmentModelArrayList;
        this.isDialog = isDialog;
        this.selectDepartmentInterface=selectDepartmentInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supar_admin_department_list, parent, false);
        return new DepartmentListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DepartmentModel departmentModel = departmentModelArrayList.get(position);

        if (departmentModelArrayList.get(position).isSelected()) {
            holder.cardViewDepartment.setCardBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.cardViewDepartment.setCardBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }

        holder.txtDepartmentName.setText(departmentModel.getName());
        holder.txtDescription.setText(departmentModel.getDescription());


        if (isDialog) {

            holder.cardViewDepartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (!departmentModelArrayList.get(position).isSelected()) {
                            for (int i = 0; i < departmentModelArrayList.size(); i++) {
                                departmentModelArrayList.get(i).setSelected(false);
                            }
                            departmentModelArrayList.get(position).setSelected(true);
                            selectDepartmentInterface.getDepartment(departmentModelArrayList.get(position));

                        } else {
                            selectDepartmentInterface.getDepartment(null);
                            departmentModelArrayList.get(position).setSelected(false);
                        }
                        notifyDataSetChanged();
                    }

            });
        }




    }

    @Override
    public int getItemCount() {
        return departmentModelArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    departmentModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<DepartmentModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < departmentModelArrayList.size(); i++) {

                        String departmentName=departmentModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (departmentName.contains(charString)) {
                            filteredList.add(departmentModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        departmentModelArrayList = filteredList;
                    } else {
                        departmentModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = departmentModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                departmentModelArrayList = (ArrayList<DepartmentModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDepartmentName,txtDescription;
        CardView cardViewDepartment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDepartmentName = itemView.findViewById(R.id.txtDepartmentName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            cardViewDepartment = itemView.findViewById(R.id.cardViewDepartment);
        }
    }
}
