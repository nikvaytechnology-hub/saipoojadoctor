package com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.model.ClassModel;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.ShowProgress;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddClassActivity extends AppCompatActivity
{
    private TextView textClass, textDuration, textSeats, textCost,textTitleName, textDescription, textDate;
    private ImageView iv_close_activity;
    private Button btnSave;
    private String textClassName, textClassDuration, textClassSeats, textClassCost, textClassDescription, doctor_id, user_id, TAG = getClass().getSimpleName(), textClassDate,mTitle="Update Details",class_id;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ShowProgress showProgress;
    private ClassModel classModel;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_class);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation())
                {
                    callAddClass();

                    if (btnSave.getText().equals(StaticContent.ButtonContent.UPDATE)) {
                        if (NetworkUtils.isNetworkAvailable(AdminAddClassActivity.this)) {
                            callUpdateClass();
                        } else
                            NetworkUtils.isNetworkNotAvailable(AdminAddClassActivity.this);
                    } else {
                        if (NetworkUtils.isNetworkAvailable(AdminAddClassActivity.this))
                        {

                        } else
                            NetworkUtils.isNetworkNotAvailable(AdminAddClassActivity.this);
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
        Toast.makeText(this, "class", Toast.LENGTH_SHORT).show();
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
        // textClassDate = textDate.getText().toString().trim();

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
        }
        /*else if (textClassDate.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Date Can't Empty");
            return false;
        }
*/

        return true;
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close_activity = findViewById(R.id.iv_close);
        textClass = findViewById(R.id.textClass);
        textDuration = findViewById(R.id.textDuration);
        textSeats = findViewById(R.id.textSeats);
        textCost = findViewById(R.id.textCost);
        btnSave = findViewById(R.id.btnSave);
        textTitleName = findViewById(R.id.textTitleName);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);


        errorMessageDialog = new ErrorMessageDialog(AdminAddClassActivity.this);
        successMessageDialog = new SuccessMessageDialog(AdminAddClassActivity.this);


        doctorModelArrayList = SharedUtils.getUserDetails(AdminAddClassActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        showProgress = new ShowProgress(AdminAddClassActivity.this);
        errorMessageDialog = new ErrorMessageDialog(AdminAddClassActivity.this);
        successMessageDialog = new SuccessMessageDialog(AdminAddClassActivity.this);


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
            btnSave.setText(StaticContent.ButtonContent.UPDATE);
            class_id = classModel.getClass_id();
        }
    }
}
