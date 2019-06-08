package com.nikvay.doctorapplication.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.AppointmentDialog;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAppointmentActivity extends AppCompatActivity {


    private ImageView iv_close;
    private TextView textDone,textDateDay,textTime,textPatientName,textEmail,textContact,textServiceName,textDuration,textServiceCost,textcommentName,textLabelName
            ,textPending,textConfirm;
    private ServiceModel serviceModel;
    private PatientModel patientModel;
    private  String date="",time="",service_id,patient_id,TAG = getClass().getSimpleName(),user_id,doctor_id,comment="",label="1";
    private ApiInterface apiInterface;
    private RelativeLayout relativeLayoutComments,relativeLayoutCommentsHide,relativeLayoutLabelHide;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private AppointmentDialog appointmentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        find_All_IDs();
        events();
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewAppointmentActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        relativeLayoutComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog();
            }
        });


        textDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(NewAppointmentActivity.this))
                    callAddAppoitment();
                else
                    NetworkUtils.isNetworkNotAvailable(NewAppointmentActivity.this);

            }
        });


        textPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="0";
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.black));

            }
        });
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="1";
                textLabelName.setText("Confirm");
                textLabelName.setTextColor(getResources().getColor(R.color.confirm));
            }
        });

    }

    private void callAddAppoitment() {
        Call<SuccessModel> call = apiInterface.addAppointment(doctor_id,user_id,service_id,patient_id,date,time,comment,label);


        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
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

                                appointmentDialog.showDialog("Add Appointment Successfully !");

                            } else {
                                errorMessageDialog.showDialog("Wrong Appointment");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                errorMessageDialog.showDialog(t.getMessage());
            }
        });


    }


    private void commentDialog() {

        final Dialog dialog = new Dialog(NewAppointmentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comment_add_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextInputEditText textComment= dialog.findViewById(R.id.textComment);
        Button btn_done =dialog.findViewById(R.id.btn_done);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comment=textComment.getText().toString().trim();
                dialog.dismiss();
                relativeLayoutCommentsHide.setVisibility(View.VISIBLE);
                textcommentName.setText(comment);


            }
        });

        dialog.show();
        
        
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close=findViewById(R.id.iv_close);
        textDone=findViewById(R.id.textDone);
        textDateDay=findViewById(R.id.textDateDay);
        textTime=findViewById(R.id.textTime);
        textPatientName=findViewById(R.id.textPatientName);
        textEmail=findViewById(R.id.textEmail);
        textContact=findViewById(R.id.textContact);
        textServiceName=findViewById(R.id.textServiceName);
        textDuration=findViewById(R.id.textDuration);
        textServiceCost=findViewById(R.id.textServiceCost);
        relativeLayoutComments=findViewById(R.id.relativeLayoutComments);
        relativeLayoutCommentsHide=findViewById(R.id.relativeLayoutCommentsHide);
        relativeLayoutLabelHide=findViewById(R.id.relativeLayoutLabelHide);
        textcommentName=findViewById(R.id.textcommentName);
        textLabelName=findViewById(R.id.textLabelName);
        textPending=findViewById(R.id.textPending);
        textConfirm=findViewById(R.id.textConfirm);

        errorMessageDialog= new ErrorMessageDialog(NewAppointmentActivity.this);
        appointmentDialog= new AppointmentDialog(NewAppointmentActivity.this);

        doctorModelArrayList= SharedUtils.getUserDetails(NewAppointmentActivity.this);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();
        user_id=doctorModelArrayList.get(0).getUser_id();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            patientModel= (PatientModel) bundle.getSerializable(StaticContent.IntentKey.PATIENT_DETAIL);
            date=bundle.getString(StaticContent.IntentKey.DATE);
            time=bundle.getString(StaticContent.IntentKey.TIME);
            service_id=serviceModel.getService_id();
            patient_id=patientModel.getPatient_id();

            textServiceName.setText(serviceModel.getS_name());
            textDuration.setText(serviceModel.getService_time());
            textServiceCost.setText(serviceModel.getService_cost());

            textPatientName.setText(patientModel.getName());
            textEmail.setText(patientModel.getEmail());
            textContact.setText(patientModel.getPhone_no());

            textDateDay.setText(date);
            textTime.setText(time);

            //Toast.makeText(this, serviceModel.getS_name()+""+patientModel.getPatient_id()+" "+date+" "+time, Toast.LENGTH_SHORT).show();

        }
        textLabelName.setTextColor(getResources().getColor(R.color.confirm));
        textLabelName.setText("Confirm");



    }
}
