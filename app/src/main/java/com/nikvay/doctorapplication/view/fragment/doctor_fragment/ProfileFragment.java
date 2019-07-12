package com.nikvay.doctorapplication.view.fragment.doctor_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SuccessDialogDisplay;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.AddDepartmentActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Context mContext;
    private TextView textName, textEmail, textDoctorName, textDoctorEmail, textDoctorProfile, textDoctorAddress;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private Button btn_update_profile;
    private String doctor_id, user_id, name, email, address, profile;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessDialogDisplay successDialogDisplay;

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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textDoctorName = view.findViewById(R.id.textDoctorName);
        textDoctorEmail = view.findViewById(R.id.textDoctorEmail);
        textDoctorProfile = view.findViewById(R.id.textDoctorProfile);
        textDoctorAddress = view.findViewById(R.id.textDoctorAddress);
        btn_update_profile = view.findViewById(R.id.btn_update_profile);

        successDialogDisplay = new SuccessDialogDisplay(mContext);
        errorMessageDialog = new ErrorMessageDialog(mContext);


        doctorModelArrayList = SharedUtils.getUserDetails(mContext);
        textName.setText(doctorModelArrayList.get(0).getName());
        textEmail.setText(doctorModelArrayList.get(0).getEmail());
        textDoctorName.setText(doctorModelArrayList.get(0).getName());
        textDoctorEmail.setText(doctorModelArrayList.get(0).getEmail());
        textDoctorAddress.setText(doctorModelArrayList.get(0).getAddress());
        textDoctorProfile.setText(doctorModelArrayList.get(0).getProfile());
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();
        user_id=doctorModelArrayList.get(0).getUser_id();


    }

    private void events() {
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {

                    if (NetworkUtils.isNetworkAvailable(mContext)) {
                        updateProfile();
                    } else
                        NetworkUtils.isNetworkNotAvailable(mContext);

                }


            }
        });
    }

    private void updateProfile() {

        Call<SuccessModel> call = apiInterface.updateProfile(doctor_id, user_id, name,email,profile,address);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel departmentModel = response.body();
                        String message = null, errorCode = null;

                        if (departmentModel != null) {
                            message = departmentModel.getMsg();
                            errorCode = departmentModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                SharedUtils.updateProfile(mContext,name,email,address,profile);
                                successDialogDisplay.showDialog("Update  Profile successfully ");
                                ((MainActivity) mContext).loadFragment(new HomeFragment());
                            }  else if(errorCode.equalsIgnoreCase("2")){
                                errorMessageDialog.showDialog("Email ID Already Exits ");
                            }
                            else
                            {
                                errorMessageDialog.showDialog("Parameter Missing");
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });

    }

    private boolean validation() {
        name = textDoctorName.getText().toString().trim();
        email = textDoctorEmail.getText().toString().trim();
        address = textDoctorAddress.getText().toString().trim();
        profile = textDoctorProfile.getText().toString().trim();

        if (name.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Enter Name");
            return false;
        } else if (email.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Enter Email");
            return false;
        } else if (address.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Enter Address");
            return false;
        } else if (profile.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Enter Profile");
            return false;
        }


        return true;

    }


}
