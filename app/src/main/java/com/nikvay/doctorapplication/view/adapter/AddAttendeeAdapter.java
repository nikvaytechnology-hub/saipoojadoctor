package com.nikvay.doctorapplication.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SessionPatientExistModel;
import com.nikvay.doctorapplication.utils.MultimpleSelectinterface;

import java.util.ArrayList;

public class AddAttendeeAdapter extends RecyclerView.Adapter<AddAttendeeAdapter.AttendeeVievholder>
{
    Context mContext;
    ArrayList<SessionPatientExistModel>patientArraylist=new ArrayList<>();
   ArrayList<SessionPatientExistModel>sessionPatientExistModelArrayList=new ArrayList<>();

    public AddAttendeeAdapter(Context mContext, ArrayList<SessionPatientExistModel> sessionPatientExistModelArrayList, MultimpleSelectinterface multimpleSelectinterface)
    {
        this.mContext = mContext;
        this.sessionPatientExistModelArrayList = sessionPatientExistModelArrayList;
    }

    @NonNull
    @Override
    public AttendeeVievholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_item_session_patient_adapter,viewGroup,false);

        return new AttendeeVievholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendeeVievholder attendeeVievholder, final int i)
    {
        final SessionPatientExistModel sessionPatientExistModel=sessionPatientExistModelArrayList.get(i);
        Toast.makeText(mContext, ""+sessionPatientExistModel.getName(), Toast.LENGTH_SHORT).show();
        if (sessionPatientExistModel.getStatus().equals("0"))
        {
            attendeeVievholder.relativeLayout.setBackgroundColor(Color.RED);
        }
        else
        {
        }
        attendeeVievholder.attendee_name.setText(sessionPatientExistModel.getName());
        attendeeVievholder.email.setText(sessionPatientExistModel.getEmail());
        attendeeVievholder.mobile_no.setText(sessionPatientExistModel.getPhone_no());
        attendeeVievholder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            String status=sessionPatientExistModel.getStatus();
               if (status.equals("1"))
               {
                   attendeeVievholder.relativeLayout.setBackgroundColor(Color.GREEN);
                   Toast.makeText(mContext, ""+sessionPatientExistModel.getStatus(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionPatientExistModelArrayList.size();
    }


    class AttendeeVievholder extends RecyclerView.ViewHolder
    {
        TextView attendee_name,mobile_no,email;
        RelativeLayout relativeLayout;

        public AttendeeVievholder(@NonNull View itemView)
        {
            super(itemView);
            attendee_name=itemView.findViewById(R.id.textPatientName);
            mobile_no=itemView.findViewById(R.id.mobile_no);
            email=itemView.findViewById(R.id.email);

            relativeLayout=itemView.findViewById(R.id.rl_layout);
        }
    }
}
