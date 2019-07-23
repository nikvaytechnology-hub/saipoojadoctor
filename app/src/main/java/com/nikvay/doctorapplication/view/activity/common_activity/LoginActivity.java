package com.nikvay.doctorapplication.view.activity.common_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.AdminMainActivity;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.DoctorSuperAdminActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private Button btn_login;
    private TextInputEditText textEmail,textPassword;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private String device_token,TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ShowProgress showProgress;
    private String is_super_doctor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("login_status",MODE_PRIVATE);
        find_All_Ids();
        events();
    }

    private void events() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=textEmail.getText().toString().trim();
                String password=textPassword.getText().toString().trim();
                if(email.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Email ID Can't Be Empty");
                   // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches())
                {
                    errorMessageDialog.showDialog("Enter valid Email");
                   // SnackbarCommon.displayValidation(v,"Enter valid Email");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Password Can't Be Empty");
                    //SnackbarCommon.displayValidation(v,"Password Can't Be Empty");
                }
                else if(password.length()>15||password.length()<5)
                {
                    errorMessageDialog.showDialog("Password Length Between 5 to 15");
                    //SnackbarCommon.displayValidation(v,"Password Length Between 5 to 15");
                }
                else
                {

                    if (NetworkUtils.isNetworkAvailable(LoginActivity.this))
                        callLogin(email,password);
                    else
                        NetworkUtils.isNetworkNotAvailable(LoginActivity.this);

                }

            }
        });
    }

    private void callLogin(String email, String password) {


        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.loginCall(email, password, device_token);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {
                                doctorModelArrayList=successModel.getDoctorModelArrayList();

                                is_super_doctor=doctorModelArrayList.get(0).getIs_super_admin();
                                if(is_super_doctor.equalsIgnoreCase("1"))
                                {
                                    SharedUtils.putSharedUtils(LoginActivity.this);
                                    SharedUtils.addUserUtils(LoginActivity.this, doctorModelArrayList);
                                    loginAdminIntent();
                                }
                                else {
                                    //  successMessageDialog.showDialog("Login Successfully");
                                    SharedUtils.putSharedUtils(LoginActivity.this);
                                    SharedUtils.addUserUtils(LoginActivity.this, doctorModelArrayList);
                                    loginIntent();
                                }
                            } else {
                                errorMessageDialog.showDialog("User Not Registered");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }


    private void loginIntent() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("login_status","doctor");
        editor.apply();
        editor.commit();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    private void loginAdminIntent() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("login_status","admin");
        editor.apply();
        editor.commit();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoginActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    private void find_All_Ids() {
        textEmail=findViewById(R.id.textEmail);
        textPassword=findViewById(R.id.textPassword);
        btn_login=findViewById(R.id.btn_login);
        showProgress=new ShowProgress(LoginActivity.this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        errorMessageDialog= new ErrorMessageDialog(LoginActivity.this);
        successMessageDialog= new SuccessMessageDialog(LoginActivity.this);

        device_token= SharedUtils.getDeviceToken(LoginActivity.this);

    }

    private void isSuperAdminIntent() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoginActivity.this, DoctorSuperAdminActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }


  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();
        return true;
        // Disable back button..............
    }*/

}
