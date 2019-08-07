package com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.ShowProgress;
import com.nikvay.saipoojadoctor.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDepartmentActivity extends AppCompatActivity {

   private ApiInterface apiInterface;
    private ShowProgress showProgress;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String super_doctor_id,user_id,TAG = getClass().getSimpleName(),name,description;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private Button btn_department_save;
    private TextInputEditText edt_desc_add_message,edt_department_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        find_All_Ids();
        events();
    }

    private void find_All_Ids()
    {

        btn_department_save = findViewById(R.id.btn_department_save);
        edt_department_name = findViewById(R.id.edt_department_name);
        edt_desc_add_message = findViewById(R.id.edt_desc_add_message);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        doctorModelArrayList = SharedUtils.getUserDetails(AddDepartmentActivity.this);
        super_doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        errorMessageDialog = new ErrorMessageDialog(AddDepartmentActivity.this);
        successMessageDialog = new SuccessMessageDialog(AddDepartmentActivity.this);
    }
    private void events()
    {

        btn_department_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation())
                {
                    if (NetworkUtils.isNetworkAvailable(AddDepartmentActivity.this)) {
                        callAddDepartmentList();
                    } else
                        NetworkUtils.isNetworkNotAvailable(AddDepartmentActivity.this);
                }
            }
        });

    }

    private void callAddDepartmentList()
    {
        Call<SuccessModel> call = apiInterface.addNewDepartment(super_doctor_id, user_id, name, description);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel departmentModel = response.body();
                        String message = null, errorCode = null;

                        if (departmentModel != null) {
                            message = departmentModel.getMsg();
                            errorCode = departmentModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Department Add successfully ");
                            }  else if(errorCode.equalsIgnoreCase("2")){
                                errorMessageDialog.showDialog("Department Already Exits ");
                            }
                            else
                            {
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

            }
        });

    }

    private boolean doValidation() {
        name = edt_department_name.getText().toString().trim();
        //  serviceTitle = serviceTitle_name.getText().toString().trim();
        description = edt_desc_add_message.getText().toString().trim();

        if (name.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("name  Can't Be Empty");
            return false;
        } else if (description.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Description Can't Be Empty");
            return false;
        }
        return  true;
    }
}
