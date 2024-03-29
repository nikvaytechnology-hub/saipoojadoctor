package com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.interfaceutils.SelectDoctorInterface;
import com.nikvay.saipoojadoctor.model.DoctorListModel;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.PatientModel;
import com.nikvay.saipoojadoctor.model.ServiceModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.ShowProgress;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.utils.SuccessMessageDialog;
import com.nikvay.saipoojadoctor.view.adapter.admin_doctor_adapter.DoctorListAdapter;
import com.nikvay.saipoojadoctor.view.adapter.doctor_adapter.ServiceListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddPatientActivity extends AppCompatActivity implements SelectDoctorInterface {

    private TextView textName, textEmail, textPhone, textAddress, textTitleName, textAge;
    private String email, name, address, phone, mTitle = "Add Customer", patient_id, age,doctor_id="";
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ImageView iv_close_activity;
    private PatientModel patientModel;
    private ApiInterface apiInterface;
    private Button btnSave;
    private String super_doctor_id, user_id, TAG = getClass().getSimpleName(), gender;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private RadioButton radioSexButton;
    private RadioGroup rGenderGroup;


    //Select Doctor
    private Dialog selectDoctorDialog;
    private EditText editSearchDoctor;
    private Button btnCancelDialogDoctor,btnOkDialogDoctor;
    private RecyclerView recyclerDialogDoctor;
    private ArrayList<DoctorListModel> doctorListModelArrayList = new ArrayList<>();
    ShowProgress showProgress;
    private DoctorListAdapter doctorListAdapter;

    private LinearLayout linearLayoutDoctorList;
    private TextView  textDoctor;
    private LinearLayout linearLayoutDoctor;


    //Select Service
    private Dialog selectServiceDialog;
    private EditText editSearchService;
    private Button btnCancelDialogService,btnOkDialogService;
    private RecyclerView recyclerDialogService;
    private ArrayList<ServiceModel> ServiceModelArrayList = new ArrayList<>();
    private ServiceListAdapter serviceListAdapter;

    private LinearLayout linearLayoutServiceList;
    private TextView  textService;
    private LinearLayout linearLayoutService;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_patient);

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
        linearLayoutDoctorList = findViewById(R.id.linearLayoutDoctorList);
        textDoctor = findViewById(R.id.textDoctor);
        linearLayoutDoctor = findViewById(R.id.linearLayoutDoctor);
        iv_close_activity = findViewById(R.id.iv_close);
        rGenderGroup = findViewById(R.id.rGenderGroup);
        btnSave.setText(StaticContent.ButtonContent.SAVE);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        doctorModelArrayList = SharedUtils.getUserDetails(AdminAddPatientActivity.this);
        super_doctor_id = doctorModelArrayList.get(0).getDoctor_id();
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
            patient_id = patientModel.getPatient_id();
            gender = patientModel.getGender();
            btnSave.setText(StaticContent.ButtonContent.UPDATE);
            disableFields();

        }



        //Select Doctor Start

        selectDoctorDialog = new Dialog(this);
        selectDoctorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectDoctorDialog.setContentView(R.layout.dialog_select_doctor);

        btnCancelDialogDoctor = selectDoctorDialog.findViewById(R.id.btnCancelDialogDoctor);
        btnOkDialogDoctor = selectDoctorDialog.findViewById(R.id.btnOkDialogDoctor);
        editSearchDoctor = selectDoctorDialog.findViewById(R.id.editSearchDoctor);
        selectDoctorDialog.setCancelable(false);
        recyclerDialogDoctor = selectDoctorDialog.findViewById(R.id.recyclerDialogDoctor);

        LinearLayoutManager linearLayoutManagerDoctor = new LinearLayoutManager(getApplicationContext());
        recyclerDialogDoctor.setLayoutManager(linearLayoutManagerDoctor);


        Window window = selectDoctorDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //select Doctor End






        errorMessageDialog = new ErrorMessageDialog(AdminAddPatientActivity.this);
        successMessageDialog = new SuccessMessageDialog(AdminAddPatientActivity.this);
        showProgress = new ShowProgress(AdminAddPatientActivity.this);

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

                        if (NetworkUtils.isNetworkAvailable(AdminAddPatientActivity.this))
                            callAddNewPatient();
                        else
                            NetworkUtils.isNetworkNotAvailable(AdminAddPatientActivity.this);


                    } else {
                        if (NetworkUtils.isNetworkAvailable(AdminAddPatientActivity.this))
                            callUpdatePatient();
                        else
                            NetworkUtils.isNetworkNotAvailable(AdminAddPatientActivity.this);


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
                radioSexButton = findViewById(checkedId);
                gender = radioSexButton.getText().toString();
            }
        });


        linearLayoutDoctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtils.isNetworkAvailable(AdminAddPatientActivity.this))
                    callDoctorList();
                else
                    NetworkUtils.isNetworkNotAvailable(AdminAddPatientActivity.this);

            }
        });

        btnOkDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchDoctor.setText("");
                selectDoctorDialog.dismiss();
               /* if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        *//*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*//*
                    }
                    linearService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearService.setVisibility(View.GONE);
                }*/

            }
        });

        btnCancelDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  serviceModel = null;
                linearService.setVisibility(View.GONE);*/
                editSearchDoctor.setText("");
                selectDoctorDialog.dismiss();
            }
        });


        editSearchDoctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                doctorListAdapter.getFilter().filter(editSearchDoctor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






    }

    private void callUpdatePatient() {

        Call<SuccessModel> call = apiInterface.updatePatient(doctor_id, patient_id, name, email, user_id, address, phone, age, gender);


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
                                successMessageDialog.showDialog("Update Patient");
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


        Call<SuccessModel> call = apiInterface.addPatientAdmin(super_doctor_id, name, email, user_id, address, phone, age, gender,doctor_id);


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


    private void callDoctorList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.doctorList();
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
                            selectDoctorDialog.show();


                            doctorListModelArrayList.clear();
                            if (code.equalsIgnoreCase("1")) {

                                doctorListModelArrayList = successModel.getDoctorListModelArrayList();

                                if (doctorListModelArrayList.size() != 0) {
                                    Collections.reverse(doctorListModelArrayList);
                                    doctorListAdapter = new DoctorListAdapter(AdminAddPatientActivity.this, doctorListModelArrayList,true, AdminAddPatientActivity.this);
                                    recyclerDialogDoctor.setAdapter(doctorListAdapter);
                                    doctorListAdapter.notifyDataSetChanged();
                                    //recyclerViewDoctorList.addItemDecoration(new DividerItemDecoration(DoctorListActivity.this, DividerItemDecoration.VERTICAL));

                                } else {
                                    // iv_no_data_found.setVisibility(View.VISIBLE);
                                }

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
        } else if (age.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Age Can't Empty ");
            return false;
        } else if (doctor_id.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Select Doctor ");
            return false;
        }else if (gender.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Gender Can't Empty ");
            return false;
        }
        return true;
    }


    @Override
    public void getDoctor(DoctorListModel DoctorListModel) {
        linearLayoutDoctor.setVisibility(View.VISIBLE);
        textDoctor.setText(DoctorListModel.getName());
        doctor_id=DoctorListModel.getDoctor_id();
    }
}
