package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.utils.SuccessDialogClosed;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDoctorDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPatientActivity extends AppCompatActivity implements SuccessDialogClosed {

    private TextView textName, textEmail, textPhone, textAddress,textTitleName,textAge;
    private String email, name, address, phone, mTitle = "Add Customer",patient_id,age;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private SuccessMessageDoctorDialog successMessageDoctorDialog;
    private ImageView iv_close_activity;
    private PatientModel patientModel;
    private ApiInterface apiInterface;
    private Button btnSave;
    private String doctor_id, user_id, TAG = getClass().getSimpleName(),gender;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private RadioButton radioSexButton;
    private RadioGroup rGenderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        textName = findViewById(R.id.textName);
        textAddress = findViewById(R.id.textAddress);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textPhone);
        btnSave = findViewById(R.id.btnSave);
        textTitleName = findViewById(R.id.textTitleName);
        iv_close_activity = findViewById(R.id.iv_close);
        textAge = findViewById(R.id.textAge);
        iv_close_activity = findViewById(R.id.iv_close);
        rGenderGroup=findViewById(R.id.rGenderGroup);
        btnSave.setText(StaticContent.ButtonContent.SAVE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        doctorModelArrayList = SharedUtils.getUserDetails(NewPatientActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            patientModel = (PatientModel) bundle.getSerializable(StaticContent.IntentKey.PATIENT_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            textTitleName.setText(mTitle);
        }

        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT)) {
            textName.setText(patientModel.getName());
            textEmail.setText(patientModel.getEmail());
            textPhone.setText(patientModel.getPhone_no());
            textAddress.setText(patientModel.getAddress());
            textAge.setText(patientModel.getAge());
            patient_id=patientModel.getPatient_id();
            gender=patientModel.getGender();
            btnSave.setText(StaticContent.ButtonContent.UPDATE);
            disableFields();

        }

        errorMessageDialog = new ErrorMessageDialog(NewPatientActivity.this);
        successMessageDialog = new SuccessMessageDialog(NewPatientActivity.this);
        successMessageDoctorDialog=new SuccessMessageDoctorDialog(this,true);
    }
    private void disableFields() {
        textEmail.setEnabled(false);
        textEmail.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void events() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = textEmail.getText().toString().trim();
                name = textName.getText().toString().trim();
                phone = textPhone.getText().toString().trim();
                address = textAddress.getText().toString().trim();
                age = textAge.getText().toString().trim();

                if (validation()) {

                    if (btnSave.getText().equals(StaticContent.ButtonContent.SAVE)) {

                        if (NetworkUtils.isNetworkAvailable(NewPatientActivity.this))
                            callAddNewPatient();
                        else
                            NetworkUtils.isNetworkNotAvailable(NewPatientActivity.this);


                    } else {
                        if (NetworkUtils.isNetworkAvailable(NewPatientActivity.this))
                            callUpdatePatient();
                        else
                            NetworkUtils.isNetworkNotAvailable(NewPatientActivity.this);


                    }

                }

            }
        });
        iv_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton=findViewById(checkedId);
                gender=radioSexButton.getText().toString();
            }
        });

    }

    private void callUpdatePatient() {

        Call<SuccessModel> call = apiInterface.updatePatient(doctor_id,patient_id,name,email,user_id, address,phone,age,gender);


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
                                successMessageDoctorDialog.showDialog("Update Patient",true);
                            } else if (code.equalsIgnoreCase("2")) {
                                errorMessageDialog.showDialog("Patient Already Exist");
                            } else {
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
                errorMessageDialog.showDialog(t.getMessage());
            }
        });
    }

    private void callAddNewPatient() {


        Call<SuccessModel> call = apiInterface.addNewPatient(doctor_id, name, email, user_id, address,phone,age,gender);


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
                                successMessageDialog.showDialog("New Patient Add Sucessfully");
                            } else if (code.equalsIgnoreCase("2")) {
                                errorMessageDialog.showDialog("Patient Already Exist");
                            } else {
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
                errorMessageDialog.showDialog(t.getMessage());
            }
        });


    }

    private boolean validation() {

        if (email.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Email ID Can't Empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()) {
            errorMessageDialog.showDialog("Enter valid Email");
            return false;
        } else if (name.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Name Can't Empty");
            return false;
        } else if (phone.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Phone Number Can't Empty");
            return false;
        } else if (phone.length() != 10) {
            errorMessageDialog.showDialog("InValid Phone Number");
            return false;
        }else if (age.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Age Can't Empty ");
            return false;
        }else if (gender.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Gender Can't Empty ");
            return false;
        }
        return true;
    }

    @Override
    public void dialogClosed(boolean mClosed) {
        finish();
    }
}
