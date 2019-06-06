package com.nikvay.doctorapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.view.activity.AppointmentListActivity;
import com.nikvay.doctorapplication.view.activity.PatientActivity;
import com.nikvay.doctorapplication.view.activity.ServiceListActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private Context mContext;
    private CardView cardViewPrescription, cardViewPayment, cardViewService, cardViewMyPatient, cardViewAppointment, cardViewMyProfile;
    private TextView textDoctorName;
    private String doctorName;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();

        return view;
    }

    private void find_All_IDs(View view) {
        cardViewPrescription = view.findViewById(R.id.cardViewPrescription);
        cardViewPayment = view.findViewById(R.id.cardViewPayment);
        cardViewService = view.findViewById(R.id.cardViewService);
        cardViewMyPatient = view.findViewById(R.id.cardViewMyPatient);
        cardViewAppointment = view.findViewById(R.id.cardViewAppointment);
        cardViewMyProfile = view.findViewById(R.id.cardViewMyProfile);
        textDoctorName = view.findViewById(R.id.textDoctorName);


        doctorModelArrayList = SharedUtils.getUserDetails(mContext);
        doctorName = doctorModelArrayList.get(0).getName();
        textDoctorName.setText("Hello"+" "+doctorName);


    }


    private void events() {
        cardViewMyPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatientActivity.class);
                startActivity(intent);
            }
        });

        cardViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new AppointmentFragment());
            }
        });

        cardViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceListActivity.class);
                startActivity(intent);
            }
        });
        cardViewMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new ProfileFragment());
            }
        });
        cardViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Under Development", Toast.LENGTH_SHORT).show();
            }
        });
        cardViewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Under Development", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
