package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.adapter.AppointmentListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewClassActivity extends AppCompatActivity {

    private TextView textClass, textDuration, textSeats, textCost, textSave, textTitleName, textDescription, textDate;
    private ImageView iv_close_activity;
    private String textClassName, textClassDuration, textClassSeats, textClassCost, textClassDescription, doctor_id, user_id, TAG = getClass().getSimpleName(), textClassDate,mTitle="Class Details",class_id;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ShowProgress showProgress;
    private ClassModel classModel;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        find_All_IDs();
        events();
    }

    private void events() {
        iv_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_close_activity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        });

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {
                    callAddClass();

                    if (textSave.getText().equals(StaticContent.ButtonContent.UPDATE)) {
                        if (NetworkUtils.isNetworkAvailable(NewClassActivity.this)) {
                            callUpdateClass();
                        } else
                            NetworkUtils.isNetworkNotAvailable(NewClassActivity.this);
                    } else {
                        if (NetworkUtils.isNetworkAvailable(NewClassActivity.this)) {
                            callAddClass();
                        } else
                            NetworkUtils.isNetworkNotAvailable(NewClassActivity.this);
                    }
                }

            }
        });

    }

    private void callUpdateClass() {

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.updateClass(class_id,doctor_id, user_id, textClassName, textClassCost, textClassDuration, textClassDescription, textClassSeats, textClassDate);
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
                                successMessageDialog.showDialog("Class Update Successfully");
                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
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

    private void callAddClass() {

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.addClass(doctor_id, user_id, textClassName, textClassCost, textClassDuration, textClassDescription, textClassSeats, textClassDate);
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
                                successMessageDialog.showDialog("Class Add Successfully");
                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
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

    private boolean validation() {

        textClassName = textClass.getText().toString().trim();
        textClassDuration = textDuration.getText().toString().trim();
        textClassCost = textCost.getText().toString().trim();
        textClassSeats = textSeats.getText().toString().trim();
        textClassDescription = textDescription.getText().toString().trim();
        textClassDate = textDate.getText().toString().trim();

        if (textClassName.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Class Name Can't Empty");
            return false;
        } else if (textClassDuration.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Duration Can't Empty");
            return false;
        } else if (textClassSeats.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Seats Can't Empty");
            return false;
        } else if (textClassCost.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Cost Can't Empty");
            return false;
        } else if (textClassDate.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Date Can't Empty");
            return false;
        }


        return true;
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close_activity = findViewById(R.id.iv_close);
        textClass = findViewById(R.id.textClass);
        textDuration = findViewById(R.id.textDuration);
        textSeats = findViewById(R.id.textSeats);
        textCost = findViewById(R.id.textCost);
        textSave = findViewById(R.id.textSave);
        textTitleName = findViewById(R.id.textTitleName);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);


        errorMessageDialog = new ErrorMessageDialog(NewClassActivity.this);
        successMessageDialog = new SuccessMessageDialog(NewClassActivity.this);


        doctorModelArrayList = SharedUtils.getUserDetails(NewClassActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        showProgress = new ShowProgress(NewClassActivity.this);
        errorMessageDialog = new ErrorMessageDialog(NewClassActivity.this);
        successMessageDialog = new SuccessMessageDialog(NewClassActivity.this);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            textTitleName.setText("Update Class");
        }

        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS)) {
            textClass.setText(classModel.getName());
            textDuration.setText(classModel.getDuration());
            textCost.setText(classModel.getCost());
            textDescription.setText(classModel.getDescription());
            textDate.setText(classModel.getDate());
            textSeats.setText(classModel.getSeats());
            textSave.setText(StaticContent.ButtonContent.UPDATE);
            class_id=classModel.getClass_id();
        }
    }
}
