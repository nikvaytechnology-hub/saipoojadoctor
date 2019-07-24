package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectDepartmentInterface;
import com.nikvay.doctorapplication.model.DepartmentModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.DepartmentListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDoctorActivity extends AppCompatActivity implements SelectDepartmentInterface {

    private TextInputEditText textDoctorName, textEmail, textAddress, textProfile,textPhone;
    private String doctor_name, email, address, profile, department,phone,TAG = getClass().getSimpleName(),
            super_doctor_id,user_id,is_super_admin,gender,doctor_type,department_id="";
    private ImageView iv_close;
    private Button btnSave;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private RadioButton radioSexButton;
    private RadioGroup rGenderGroup;

    private RadioButton radioDoctorButton;
    private RadioGroup radioGroupDoctorType;
    private LinearLayout linearLayoutDepartmentList;
    private TextView  textDepartment;
    private LinearLayout linearLayoutDepartment;

    //Select  Department
    private Dialog selectDepartmentDialog;
    private EditText editSearchDepartment;
    private Button btnCancelDialogDepartment,btnOkDialogDepartment;
    private RecyclerView  recyclerDialogDepartment;
    ArrayList<DepartmentModel> departmentModelArrayList = new ArrayList<>();
    DepartmentListAdapter departmentListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);


        doctorModelArrayList = SharedUtils.getUserDetails(AddDoctorActivity.this);
        super_doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        textDoctorName = findViewById(R.id.textDoctorName);
        textEmail = findViewById(R.id.textEmail);
        textAddress = findViewById(R.id.textAddress);
        textProfile = findViewById(R.id.textProfile);
        textDepartment = findViewById(R.id.textDepartment);
        iv_close = findViewById(R.id.iv_close);
        btnSave = findViewById(R.id.btnSave);
        textPhone = findViewById(R.id.textPhone);
        rGenderGroup=findViewById(R.id.rGenderGroup);
        radioGroupDoctorType=findViewById(R.id.radioGroupDoctorType);
        linearLayoutDepartmentList = findViewById(R.id.linearLayoutDepartmentList);
        linearLayoutDepartment = findViewById(R.id.linearLayoutDepartment);

        //Select Department Start
        selectDepartmentDialog = new Dialog(this);
        selectDepartmentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectDepartmentDialog.setContentView(R.layout.dialog_select_department);

        btnCancelDialogDepartment = selectDepartmentDialog.findViewById(R.id.btnCancelDialogDepartment);
        btnOkDialogDepartment = selectDepartmentDialog.findViewById(R.id.btnOkDialogDepartment);
        editSearchDepartment = selectDepartmentDialog.findViewById(R.id.editSearchDepartment);
        selectDepartmentDialog.setCancelable(false);
        recyclerDialogDepartment = selectDepartmentDialog.findViewById(R.id.recyclerDialogDepartment);

        LinearLayoutManager linearLayoutManagerDepartment = new LinearLayoutManager(getApplicationContext());
        recyclerDialogDepartment.setLayoutManager(linearLayoutManagerDepartment);

        Window window = selectDepartmentDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //select Department End
        errorMessageDialog = new ErrorMessageDialog(AddDoctorActivity.this);
        successMessageDialog = new SuccessMessageDialog(AddDoctorActivity.this);
    }

    private void events() {

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {

                    if (NetworkUtils.isNetworkAvailable(AddDoctorActivity.this))
                        addNewDoctor();
                    else
                        NetworkUtils.isNetworkNotAvailable(AddDoctorActivity.this);

                }

            }
        });
        rGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton=findViewById(checkedId);
                gender=radioSexButton.getText().toString();
            }
        });

        radioGroupDoctorType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioDoctorButton=findViewById(checkedId);
                doctor_type=radioDoctorButton.getText().toString();
                if(doctor_type.equalsIgnoreCase("Admin Doctor"))
                {
                    is_super_admin="1";
                }
                else
                {
                    is_super_admin="0";
                }
            }
        });

        linearLayoutDepartmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (NetworkUtils.isNetworkAvailable(AddDoctorActivity.this))
                      callListDepartment();
                    else
                        NetworkUtils.isNetworkNotAvailable(AddDoctorActivity.this);


            }
        });

        btnOkDialogDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchDepartment.setText("");
                selectDepartmentDialog.dismiss();
               /* if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);
                    }
                    linearService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearService.setVisibility(View.GONE);
                }
*/
            }
        });

        btnCancelDialogDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  serviceModel = null;
                linearService.setVisibility(View.GONE);*/
                editSearchDepartment.setText("");
                selectDepartmentDialog.dismiss();
            }
        });

        editSearchDepartment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                departmentListAdapter.getFilter().filter(editSearchDepartment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void addNewDoctor()
    {

        Call<SuccessModel> call = apiInterface.addNewDoctor(super_doctor_id,user_id, doctor_name, email,phone, address,profile,department_id,is_super_admin,gender);


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
                                successMessageDialog.showDialog("New Doctor Add Sucessfully");
                            } else if (code.equalsIgnoreCase("2")) {
                                errorMessageDialog.showDialog("Doctor Already Exist");
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


    private void callListDepartment() {

        Call<SuccessModel> call = apiInterface.listDepartment(user_id);

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
                            departmentModelArrayList.clear();
                            selectDepartmentDialog.show();

                            if (code.equalsIgnoreCase("1")) {

                                departmentModelArrayList=successModel.getDepartmentModelArrayList();

                                if(departmentModelArrayList.size()!=0) {

                                    departmentListAdapter=new DepartmentListAdapter(getApplicationContext(),departmentModelArrayList,true, AddDoctorActivity.this);
                                    recyclerDialogDepartment.setAdapter(departmentListAdapter);
                                    recyclerDialogDepartment.addItemDecoration(new DividerItemDecoration(AddDoctorActivity.this, DividerItemDecoration.VERTICAL));
                                    recyclerDialogDepartment.setHasFixedSize(true);
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("List is Empty");
                                }

                            } else {
                                errorMessageDialog.showDialog("Response is null");
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
        doctor_name = textDoctorName.getText().toString().trim();
        email = textEmail.getText().toString().trim();
        address = textAddress.getText().toString().trim();
        profile = textProfile.getText().toString().trim();
        department = textDepartment.getText().toString().trim();
        phone = textPhone.getText().toString().trim();


        if(doctor_name.equalsIgnoreCase(""))
        {
            errorMessageDialog.showDialog("Name Can't Empty");
            return false;
        }else if (email.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Email ID Can't Empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()) {
            errorMessageDialog.showDialog("Enter valid Email");
            return false;
        }else if (phone.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Phone Number Can't Empty");
            return false;
        } else if (phone.length() != 10) {
            errorMessageDialog.showDialog("InValid Phone Number");
            return false;
        } else if (address.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Address Can't Empty");
            return false;
        } else if (profile.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Profile Can't Empty");
            return false;
        }  else if (department_id.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog(" Select Department ");
            return false;
        } else if (doctor_type==null) {
            errorMessageDialog.showDialog(" Select Doctor type ");
            return false;
        } else if (gender==null) {
            errorMessageDialog.showDialog(" Select Gender ");
            return false;
        }
        return true;
    }

    @Override
    public void getDepartment(DepartmentModel departmentModel) {
        linearLayoutDepartment.setVisibility(View.VISIBLE);
        textDepartment.setText(departmentModel.getName());
        department_id=departmentModel.getDepartment_id();
    }
}
