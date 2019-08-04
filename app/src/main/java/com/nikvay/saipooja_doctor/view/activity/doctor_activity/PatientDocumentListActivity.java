package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.apicallcommon.BaseApi;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PrescriptionDocumentModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.PrescriptionDocumentAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDocumentListActivity extends AppCompatActivity {

    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String doctor_id,user_id, TAG = getClass().getSimpleName(),patient_id,document_url;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView_prescription_document;
    private ImageView iv_list_empty, iv_back;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ShowProgress showProgress;
    private ArrayList<PrescriptionDocumentModel> prescriptionDocumentModelArrayList=new ArrayList<>();
    private PrescriptionDocumentAdapter prescriptionDocumentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_document_list);

        find_All_Id();
        event();
    }

    private void find_All_Id() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerView_prescription_document=findViewById(R.id.recyclerView_prescription_document);
        iv_list_empty=findViewById(R.id.iv_list_empty);
        iv_back=findViewById(R.id.iv_back);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PatientDocumentListActivity.this);
        recyclerView_prescription_document.setLayoutManager(linearLayoutManager);
        recyclerView_prescription_document.setHasFixedSize(true);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            patient_id =bundle.getString(StaticContent.IntentKey.PATIENT_DETAIL);
        }


        errorMessageDialog = new ErrorMessageDialog(PatientDocumentListActivity.this);
        successMessageDialog = new SuccessMessageDialog(PatientDocumentListActivity.this);
        showProgress=new ShowProgress(PatientDocumentListActivity.this);
        doctorModelArrayList = SharedUtils.getUserDetails(PatientDocumentListActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        if (NetworkUtils.isNetworkAvailable(PatientDocumentListActivity.this))
        {
            callListDocument();

        }
        else
            NetworkUtils.isNetworkNotAvailable(PatientDocumentListActivity.this);



    }


    private void event() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
    }

    private void callListDocument() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.listPrescriptionDocument(doctor_id,user_id,patient_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        prescriptionDocumentModelArrayList.clear();
                        String message = null, code = null,folder_path;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            folder_path=successModel.getImg_base_url();

                            if (code.equalsIgnoreCase("1")) {

                                prescriptionDocumentModelArrayList=successModel.getPrescriptionDocumentModelArrayList();
                                document_url= BaseApi.BASE_URL+folder_path;
                                if(prescriptionDocumentModelArrayList.size()!=0) {
                                    Collections.reverse(prescriptionDocumentModelArrayList);
                                    prescriptionDocumentAdapter = new PrescriptionDocumentAdapter(PatientDocumentListActivity.this, prescriptionDocumentModelArrayList,document_url);
                                    recyclerView_prescription_document.setAdapter(prescriptionDocumentAdapter);
                                    iv_list_empty.setVisibility(View.GONE);
                                    prescriptionDocumentAdapter.notifyDataSetChanged();
                                    // recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));
                                }
                                else
                                {
                                    iv_list_empty.setVisibility(View.VISIBLE);
                                    prescriptionDocumentAdapter.notifyDataSetChanged();
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

}
