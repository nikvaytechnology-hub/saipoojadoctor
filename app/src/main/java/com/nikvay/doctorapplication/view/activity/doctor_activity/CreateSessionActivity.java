package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.SharedPreferences;
import android.net.wifi.aware.SubscribeConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.AppointmentDialog;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSessionActivity extends AppCompatActivity {

    private ImageView iv_close;
    private TextView textPending, textConfirm, textLabelName;
    private Button btnCreate;
    private String label = "1", date = "", time = "", class_id = "",doctor_id,
            user_id,TAG = getClass().getSimpleName(),cost,no_of_seats,class_name;
    private String mTitle = "Class Details";
    private ClassModel classModel;
    private TextView textClass, textCost, textDate, textSeats;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private AppointmentDialog  appointmentDialog;
    private ErrorMessageDialog errorMessageDialog;
SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
         sharedPreferences2=getSharedPreferences("session_details",MODE_PRIVATE);

        find_All_ID();
        event();
    }

    private void find_All_ID() {
        date=getIntent().getStringExtra("date");
        time=getIntent().getStringExtra("time");
        sharedPreferences=getSharedPreferences("class_name",MODE_PRIVATE);
        class_id=sharedPreferences.getString("class_id","");
        class_name=sharedPreferences.getString("class_name","");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close = findViewById(R.id.iv_close);
       // textPending = findViewById(R.id.textPending);
        //textConfirm = findViewById(R.id.textConfirm);
        btnCreate = findViewById(R.id.btnCreate);
        textLabelName = findViewById(R.id.textLabelName);
        textClass = findViewById(R.id.textClass);
        textClass.setText(class_name);
        textCost = findViewById(R.id.textCost);
       // textDate = findViewById(R.id.textDate);
        textSeats = findViewById(R.id.textSeats);

        cost=sharedPreferences2.getString("cost","");
        no_of_seats=sharedPreferences2.getString("seats","");
        Toast.makeText(this, cost+""+no_of_seats, Toast.LENGTH_SHORT).show();
        textCost.setText(cost);
        textSeats.setText(no_of_seats);

        doctorModelArrayList = SharedUtils.getUserDetails(CreateSessionActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


   /*     Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            time = bundle.getString(StaticContent.IntentKey.TIME);
            date = bundle.getString(StaticContent.IntentKey.DATE);
        }

        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS)) {
            textClass.setText(classModel.getName());
            textCost.setText(classModel.getCost());
            textDate.setText(classModel.getDate());
            textSeats.setText(date+"/"+time);
            class_id = classModel.getClass_id();
            cost=classModel.getCost();
            no_of_seats=classModel.getSeats();
        }
*/

        //textLabelName.setText("Confirm");
        appointmentDialog=new AppointmentDialog(CreateSessionActivity.this);
        errorMessageDialog=new ErrorMessageDialog(CreateSessionActivity.this);
    }

    private void event() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


/*
        textPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label = "0";
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.black));

            }
        });
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label = "1";
                textLabelName.setText("Confirm");
                textLabelName.setTextColor(getResources().getColor(R.color.confirm));
            }
        });
*/

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(CreateSessionActivity.this))
                {

                    callAddSession();

                }
                else
                    NetworkUtils.isNetworkNotAvailable(CreateSessionActivity.this);

            }
        });

    }

    private void callAddSession() {
        Call<SuccessModel> call =apiInterface.callSessionAdd(class_id,doctor_id,date,time,label,cost,no_of_seats);
        Toast.makeText(this, "date "+date+"doctor id "+doctor_id+" time"+"label"+label, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response  = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);
                try {
                    if (response.isSuccessful()) {
                        SuccessModel serviceModel = response.body();
                        String message = null, errorCode = null;

                        if (serviceModel != null) {
                            message = serviceModel.getMsg();
                            errorCode = serviceModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                appointmentDialog.showDialog("Session Add succesfully ");
                                //finish();
                            } else if (errorCode.equalsIgnoreCase("3")) {
                                errorMessageDialog.showDialog("Session is Already Sucessfully ");
                            } else {
                                errorMessageDialog.showDialog("Parameter is missing");
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


}
