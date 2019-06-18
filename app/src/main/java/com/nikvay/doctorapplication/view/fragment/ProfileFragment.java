package com.nikvay.doctorapplication.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.view.activity.NewAddServiceActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private Context mContext;
    private TextView textName, textEmail, textDoctorName, textDoctorEmail, textDoctorProfile, textDoctorAddress;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private Button btn_update_profile;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();

        return view;
    }

    private void find_All_IDs(View view) {
        textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textDoctorName = view.findViewById(R.id.textDoctorName);
        textDoctorEmail = view.findViewById(R.id.textDoctorEmail);
        textDoctorProfile = view.findViewById(R.id.textDoctorProfile);
        textDoctorAddress = view.findViewById(R.id.textDoctorAddress);
        btn_update_profile = view.findViewById(R.id.btn_update_profile);

        doctorModelArrayList = SharedUtils.getUserDetails(mContext);
        textName.setText(doctorModelArrayList.get(0).getName());
        textEmail.setText(doctorModelArrayList.get(0).getEmail());
        textDoctorName.setText(doctorModelArrayList.get(0).getName());
        textDoctorEmail.setText(doctorModelArrayList.get(0).getEmail());
        textDoctorAddress.setText(doctorModelArrayList.get(0).getAddress());
        textDoctorProfile.setText(doctorModelArrayList.get(0).getProfile());
        textDoctorProfile.setText(doctorModelArrayList.get(0).getProfile());


    }

    private void events() {
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
