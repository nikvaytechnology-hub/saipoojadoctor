package com.nikvay.saipooja_doctor.view.fragment.doctor_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.ChangePasswordActivity;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.ClassActivity;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.ServiceListActivity;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.SettingActivity;

public class SettingFragment extends Fragment {

    Context mContext;
    private RelativeLayout relativeLayoutService,relativeLayoutChangePassword,
            relativeLayoutSetting,relativeLayoutClass,relativeLayoutClassAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();

        return view;

    }

    private void find_All_IDs(View view) {
        relativeLayoutService=view.findViewById(R.id.relativeLayoutService);
        relativeLayoutChangePassword=view.findViewById(R.id.relativeLayoutChangePassword);
        relativeLayoutSetting=view.findViewById(R.id.relativeLayoutSetting);
        relativeLayoutClass=view.findViewById(R.id.relativeLayoutClass);
      //  relativeLayoutClassAppointment=view.findViewById(R.id.relativeLayoutClassAppointment);
    }


    private void events() {
        relativeLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ServiceListActivity.class);
                startActivity(intent);
            }
        });
        relativeLayoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });
        relativeLayoutChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        relativeLayoutClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mContext, ClassActivity.class);
               startActivity(intent);
            }
        });
       /* relativeLayoutClassAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ClassAppointmentListActivity.class);
                startActivity(intent);
            }
        });*/



    }


}
