package com.nikvay.saipooja_doctor.view.adapter.doctor_adapter;

import android.content.Context;
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
import com.nikvay.saipooja_doctor.interfaceutils.SelectPatientInterface;
import com.nikvay.saipooja_doctor.model.ServiceModel;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.PaymentActivity;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.PrescriptionActivity;

import java.util.ArrayList;

public class MyServiceDialogAdapter extends RecyclerView.Adapter<MyServiceDialogAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<ServiceModel> serviceArrayList;
    private ArrayList<ServiceModel> arrayListFiltered;
    private boolean isDialog;
    private boolean isFirstLoad;
    private ServiceModel shareModel;
    private SelectPatientInterface selectPatientInterface;
    View view;
    private boolean isNameSelect = false;
    public MyServiceDialogAdapter(Context context, ArrayList<ServiceModel> serviceModelArrayList, boolean isDialog,
                                  SelectPatientInterface selectPatientInterface)
    {
        this.mContext = context;
        this.serviceArrayList = serviceModelArrayList;
        this.arrayListFiltered = serviceModelArrayList;
        this.isFirstLoad = true;
        this.isDialog = isDialog;
        this.selectPatientInterface = selectPatientInterface;
        this.isNameSelect = false;
        if (isDialog) {
            this.shareModel = new ServiceModel();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service_adapter, parent, false);
        return new MyServiceDialogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        ServiceModel serviceModel = serviceArrayList.get(position);
        if (serviceArrayList.get(position).isSelected()) {
            holder.relativeLayoutService.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.relativeLayoutService.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }

        holder.textServiceName.setText(serviceModel.getS_name());
        holder.textTime.setText(serviceModel.getService_time());
        holder.textCost.setText(serviceModel.getService_cost());

        holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNameSelect) {
                    if (!serviceArrayList.get(position).isSelected()) {
                        for (int i = 0; i < serviceArrayList.size(); i++) {
                            serviceArrayList.get(i).setSelected(false);
                        }
                        serviceArrayList.get(position).setSelected(true);
                        shareModel = serviceArrayList.get(position);
                        PaymentActivity.serviceModel = shareModel;
                        PrescriptionActivity.serviceModel = shareModel;
                    } else {
                        PaymentActivity.serviceModel =  null;
                        PrescriptionActivity.serviceModel =  null;
                        serviceArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                } else {
                    if (!serviceArrayList.get(position).isSelected()) {
                        for (int i = 0; i < serviceArrayList.size(); i++) {
                            serviceArrayList.get(i).setSelected(false);
                        }
                        serviceArrayList.get(position).setSelected(true);
                        selectPatientInterface.getServiceDetail(serviceArrayList.get(position));
                    } else {
                        serviceArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s", "").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    serviceArrayList = arrayListFiltered;
                } else {
                    ArrayList<ServiceModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < serviceArrayList.size(); i++) {
                        String patientName = serviceArrayList.get(i).getS_name().replaceAll("\\s", "").toLowerCase().trim();
                        if (patientName.contains(charString) )
                        {
                            filteredList.add(serviceArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        serviceArrayList = filteredList;
                    } else {
                        serviceArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = serviceArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                serviceArrayList = (ArrayList<ServiceModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textServiceName, textTime, textCost;
        private RelativeLayout relativeLayoutService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textServiceName = itemView.findViewById(R.id.textServiceName);
            textTime = itemView.findViewById(R.id.textTime);
            textCost = itemView.findViewById(R.id.textCost);
            relativeLayoutService = itemView.findViewById(R.id.relativeLayoutService);

        }
    }
}
