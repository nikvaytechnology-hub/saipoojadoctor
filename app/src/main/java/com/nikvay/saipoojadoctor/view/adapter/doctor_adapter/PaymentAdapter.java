package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.PaymentDetailModel;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> /*implements Filterable*/ {


    Context mContext;
    ArrayList<PaymentDetailModel> paymentDetailModelArrayList;
    ArrayList<PaymentDetailModel> arrayListFiltered;

    public PaymentAdapter(Context mContext, ArrayList<PaymentDetailModel> paymentDetailModelArrayList) {
        this.mContext = mContext;
        this.paymentDetailModelArrayList = paymentDetailModelArrayList;
        this.arrayListFiltered = paymentDetailModelArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_payment_adapter, parent, false);

        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final PaymentDetailModel paymentDetailModel = paymentDetailModelArrayList.get(position);

        holder.txtPatientName.setText(paymentDetailModel.getName());
        holder.txtServiceName.setText(paymentDetailModel.getS_name());
        holder.txtCost.setText(paymentDetailModel.getService_cost());
        holder.txtDate.setText(paymentDetailModel.getDate());

        holder.cardViewPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog selectPayment = new Dialog(mContext);
                selectPayment.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectPayment.setContentView(R.layout.dialog_payment_history);
                selectPayment.setCancelable(true);
                selectPayment.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView textPatientName = selectPayment.findViewById(R.id.textPatientName);
                TextView textServiceName = selectPayment.findViewById(R.id.textServiceName);
                TextView textServiceCost = selectPayment.findViewById(R.id.textServiceCost);
                TextView textHospitalCharges = selectPayment.findViewById(R.id.textHospitalCharges);
                TextView textTotalCharges = selectPayment.findViewById(R.id.textTotalCharges);
                TextView textDate = selectPayment.findViewById(R.id.textDate);
                TextView textDescription = selectPayment.findViewById(R.id.textDescription);

                textPatientName.setText(paymentDetailModel.getName());
                textServiceName.setText(paymentDetailModel.getS_name());
                textServiceCost.setText(paymentDetailModel.getService_cost());
                textHospitalCharges.setText(paymentDetailModel.getHospital_charges());
                textTotalCharges.setText(paymentDetailModel.getTotal_amount());
                textDate.setText(paymentDetailModel.getDate());
                textDescription.setText(paymentDetailModel.getDescription());


                selectPayment.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentDetailModelArrayList.size();
    }

    /*@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    paymentDetailModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<PaymentDetailModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < paymentDetailModelArrayList.size(); i++) {

                        String serviceName=paymentDetailModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(paymentDetailModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        paymentDetailModelArrayList = filteredList;
                    } else {
                        paymentDetailModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = paymentDetailModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                paymentDetailModelArrayList = (ArrayList<PaymentDetailModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }
*/
    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_paymentDetail;
        TextView txtPatientName, txtServiceName, txtCost, txtDate, txtResponse;
        CardView cardViewPaymentHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_paymentDetail = itemView.findViewById(R.id.ll_paymentDetail);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtCost = itemView.findViewById(R.id.txtCost);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtResponse = itemView.findViewById(R.id.txtResponse);
            cardViewPaymentHistory = itemView.findViewById(R.id.cardViewPaymentHistory);

        }
    }
}
