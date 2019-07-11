package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectDoctorInterface;
import com.nikvay.doctorapplication.model.DoctorListModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.AppointmentDialog;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.DoctorListAdapter;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.MyDoctorDialogAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceActivity extends AppCompatActivity  implements SelectDoctorInterface {


    private Button btn_service_save, btnOkDialogDoctor, btnCancelDialogDoctor;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String TAG = getClass().getSimpleName(), doctorName, user_id, service_desc, service_time, service_cost, service_name;
    private Dialog selectDoctorDialog;
    private ArrayList<DoctorListModel> doctorListModelArrayList=new ArrayList<>();
    private ArrayList<DoctorListModel> doctorListModelArrayListSelected=new ArrayList<>();

    private MyDoctorDialogAdapter myDoctorDialogAdapter, myDoctorListDialogAdapter;
    public DoctorListModel doctorListModel = null;
    private RecyclerView recyclerDialogDoctor, recyclerViewDoctorList;
    private AutoCompleteTextView txtDoctorName;
    private LinearLayout linearDoctor;
    private ImageView iv_doctorName,iv_close;
    private TextInputEditText edt_service_name, edt_service_cost_add, edt_service_time_add, edt_service_desc;
    private AppointmentDialog appointmentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        find_All_IDs();
        events();

    }

    private void events() {

        btn_service_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    callAddNewService(user_id, service_name, service_desc, service_cost, service_time);
                }
            }
        });
        iv_doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDoctorList();
            }
        });
        btnOkDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDoctorDialog.dismiss();
            }
        });
        btnCancelDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorListModel = null;
                linearDoctor.setVisibility(View.GONE);
                selectDoctorDialog.dismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void find_All_IDs() {
        linearDoctor = findViewById(R.id.linearDoctor);
        txtDoctorName = findViewById(R.id.txtDoctorName);
        iv_doctorName = findViewById(R.id.iv_doctorName);
        edt_service_name = findViewById(R.id.edt_service_name);
        edt_service_cost_add = findViewById(R.id.edt_service_cost_add);
        edt_service_time_add = findViewById(R.id.edt_service_time_add);
        edt_service_desc = findViewById(R.id.edt_service_desc);
        btn_service_save = findViewById(R.id.btn_service_save);
        iv_close = findViewById(R.id.iv_close);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        doctorModelArrayList = SharedUtils.getUserDetails(AddServiceActivity.this);
        user_id = doctorModelArrayList.get(0).getUser_id();

        selectDoctorDialog = new Dialog(AddServiceActivity.this);
        selectDoctorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectDoctorDialog.setContentView(R.layout.dialog_select_doctor);
        btnOkDialogDoctor = selectDoctorDialog.findViewById(R.id.btnOkDialogDoctor);
        btnCancelDialogDoctor = selectDoctorDialog.findViewById(R.id.btnCancelDialogDoctor);

        selectDoctorDialog.setCancelable(false);
        recyclerDialogDoctor = selectDoctorDialog.findViewById(R.id.recyclerDialogDoctor);
        recyclerViewDoctorList = findViewById(R.id.recyclerViewDoctorList);
        LinearLayoutManager layoutManagerService = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManagerServiceSelect = new LinearLayoutManager(getApplicationContext());
        recyclerDialogDoctor.setLayoutManager(layoutManagerService);
        recyclerViewDoctorList.setLayoutManager(layoutManagerServiceSelect);

        errorMessageDialog = new ErrorMessageDialog(AddServiceActivity.this);
        successMessageDialog = new SuccessMessageDialog(AddServiceActivity.this);
        appointmentDialog = new AppointmentDialog(AddServiceActivity.this);
    }


    private void callDoctorList() {
        Call<SuccessModel> call = apiInterface.doctorList();

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                //  showProgress.dismissDialog();
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
                                doctorListModelArrayList.clear();
                                doctorListModelArrayList = successModel.getDoctorListModelArrayList();

                                if (doctorListModelArrayList.size() != 0) {
                                    Collections.reverse(doctorListModelArrayList);
                                    myDoctorDialogAdapter = new MyDoctorDialogAdapter(AddServiceActivity.this, doctorListModelArrayList, true, AddServiceActivity.this);
                                   // doctorListModelArrayListSelected.clear();

                                    selectDoctorDialog.show();
                                    Window window = selectDoctorDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogDoctor.setAdapter(myDoctorDialogAdapter);
                                    myDoctorDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogDoctor.setVisibility(View.VISIBLE);
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

            }
        });
    }

    private void callAddNewService(String user_id, String s_name, String description,
                                   String service_cost, String service_time) {
        String doctor_id = String.valueOf(getDoctorIdArray());

        Call<SuccessModel> call = apiInterface.addNewAdminService(doctor_id, user_id, s_name,
                service_cost, service_time, description);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {

                        SuccessModel adminServiceModel = response.body();
                        String message = null, code = null;

                        if (adminServiceModel != null) {
                            message = adminServiceModel.getMsg();
                            code = adminServiceModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                successMessageDialog.showDialog("Add Service Successfully !");

                            } else {
                                errorMessageDialog.showDialog("Wrong Service");
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

    private boolean isValid() {
        service_name = edt_service_name.getText().toString().trim();
        service_cost = edt_service_cost_add.getText().toString().trim();
        service_time = edt_service_time_add.getText().toString().trim();
        service_desc = edt_service_desc.getText().toString().trim();

        if (service_name.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Service Name ID Can't Be Empty");
            edt_service_name.requestFocus();
            return false;
        } else if (service_cost.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Service_cost ID Can't Be Empty");
            edt_service_cost_add.requestFocus();
            return false;
        }

        return true;
    }



    private JSONArray getDoctorIdArray() {
        List<String> doctor_id_list = new ArrayList<>();
        for (int i = 0; i < doctorListModelArrayListSelected.size(); i++) {
            doctor_id_list.add(doctorListModelArrayListSelected.get(i).getDoctor_id());
        }
        JSONArray ppJsonArray = new JSONArray(doctor_id_list);
        return ppJsonArray;
    }


    @Override
    public void getDoctor(DoctorListModel doctorListModel) {

        doctorListModelArrayListSelected.add(doctorListModel);
        if (doctorListModelArrayListSelected.size() != 0) {
            myDoctorListDialogAdapter = new MyDoctorDialogAdapter(AddServiceActivity.this, doctorListModelArrayListSelected, false,AddServiceActivity.this);
            recyclerViewDoctorList.setAdapter(myDoctorListDialogAdapter);
            recyclerViewDoctorList.setVisibility(View.VISIBLE);

        } else {
            myDoctorListDialogAdapter.notifyDataSetChanged();
        }

    }
}
