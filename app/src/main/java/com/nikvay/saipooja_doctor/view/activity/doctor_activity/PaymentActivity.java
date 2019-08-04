package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.interfaceutils.SelectPatientInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.ServiceModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.MyPatientDialogAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.MyServiceDialogAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements SelectPatientInterface {

    ImageView iv_close,imgSelectPatient,imgHospitalService;
    TextView textSelectPatientList,textSelectService;
    AutoCompleteTextView textHospitalPatient,textServiceName,textServiceCost;
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    private int year, month, day;


    Spinner spinnePaymentType;
    Button btnOkDialogSC,btnCancelDialogSC,btnOkDialogService,btnCancelDialogService,submitBtnPayment;
    EditText editSearchCPatient,editSearchService, textcomment_payment,texthospital_charges;
    SelectPatientInterface selectPatientInterface;
    public static PatientModel patientModel = null;
    public static ServiceModel serviceModel = null;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
    RecyclerView recyclerDialogSC,recyclerDialogService;
    LinearLayout linearPatient,linearService;

    private Dialog selectPatientDialog, selectServiceDialog;

    private ApiInterface apiInterface;

    String TAG = getClass().getSimpleName(),date;

    ShowProgress showProgress;

    MyPatientDialogAdapter myPatientDialogAdapter;
    MyServiceDialogAdapter myServiceDialogAdapter;



    private String doctor_id,user_id,service_id = null,patient_id,hospital_charges,mode_of_payment,comment;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        selectPatientDialog = new Dialog(this);
        selectPatientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        selectServiceDialog = new Dialog(this);
        selectServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        selectPatientDialog.setContentView(R.layout.dialog_select_patient);
        selectServiceDialog.setContentView(R.layout.dialog_select_service);


        iv_close=findViewById(R.id.iv_close);
        submitBtnPayment=findViewById(R.id.submitBtnPayment);
        imgHospitalService = findViewById(R.id.imgHospitalService);
        texthospital_charges = findViewById(R.id.texthospital_charges);
        textcomment_payment = findViewById(R.id.textcomment_payment);
        spinnePaymentType = findViewById(R.id.spinnePaymentType);
        textHospitalPatient = findViewById(R.id.textHospitalPatient);
        showProgress = new ShowProgress(PaymentActivity.this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

                // Patient Dialog Configuration
        imgSelectPatient =  findViewById(R.id.imgSelectPatient);
        linearPatient =  findViewById(R.id.linearPatient);
        btnOkDialogSC = selectPatientDialog.findViewById(R.id.btnOkDialogSC);
        btnCancelDialogSC =  selectPatientDialog.findViewById(R.id.btnCancelDialogSC);
        editSearchCPatient =  selectPatientDialog.findViewById(R.id.editSearchCPatient);
        selectPatientDialog.setCancelable(false);
        recyclerDialogSC = selectPatientDialog.findViewById(R.id.recyclerDialogSC);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerDialogSC.setLayoutManager(manager);



        // Service Dialog Configuration
        linearService =  findViewById(R.id.linearService);
        imgHospitalService =  findViewById(R.id.imgHospitalService);
        textServiceName =  findViewById(R.id.textServiceName);
        textServiceCost =  findViewById(R.id.textServiceCost);

        btnOkDialogService = selectServiceDialog.findViewById(R.id.btnOkDialogService);
        btnCancelDialogService =  selectServiceDialog.findViewById(R.id.btnCancelDialogService);
        editSearchService =  selectServiceDialog.findViewById(R.id.editSearchService);
        selectServiceDialog.setCancelable(false);
        recyclerDialogService = selectServiceDialog.findViewById(R.id.recyclerDialogService);
        LinearLayoutManager layoutManagerService = new LinearLayoutManager(getApplicationContext());
        recyclerDialogService.setLayoutManager(layoutManagerService);


        errorMessageDialog = new ErrorMessageDialog(PaymentActivity.this);
        successMessageDialog = new SuccessMessageDialog(PaymentActivity.this);

        doctorModelArrayList = SharedUtils.getUserDetails(PaymentActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

    }



    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int dayOfMonth) {


                    CharSequence strDate = null;
                    Time chosenDate = new Time();
                    chosenDate.set(dayOfMonth, month, year);

                    long dateAppointment = chosenDate.toMillis(true);
                    strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
                    date= (String) strDate;

                    if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
                    {

                    }
                    //    preScriptionListCall(patient_id,date);
                    else
                    {

                    }
                    //    NetworkUtils.isNetworkNotAvailable(PreSriptionActivity.this);

                }
            };


    private void events() {

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitBtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {

                    if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
                    {
                        callListPayment(doctor_id,user_id,service_id,patient_id,mode_of_payment,comment,hospital_charges);

                    }
                    else
                        NetworkUtils.isNetworkNotAvailable(PaymentActivity.this);

                }
            }
        });

        imgSelectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
                {
                    callListPatient();

                }
                else
                    NetworkUtils.isNetworkNotAvailable(PaymentActivity.this);
            }
        });
        btnOkDialogSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCPatient.setText("");
                selectPatientDialog.dismiss();
                if (patientModel != null) {
                    setPatientData();
                    if (patientModelArrayList.size() > 0) {
                        /*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*/
                    }
                    linearPatient.setVisibility(View.VISIBLE);
                } else {
                 //   clearDiscountData();
                    linearPatient.setVisibility(View.GONE);
                }
            }
        });

        btnCancelDialogSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientModel = null;

                linearPatient.setVisibility(View.GONE);
                editSearchCPatient.setText("");
                selectPatientDialog.dismiss();
            }
        });

        imgHospitalService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
                    callServiceList();
                else
                    NetworkUtils.isNetworkNotAvailable(PaymentActivity.this);


            }
        });

        btnOkDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchService.setText("");
                selectServiceDialog.dismiss();
                if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        /*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*/
                    }
                    linearService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearService.setVisibility(View.GONE);
                }
                
            }
        });

        btnCancelDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceModel = null;
                linearService.setVisibility(View.GONE);
                editSearchService.setText("");
                selectServiceDialog.dismiss();
            }
        });
        
        spinnePaymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 mode_of_payment = spinnePaymentType.getSelectedItem().toString();
                Log.e(""+TAG,"Response >>>>>>>"+ mode_of_payment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        editSearchCPatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myPatientDialogAdapter.getFilter().filter(editSearchCPatient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editSearchService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myServiceDialogAdapter.getFilter().filter(editSearchService.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void callListPayment(String doctor_id, String user_id, String service_id, String patient_id,
                                 String mode_of_payment, String comment, String hospital_charges)
    {
        Call<SuccessModel>call =apiInterface.paymentList(doctor_id,user_id,service_id,patient_id,hospital_charges,mode_of_payment,comment);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response  = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);
                try {
                    if (response.isSuccessful()) {
                        SuccessModel serviceModel = response.body();
                        String message = null, errorCode = null;

                        if (serviceModel != null) {
                            message = serviceModel.getMsg();
                            errorCode = serviceModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Payment Add succesfully ");
                                //finish();
                            } else if (errorCode.equalsIgnoreCase("3")) {
                                errorMessageDialog.showDialog("Payment is Already Sucessfully ");
                            } else {
                                errorMessageDialog.showDialog("Parameter is missing");
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

    private boolean isValid()
    {
        comment = textcomment_payment.getText().toString().trim();
        hospital_charges = texthospital_charges.getText().toString().trim();

        if (patientModel == null) {
            Toast.makeText(getApplicationContext(), "Select Patient", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinnePaymentType.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Select Payment type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (comment.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("comment  Can't Be Empty");
            return false;
        } else if (hospital_charges.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("hospital_charges Can't Be Empty");
            return false;
        }

        return true;
    }


    private void setServiceData() {

        service_id = serviceModel.getService_id();
        textServiceName.setText(serviceModel.getS_name());
        textServiceCost.setText(serviceModel.getService_cost());
    }

    private void callServiceList()
    {
        Call<SuccessModel> call = apiInterface.serviceList(doctor_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        serviceModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                serviceModelArrayList = successModel.getServiceModelArrayList();

                                if (serviceModelArrayList.size() != 0) {

                                    myServiceDialogAdapter = new MyServiceDialogAdapter(getApplicationContext(), serviceModelArrayList, true,  selectPatientInterface);

                                    selectServiceDialog.show();
                                    Window window = selectServiceDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogService.setAdapter(myServiceDialogAdapter);
                                    myServiceDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogService.setVisibility(View.VISIBLE);
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

    private void setPatientData()
    {
        textHospitalPatient.setText(patientModel.getName());
        patient_id = patientModel.getPatient_id();
    }


    private void callListPatient()
        {
      //  showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientList(doctor_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        patientModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                patientModelArrayList = successModel.getPatientModelArrayList();

                                if (patientModelArrayList.size() != 0) {

                                    myPatientDialogAdapter = new MyPatientDialogAdapter(getApplicationContext(), patientModelArrayList, true,  selectPatientInterface);

                                    selectPatientDialog.show();
                                    Window window = selectPatientDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogSC.setAdapter(myPatientDialogAdapter);
                                    myPatientDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogSC.setVisibility(View.VISIBLE);
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


    @Override
    public void getPatientDetail(PatientModel patientModel) {
        
    }

    @Override
    public void getServiceDetail(ServiceModel serviceModel) {

    }
}
