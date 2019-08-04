package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PaymentDetailModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.PaymentAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistoryActivity extends AppCompatActivity {

    private  RecyclerView recyclerView_payment;
    private ApiInterface apiInterface;
    private  String user_id,doctor_id,TAG = getClass().getSimpleName();
    private  ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private  ArrayList<PaymentDetailModel>paymentDetailModelArrayList = new ArrayList<>();
    private PaymentAdapter paymentAdapter;
    private  ImageView iv_empty_list,iv_back,iv_date;
    private ShowProgress showProgress;
   // private  EditText edt_search_service;
    private  String date,patient_id,mTitle="Payment History";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        find_All_Id();
        event();
    }

    private void find_All_Id() {

        recyclerView_payment = findViewById(R.id.recyclerView_payment);
       // edt_search_service = findViewById(R.id.edt_search_service);
        iv_date = findViewById(R.id.iv_date);
        iv_back = findViewById(R.id.iv_back);

        errorMessageDialog = new ErrorMessageDialog(PaymentHistoryActivity.this);
        successMessageDialog = new SuccessMessageDialog(PaymentHistoryActivity.this);
        showProgress=new ShowProgress(PaymentHistoryActivity.this);

        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            patient_id =  bundle.getString(StaticContent.IntentKey.PATIENT_DETAIL);

        }



        doctorModelArrayList = SharedUtils.getUserDetails(PaymentHistoryActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PaymentHistoryActivity.this);
        recyclerView_payment.setLayoutManager(linearLayoutManager);
        recyclerView_payment.setHasFixedSize(true);



        if (NetworkUtils.isNetworkAvailable(PaymentHistoryActivity.this))
        {
            date ="";
            paymentListCall(doctor_id,user_id,patient_id,date);
        }
        else
            NetworkUtils.isNetworkNotAvailable(PaymentHistoryActivity.this);
    }

    private void event() {

   /*     edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                paymentAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(day, month, year);

                        long dateAttendance = chosenDate.toMillis(true);
                        strDate = DateFormat.format("yyyy-MM-dd", dateAttendance);

                        date = (String) strDate;

                        if (NetworkUtils.isNetworkAvailable(PaymentHistoryActivity.this))
                            paymentListCall(doctor_id,user_id,patient_id,date);
                        else
                            NetworkUtils.isNetworkNotAvailable(PaymentHistoryActivity.this);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

    }



    private void paymentListCall(String doctor_id, String user_id,String patient_id, String date)
    {
        if (patient_id==null)
        {
            patient_id=getIntent().getStringExtra("patient_id");
        }
        Toast.makeText(this, date+""+patient_id+""+doctor_id, Toast.LENGTH_SHORT).show();
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientPaymentDetails(doctor_id,user_id,patient_id, date);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
            {
                showProgress.dismissDialog();

                String str_response = new Gson().toJson(response.body());
                Log.e(""+TAG,"Response>>>>>>>>>>"+str_response);

                try
                {
                    if (response.isSuccessful())
                    {
                        SuccessModel paymentModel = response.body();
                        String errorCode = null,msg= null;

                        if (paymentModel != null)
                        {
                            errorCode =paymentModel.getError_code();
                            msg = paymentModel.getMsg();
                            paymentDetailModelArrayList.clear();
                            if (errorCode.equalsIgnoreCase("1"))
                            {
                                paymentDetailModelArrayList = paymentModel.getPaymentDetailModelArrayList();

                                if (paymentDetailModelArrayList.size()!= 0)
                                {
                                    paymentAdapter = new PaymentAdapter(PaymentHistoryActivity.this,paymentDetailModelArrayList);
                                    recyclerView_payment.setAdapter(paymentAdapter);
                                    paymentAdapter.notifyDataSetChanged();

                                    recyclerView_payment.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerView_payment.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();

            }
        });
    }



}
