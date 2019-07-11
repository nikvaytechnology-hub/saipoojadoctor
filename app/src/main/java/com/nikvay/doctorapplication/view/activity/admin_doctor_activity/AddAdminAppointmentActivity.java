package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.app.Dialog;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectAllPatientInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectDoctorInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectPatientInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectServiceInterface;
import com.nikvay.doctorapplication.interfaceutils.SelectTimeSlotInterface;
import com.nikvay.doctorapplication.model.DoctorListModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.SelectDateTimeModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.AppointmentDialog;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.SuccessDialogClosed;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.utils.SuccessMessageDoctorDialog;
import com.nikvay.doctorapplication.view.activity.doctor_activity.DateTimeSelectActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.NewAppointmentActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.PaymentActivity;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.AllPatientListAdapter;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.DoctorListAdapter;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.SelectDoctorServiceAdapter;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.MyServiceDialogAdapter;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.SelectDateTimeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.internal.util.LinkedArrayList;

public class AddAdminAppointmentActivity extends AppCompatActivity implements SelectDoctorInterface, SelectServiceInterface, SelectAllPatientInterface, SelectTimeSlotInterface, SuccessDialogClosed {

    //Date And Time Slot List
    private ImageView iv_close;
    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList = new ArrayList<>();
    private CalendarView calendarView;
    private TextView textSlotNotFound, textcommentName;
    private String date;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String doctor_id = "", service_id = "", patient_id = "", label = "1", notification_type = "1", time = "", comment = "", user_id, super_admin_id, TAG = getClass().getSimpleName();
    private SuccessMessageDialog successMessageDialog;
    private TextView textPending, textConfirm, textLabelName, textTimeSlot;
    private Button btnDone;
    private AppointmentDialog appointmentDialog;
    private SuccessMessageDoctorDialog successMessageDoctorDialog;


    //Select Doctor
    private Dialog selectDoctorDialog;
    private EditText editSearchDoctor;
    private Button btnCancelDialogDoctor, btnOkDialogDoctor;
    private RecyclerView recyclerDialogDoctor;
    private ArrayList<DoctorListModel> doctorListModelArrayList = new ArrayList<>();
    ShowProgress showProgress;
    private DoctorListAdapter doctorListAdapter;
    private LinearLayout linearLayoutDoctorList;
    private TextView textDoctor;
    private LinearLayout linearLayoutDoctor;


    //Select Service
    private Dialog selectServiceDialog;
    private EditText editSearchService;
    private Button btnCancelDialogService, btnOkDialogService;
    private RecyclerView recyclerDialogService;
    ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
    SelectDoctorServiceAdapter selectDoctorServiceAdapter;
    private LinearLayout linearLayoutServiceList;
    private TextView textService;
    private LinearLayout linearLayoutService;


    //select Patient
    private Dialog selectPatientDialog;
    private EditText editSearchPatient;
    private Button btnCancelDialogPatient, btnOkDialogPatient;
    private RecyclerView recyclerDialogPatient;
    private LinearLayout linearLayoutPatientList;
    private TextView textPatient;
    private LinearLayout linearLayoutPatient;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    private AllPatientListAdapter allPatientListAdapter;


    //Select Time Slot
    private LinearLayout linearLayoutTimeSlot;

