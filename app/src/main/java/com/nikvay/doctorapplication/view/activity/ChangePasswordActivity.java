package com.nikvay.doctorapplication.view.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText textOldPassword, textNewPassword;
    private Button btn_change_password;
    ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ImageView iv_close;

    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    String TAG = getClass().getSimpleName(), user_id, doctor_id, old_password, new_password,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        textOldPassword = findViewById(R.id.textOldPassword);
        textNewPassword = findViewById(R.id.textNewPassword);
        btn_change_password = findViewById(R.id.btn_change_password);
        iv_close = findViewById(R.id.iv_close);

        errorMessageDialog = new ErrorMessageDialog(ChangePasswordActivity.this);
        successMessageDialog = new SuccessMessageDialog(ChangePasswordActivity.this);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        doctorModelArrayList = SharedUtils.getUserDetails(ChangePasswordActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();
        email = doctorModelArrayList.get(0).getEmail();


    }

    private void events() {

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()) {

                    if (NetworkUtils.isNetworkAvailable(ChangePasswordActivity.this)) {
                        callChangePasswordWS();

                    } else
                        NetworkUtils.isNetworkNotAvailable(ChangePasswordActivity.this);
                }

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callChangePasswordWS() {

        Call<SuccessModel> call = apiInterface.changePassword(doctor_id, user_id, email, old_password , new_password);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel serviceModel = response.body();
                        String message = null, errorCode = null;

                        if (serviceModel != null) {
                            message = serviceModel.getMsg();
                            errorCode = serviceModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Password Change successfully");
                                //finish();
                            } else {
                                errorMessageDialog.showDialog("Old Password Is Wrong ");
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

    private boolean doValidation() {

        old_password = textOldPassword.getText().toString();
        new_password = textNewPassword.getText().toString();

        if (old_password.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Old Password Can't Be Empty");
            return false;
        } else if (new_password.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("New Password Can't Be Empty");
            return false;
        }

        return  true;

    }
}
