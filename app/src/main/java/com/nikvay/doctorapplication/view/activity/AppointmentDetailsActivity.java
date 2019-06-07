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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.ServiceModel;
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

public class AppointmentDetailsActivity extends AppCompatActivity {

    private AppoinmentListModel appoinmentListModel;
    private TextView textDateDay,textTime,textPatientName,textEmail,textContact,textServiceName,textDuration,textServiceCost,textUpdate,textcommentName,textLabelName
            ,textPending,textConfirm,textCancel,textComplete;
    private ApiInterface apiInterface;
    private ImageView iv_close;
    private RelativeLayout relativeLayoutComments,relativeLayoutCommentsHide,relativeLayoutLabelHide,relativeLayoutReschedule;
    private ErrorMessageDialog errorMessageDialog;
    private AppointmentDialog appointmentDialog;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private  String date="",time="",service_id,patient_id,TAG = getClass().getSimpleName(),user_id,doctor_id,comment="",label="",appointment_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        find_All_IDs();
        events();

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            appoinmentListModel= (AppoinmentListModel) bundle.getSerializable(StaticContent.IntentKey.APPOINTMENT);
            date=appoinmentListModel.getDate();
            time=appoinmentListModel.getTime();
            textDateDay.setText(date);
            textTime.setText(time);
            textPatientName.setText(appoinmentListModel.getName());
            textEmail.setText(appoinmentListModel.getEmail());
            textContact.setText(appoinmentListModel.getPhone_no());
            textServiceName.setText(appoinmentListModel.getS_name());
            textDuration.setText("Time"+" "+appoinmentListModel.getService_time());
            textServiceCost.setText("Rs"+" "+appoinmentListModel.getService_cost());
            //textcommentName.setText(appoinmentListModel.getde);
            label=appoinmentListModel.getLabel();
            service_id=appoinmentListModel.getService_id();
            patient_id=appoinmentListModel.getPatient_id();
            appointment_id=appoinmentListModel.getAppointment_id();

            if(label.equalsIgnoreCase("0"))
            {
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.pending));
                textPending.setVisibility(View.GONE);
            }else if(label.equalsIgnoreCase("1"))
            {
                textLabelName.setText("Confirm");
                textLabelName.setTextColor(getResources().getColor(R.color.confirm));
                textConfirm.setVisibility(View.GONE);

            }else if(label.equalsIgnoreCase("2"))
            {
                textLabelName.setText("Canceled");
                textLabelName.setTextColor(getResources().getColor(R.color.cancel));
                textCancel.setVisibility(View.GONE);
            }
            else
            {
                textLabelName.setText("Completed");
                textLabelName.setTextColor(getResources().getColor(R.color.complete));
                textComplete.setVisibility(View.GONE);
            }
            textcommentName.setText(appoinmentListModel.getComment());
        }

    }

    private void events() {
        relativeLayoutComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog();
            }
        });


        textUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(AppointmentDetailsActivity.this))
                    editAppointment();
                else
                    NetworkUtils.isNetworkNotAvailable(AppointmentDetailsActivity.this);
            }
        });

        relativeLayoutReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(AppointmentDetailsActivity.this,DateTimeSelectActivity.class);
                intent.putExtra(StaticContent.IntentKey.RESCHEDULE,StaticContent.IntentValue.RESCHEDULE);
                startActivity(intent);*/
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        textPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="0";
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.pending));

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
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="2";
                textLabelName.setText("Cancel");
                textLabelName.setTextColor(getResources().getColor(R.color.cancel));
            }
        });
        textComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="3";
                textLabelName.setText("Completed");
                textLabelName.setTextColor(getResources().getColor(R.color.complete));
            }
        });

    }

    private void editAppointment() {
        Call<SuccessModel> call = apiInterface.editAppointment(doctor_id,patient_id,appointment_id,date,time,comment,label);


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

                                appointmentDialog.showDialog("Update Appointment Successfully !");

                            } else {
                                errorMessageDialog.showDialog("Response Wrong");
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

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        textDateDay=findViewById(R.id.textDateDay);
        textTime=findViewById(R.id.textTime);
        textPatientName=findViewById(R.id.textPatientName);
        textEmail=findViewById(R.id.textEmail);
        textContact=findViewById(R.id.textContact);
        textServiceName=findViewById(R.id.textServiceName);
        textDuration=findViewById(R.id.textDuration);
        textServiceCost=findViewById(R.id.textServiceCost);
        textUpdate=findViewById(R.id.textUpdate);
        textcommentName=findViewById(R.id.textcommentName);
        textLabelName=findViewById(R.id.textLabelName);
        iv_close=findViewById(R.id.iv_close);

        textPending=findViewById(R.id.textPending);
        textConfirm=findViewById(R.id.textConfirm);
        textCancel=findViewById(R.id.textCancel);
        textComplete=findViewById(R.id.textComplete);

        relativeLayoutComments=findViewById(R.id.relativeLayoutComments);
        relativeLayoutCommentsHide=findViewById(R.id.relativeLayoutCommentsHide);
        relativeLayoutLabelHide=findViewById(R.id.relativeLayoutLabelHide);
        relativeLayoutReschedule=findViewById(R.id.relativeLayoutReschedule);


        errorMessageDialog= new ErrorMessageDialog(AppointmentDetailsActivity.this);
        appointmentDialog= new AppointmentDialog(AppointmentDetailsActivity.this);

        doctorModelArrayList= SharedUtils.getUserDetails(AppointmentDetailsActivity.this);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();
        user_id=doctorModelArrayList.get(0).getUser_id();

    }

    private void commentDialog() {

        final Dialog dialog = new Dialog(AppointmentDetailsActivity.this);
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
}