    //Select Label
    private RelativeLayout relativeLayoutLabelHide, relativeLayoutComments, relativeLayoutCommentsHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin_appointment);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        recyclerViewTime = findViewById(R.id.recyclerViewTime);
        iv_close = findViewById(R.id.iv_close);
        calendarView = findViewById(R.id.calendarView);
        textSlotNotFound = findViewById(R.id.textSlotNotFound);
        textPending = findViewById(R.id.textPending);
        textConfirm = findViewById(R.id.textConfirm);
        textLabelName = findViewById(R.id.textLabelName);
        textTimeSlot = findViewById(R.id.textTimeSlot);
        btnDone = findViewById(R.id.btnDone);
        relativeLayoutComments = findViewById(R.id.relativeLayoutComments);
        relativeLayoutCommentsHide = findViewById(R.id.relativeLayoutCommentsHide);
        textcommentName = findViewById(R.id.textcommentName);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(AddAdminAppointmentActivity.this, 3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        recyclerViewTime.hasFixedSize();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        errorMessageDialog = new ErrorMessageDialog(AddAdminAppointmentActivity.this);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        doctorModelArrayList = SharedUtils.getUserDetails(AddAdminAppointmentActivity.this);
        super_admin_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        //Select Doctor Start

        selectDoctorDialog = new Dialog(this);
        selectDoctorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectDoctorDialog.setContentView(R.layout.dialog_select_doctor);

        btnCancelDialogDoctor = selectDoctorDialog.findViewById(R.id.btnCancelDialogDoctor);
        btnOkDialogDoctor = selectDoctorDialog.findViewById(R.id.btnOkDialogDoctor);
        editSearchDoctor = selectDoctorDialog.findViewById(R.id.editSearchDoctor);
        selectDoctorDialog.setCancelable(false);
        recyclerDialogDoctor = selectDoctorDialog.findViewById(R.id.recyclerDialogDoctor);

        linearLayoutDoctorList = findViewById(R.id.linearLayoutDoctorList);
        textDoctor = findViewById(R.id.textDoctor);
        linearLayoutDoctor = findViewById(R.id.linearLayoutDoctor);

        LinearLayoutManager linearLayoutManagerDoctor = new LinearLayoutManager(getApplicationContext());
        recyclerDialogDoctor.setLayoutManager(linearLayoutManagerDoctor);


        Window window = selectDoctorDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //select Doctor End


        //Select Service Start

        selectServiceDialog = new Dialog(this);
        selectServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectServiceDialog.setContentView(R.layout.dialog_select_service);

        btnCancelDialogService = selectServiceDialog.findViewById(R.id.btnCancelDialogService);
        btnOkDialogService = selectServiceDialog.findViewById(R.id.btnOkDialogService);
        editSearchService = selectServiceDialog.findViewById(R.id.editSearchService);
        selectServiceDialog.setCancelable(false);
        recyclerDialogService = selectServiceDialog.findViewById(R.id.recyclerDialogService);

        linearLayoutServiceList = findViewById(R.id.linearLayoutServiceList);
        textService = findViewById(R.id.textService);
        linearLayoutService = findViewById(R.id.linearLayoutService);

        LinearLayoutManager linearLayoutManagerService = new LinearLayoutManager(getApplicationContext());
        recyclerDialogService.setLayoutManager(linearLayoutManagerService);

        Window windowService = selectServiceDialog.getWindow();
        windowService.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //select Service End


        //Select Patient Start

        selectPatientDialog = new Dialog(this);
        selectPatientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPatientDialog.setContentView(R.layout.dialog_select_patient);

        btnCancelDialogPatient = selectPatientDialog.findViewById(R.id.btnCancelDialogSC);
        btnOkDialogPatient = selectPatientDialog.findViewById(R.id.btnOkDialogSC);
        editSearchPatient = selectPatientDialog.findViewById(R.id.editSearchCPatient);
        selectPatientDialog.setCancelable(false);
        recyclerDialogPatient = selectPatientDialog.findViewById(R.id.recyclerDialogSC);

        linearLayoutPatientList = findViewById(R.id.linearLayoutPatientList);
        textPatient = findViewById(R.id.textPatient);
        linearLayoutPatient = findViewById(R.id.linearLayoutPatient);

        LinearLayoutManager linearLayoutManagerPatient = new LinearLayoutManager(getApplicationContext());
        recyclerDialogPatient.setLayoutManager(linearLayoutManagerPatient);


        Window windowPatient = selectPatientDialog.getWindow();
        windowPatient.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //select Patient End


        //Select Time Slot
        linearLayoutTimeSlot = findViewById(R.id.linearLayoutTimeSlot);

        //Select Time Slot
        relativeLayoutLabelHide = findViewById(R.id.relativeLayoutLabelHide);


        errorMessageDialog = new ErrorMessageDialog(AddAdminAppointmentActivity.this);
        successMessageDialog = new SuccessMessageDialog(AddAdminAppointmentActivity.this);
        showProgress = new ShowProgress(AddAdminAppointmentActivity.this);
        appointmentDialog = new AppointmentDialog(AddAdminAppointmentActivity.this);
        successMessageDoctorDialog = new SuccessMessageDoctorDialog(AddAdminAppointmentActivity.this, true);
        textLabelName.setTextColor(getResources().getColor(R.color.confirm));
        textLabelName.setText("Confirm");


    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                CharSequence strDate = null;
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, month, year);

                long dateAppointment = chosenDate.toMillis(true);
                strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
                date = (String) strDate;

                if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
                    callTimeSlot();
                else
                    NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);
                // Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });


        linearLayoutDoctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
                    callDoctorList();
                else
                    NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);

            }
        });


        linearLayoutServiceList.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!doctor_id.equalsIgnoreCase("")) {
                    if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
                        callServiceList();
                    else
                        NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);

                } else {
                    errorMessageDialog.showDialog("Please Select Doctor First");
                }

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
                doctor_id = "";
                linearLayoutDoctor.setVisibility(View.GONE);
                editSearchDoctor.setText("");
                selectDoctorDialog.dismiss();
            }
        });


        btnOkDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchService.setText("");
                selectServiceDialog.dismiss();
                /*if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        //editDiscountQuantity.setText("");
                       // setDiscountData(mTotalAmount, mDiscountLimit, true);
                    }
                    linearLayoutService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearLayoutService.setVisibility(View.GONE);
                }*/

            }
        });

        btnCancelDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_id = "";
                linearLayoutService.setVisibility(View.GONE);
                editSearchService.setText("");
                selectServiceDialog.dismiss();
            }
        });


        btnOkDialogPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchPatient.setText("");
                selectPatientDialog.dismiss();
                /*if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        //editDiscountQuantity.setText("");
                       // setDiscountData(mTotalAmount, mDiscountLimit, true);
                    }
                    linearLayoutService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearLayoutService.setVisibility(View.GONE);
                }*/

            }
        });

        btnCancelDialogPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient_id = "";
                linearLayoutPatient.setVisibility(View.GONE);

                editSearchPatient.setText("");
                selectPatientDialog.dismiss();
            }
        });


        linearLayoutPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
                    callListPatient();
                else
                    NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);


            }
        });

        textPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label = "0";
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.black));

            }
        });
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label = "1";
                textLabelName.setText("Confirm");
                textLabelName.setTextColor(getResources().getColor(R.color.confirm));
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validation()) {
                    if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
                        callAddAppoitment();
                    else
                        NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);
                }

            }
        });


        relativeLayoutComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog();
            }
        });


        //Search Method Start

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

        editSearchService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                selectDoctorServiceAdapter.getFilter().filter(editSearchService.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editSearchPatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                allPatientListAdapter.getFilter().filter(editSearchPatient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Search Method End


    }

    private boolean validation() {
        if (doctor_id.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Select Doctor");
            return false;
        } else if (service_id.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Select Service");
            return false;
        } else if (patient_id.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Patient Time");
            return false;
        } else if (time.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Select Time");
            return false;
        }
        return true;
    }


    //===================All API Call========================================

    //Date Time Slot API Call
    private void callTimeSlot() {


        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(date, doctor_id, user_id);


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

                            selectDateTimeModelArrayList.clear();

                            if (code.equalsIgnoreCase("1")) {
                                selectDateTimeModelArrayList = successModel.getSelectDateTimeModelArrayList();
                                if (selectDateTimeModelArrayList.size() != 0) {
                                    selectDateTimeAdapter = new SelectDateTimeAdapter(AddAdminAppointmentActivity.this, selectDateTimeModelArrayList, true, AddAdminAppointmentActivity.this);
                                    recyclerViewTime.setAdapter(selectDateTimeAdapter);
                                    textSlotNotFound.setVisibility(View.GONE);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                } else {
                                    textSlotNotFound.setVisibility(View.VISIBLE);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                }

                            } else {
                                errorMessageDialog.showDialog("List Not Found");
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


    //Doctor List API Call
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
                                    doctorListAdapter = new DoctorListAdapter(AddAdminAppointmentActivity.this, doctorListModelArrayList, true, AddAdminAppointmentActivity.this);
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

    //Service List API call
    private void callServiceList() {
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

                                    selectDoctorServiceAdapter = new SelectDoctorServiceAdapter(getApplicationContext(), serviceModelArrayList, true, AddAdminAppointmentActivity.this);

                                    selectServiceDialog.show();
                                    Window window = selectServiceDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogService.setAdapter(selectDoctorServiceAdapter);
                                    selectDoctorServiceAdapter.notifyDataSetChanged();

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


    //Patient List API Call

    private void callListPatient() {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientListAdmin(user_id);

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
                                selectPatientDialog.show();
                                patientModelArrayList = successModel.getPatientModelArrayListAdmin();

                                if (patientModelArrayList.size() != 0) {

                                    Collections.reverse(patientModelArrayList);
                                    allPatientListAdapter = new AllPatientListAdapter(AddAdminAppointmentActivity.this, patientModelArrayList, true, AddAdminAppointmentActivity.this);
                                    recyclerDialogPatient.setAdapter(allPatientListAdapter);
                                    allPatientListAdapter.notifyDataSetChanged();
                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    //  iv_no_data_found.setVisibility(View.VISIBLE);
                                    allPatientListAdapter.notifyDataSetChanged();
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

    //Call Add New Appointment
    private void callAddAppoitment() {
        Call<SuccessModel> call = apiInterface.addAppointment(doctor_id, user_id, service_id, patient_id, date, time, comment, label, notification_type);


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

                                successMessageDoctorDialog.showDialog("Add Appointment Successfully !", true);

                            } else {
                                errorMessageDialog.showDialog("Wrong Appointment");
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


    @Override
    public void getDoctor(DoctorListModel DoctorListModel) {
        linearLayoutDoctor.setVisibility(View.VISIBLE);
        textDoctor.setText(DoctorListModel.getName());
        doctor_id = DoctorListModel.getDoctor_id();

        service_id = "";
        textService.setText("");
        linearLayoutService.setVisibility(View.GONE);


        patient_id = "";
        textPatient.setText("");
        linearLayoutPatient.setVisibility(View.GONE);


        time = "";
        textTimeSlot.setText("");
        linearLayoutTimeSlot.setVisibility(View.GONE);


        if (NetworkUtils.isNetworkAvailable(AddAdminAppointmentActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(AddAdminAppointmentActivity.this);


    }

    @Override
    public void getServiceDetail(ServiceModel serviceModel) {
        linearLayoutService.setVisibility(View.VISIBLE);
        textService.setText(serviceModel.getS_name());
        service_id = serviceModel.getService_id();
    }


    @Override
    public void getPatientDetail(PatientModel patientModel) {
        linearLayoutPatient.setVisibility(View.VISIBLE);
        textPatient.setText(patientModel.getName());
        patient_id = patientModel.getPatient_id();
    }

    @Override
    public void getTimeSlotDetail(SelectDateTimeModel selectDateTimeModel) {
        linearLayoutTimeSlot.setVisibility(View.VISIBLE);
        textTimeSlot.setText(selectDateTimeModel.getTime());
        time = selectDateTimeModel.getTime();
    }


    private void commentDialog() {

        final Dialog dialog = new Dialog(AddAdminAppointmentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comment_add_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextInputEditText textComment = dialog.findViewById(R.id.textComment);
        Button btn_done = dialog.findViewById(R.id.btn_done);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comment = textComment.getText().toString().trim();
                dialog.dismiss();
                relativeLayoutCommentsHide.setVisibility(View.VISIBLE);
                textcommentName.setText(comment);


            }
        });

        dialog.show();


    }

    @Override
    public void dialogClosed(boolean mClosed) {
        finish();
    }
}
