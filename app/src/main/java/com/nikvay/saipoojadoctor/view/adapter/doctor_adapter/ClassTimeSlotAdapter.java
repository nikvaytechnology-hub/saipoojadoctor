package com.nikvay.saipoojadoctor.view.adapter.doctor_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.ClassModel;
import com.nikvay.saipoojadoctor.model.SelectDateTimeModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.view.activity.doctor_activity.CreateSessionActivity;

import java.util.ArrayList;

public class ClassTimeSlotAdapter extends RecyclerView.Adapter<ClassTimeSlotAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList;
    private String date;
    private ErrorMessageDialog errorMessageDialog;
    private ClassModel classModel;


    public ClassTimeSlotAdapter(Context context, ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList, String date, ClassModel classModel) {
        this.mContext = context;
        this.selectDateTimeModelArrayList = selectDateTimeModelArrayList;
        this.date = date;
        errorMessageDialog = new ErrorMessageDialog(mContext);
        this.classModel=classModel;
    }


    @NonNull
    @Override
    public ClassTimeSlotAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_adapter, parent, false);
        return new ClassTimeSlotAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassTimeSlotAdapter.MyViewHolder holder, final int position) {
        final SelectDateTimeModel selectDateTimeModel = selectDateTimeModelArrayList.get(position);
        
        holder.textTime.setText(selectDateTimeModel.getTime());
        String status=selectDateTimeModel.getStatus()==null?"":selectDateTimeModel.getStatus();

        if (status.equalsIgnoreCase("1")) {
            holder.cardViewTime.setBackgroundColor(mContext.getResources().getColor(R.color.app_color));
        } else {
            holder.cardViewTime.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }


        holder.cardViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (selectDateTimeModel.getStatus().equals("1")) {
                    errorMessageDialog.showDialog("This Slot Is Already Booked");
                } else {
                    Intent intent=new Intent(mContext, CreateSessionActivity.class);
                    intent.putExtra("date",date);
                    intent.putExtra("time",selectDateTimeModel.getTime());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                    intent.putExtra(StaticContent.IntentKey.DATE, date);
                    intent.putExtra(StaticContent.IntentKey.TIME, selectDateTimeModel.getTime());
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                    mContext.startActivity(intent);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return selectDateTimeModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private CardView cardViewTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.textTime);
            cardViewTime = itemView.findViewById(R.id.cardViewTime);
        }
    }

}
