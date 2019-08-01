package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SessionPatientExistModel;

import java.util.ArrayList;

public class AddAttendeeAdapter extends RecyclerView.Adapter
{
    Context mContext;
   ArrayList<SessionPatientExistModel>sessionPatientExistModelArrayList=new ArrayList<>();

    public AddAttendeeAdapter(Context mContext, ArrayList<SessionPatientExistModel> sessionPatientExistModelArrayList)
    {
        this.mContext = mContext;
        this.sessionPatientExistModelArrayList = sessionPatientExistModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_item_session_patient_adapter,viewGroup,false);

        return new AttendeeVievholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
       // SessionPatientExistModel sessionPatientExistModel=sessionPatientExistModelArrayList.get(i);
       // Toast.makeText(mContext, ""+sessionPatientExistModel.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AttendeeVievholder extends RecyclerView.ViewHolder
    {
        TextView attendee_name,mobile_no,email;

        public AttendeeVievholder(@NonNull View itemView)
        {
            super(itemView);
            attendee_name=itemView.findViewById(R.id.user);
            mobile_no=itemView.findViewById(R.id.mobile_no);
            email=itemView.findViewById(R.id.email);
        }
    }
}
