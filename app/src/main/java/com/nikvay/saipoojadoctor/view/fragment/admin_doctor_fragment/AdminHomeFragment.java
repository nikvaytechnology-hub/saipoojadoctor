package com.nikvay.saipoojadoctor.view.fragment.admin_doctor_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.AddAdminAppointmentActivity;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.AdminClassListActivity;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.AdminMainActivity;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.AdminServiceListActivity;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.AllPatientListActivity;
import com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity.DoctorListActivity;

import java.util.ArrayList;


public class AdminHomeFragment extends Fragment {


    private Context mContext;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String  hospital_name;
    private TextView textHospitalName;
    private CardView cardViewDoctorList,cardViewDepartment,cardViewPatient,cardViewAppointment,
            cardViewService,cardViewClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();

        return view;

    }

    private void find_All_IDs(View view) {
        textHospitalName=view.findViewById(R.id.textHospitalName);
        cardViewDoctorList=view.findViewById(R.id.cardViewDoctorList);
        cardViewDepartment=view.findViewById(R.id.cardViewDepartment);
        cardViewPatient=view.findViewById(R.id.cardViewPatient);
        cardViewAppointment=view.findViewById(R.id.cardViewAppointment);
        cardViewService=view.findViewById(R.id.cardViewService);
        cardViewClass=view.findViewById(R.id.cardViewClass);


        doctorModelArrayList = SharedUtils.getUserDetails(mContext);

        hospital_name = doctorModelArrayList.get(0).getTitle();
        textHospitalName.setText(hospital_name);

    }

    private void events() {

        cardViewDoctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DoctorListActivity.class);
                startActivity(intent);
            }
        });

        cardViewDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminMainActivity) mContext).loadAdminFragment(new DepartmentFragment());
            }
        });

        cardViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AllPatientListActivity.class);
                startActivity(intent);
            }
        });

        cardViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AddAdminAppointmentActivity.class);
                startActivity(intent);
            }
        });

        cardViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AdminServiceListActivity.class);
                startActivity(intent);
            }
        });

        cardViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AdminClassListActivity.class);
                startActivity(intent);
            }
        });

    }

}
