package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.Dialog;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.SessionListModel;
import com.nikvay.saipooja_doctor.model.SessionPatientAddedModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.AddAttendeeAdapter;
import com.nikvay.saipooja_doctor.interfaceutils.MultipleSelectInterface;
import com.nikvay.saipooja_doctor.model.SessionPatientExistModel;
import com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter.AllPatientListAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.PatientMultipleSelecationAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.SessionPatientAddedListAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionEditActivity extends AppCompatActivity implements MultipleSelectInterface {

    private ImageView iv_close;
    private TextView textClass, textCost, textDate, textSeats;
    // private Button btnEdit;
    private SessionListModel sessionListModel;
    private LinearLayout linearLayoutPatient, linearLayoutSelectPatient;
    //  private RecyclerView recyclerViewPatient;
    private ApiInterface apiInterface;
    String TAG = getClass().getSimpleName();
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    ArrayList<SessionPatientExistModel> sessionPatientExistModelArrayList = new ArrayList<>();
    ArrayList<SessionPatientExistModel> sessionPatientExistModelSelectArrayList = new ArrayList<>();
    private String doctor_id = "", user_id = "", session_id = "", doctor_idAssign = "";
    private int no_of_seats;
    private ShowProgress showProgress;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDoctorDialog;
    AllPatientListAdapter allPatientListAdapter;

    //select Patient
    private Dialog selectPatientDialog;
    private EditText editSearchPatient;
    private Button btnCancelDialogPatient, btnOkDialogPatient;
    private RecyclerView recyclerDialogPatient;
    //   private LinearLayout linearLayoutPatientList;

    private TextView textPatient;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    ArrayList<SessionPatientExistModel> patientModelArrayListSelected = new ArrayList<>();
    private PatientMultipleSelecationAdapter patientMultipleSelecationAdapter;


    //Patient Added List
    private RecyclerView recyclerViewPatientAddList;
    private LinearLayout linearLayoutPatientAddedList;
    private ArrayList<SessionPatientAddedModel> sessionPatientAddedModelArrayList = new ArrayList<>();
    private SessionPatientAddedListAdapter sessionPatientAddedListAdapter;
    private ArrayList<String> patient_idArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_edit);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close = findViewById(R.id.iv_close);
        textClass = findViewById(R.id.textClass);
        textCost = findViewById(R.id.textCost);
        textDate = findViewById(R.id.textDate);
        textSeats = findViewById(R.id.textSeats);
        // btnEdit = findViewById(R.id.btnEdit);
        //recyclerViewPatient = findViewById(R.id.recyclerViewPatient);

        linearLayoutPatientAddedList = findViewById(R.id.linearLayoutPatientAddedList);
        recyclerViewPatientAddList = findViewById(R.id.recyclerViewPatientAddList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SessionEditActivity.this);
        // recyclerViewPatient.setLayoutManager(linearLayoutManager);
        // recyclerViewPatient.hasFixedSize();

        doctorModelArrayList = SharedUtils.getUserDetails(SessionEditActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            sessionListModel = (SessionListModel) bundle.getSerializable(StaticContent.IntentKey.SESSION__DETAIL);
            textClass.setText(sessionListModel.getName_class());
            textCost.setText(sessionListModel.getCost());
            textDate.setText(sessionListModel.getDate());
            textSeats.setText(sessionListModel.getNo_of_seats());
            doctor_idAssign = sessionListModel.getDoctor_id();
            session_id = sessionListModel.getSession_id();
            no_of_seats = Integer.parseInt(sessionListModel.getNo_of_seats());
        }


        //Select Patient Start

        selectPatientDialog = new Dialog(this);
        selectPatientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPatientDialog.setContentView(R.layout.dialog_select_patient);

        btnCancelDialogPatient = selectPatientDialog.findViewById(R.id.btnCancelDialogSC);
        btnOkDialogPatient = selectPatientDialog.findViewById(R.id.btnOkDialogSC);
        editSearchPatient = selectPatientDialog.findViewById(R.id.editSearchCPatient);
        selectPatientDialog.setCancelable(false);
        recyclerDialogPatient = selectPatientDialog.findViewById(R.id.recyclerDialogSC);

        // linearLayoutPatientList = findViewById(R.id.linearLayoutPatientList);
        textPatient = findViewById(R.id.textPatient);
        linearLayoutPatient = findViewById(R.id.linearLayoutPatient);

        LinearLayoutManager linearLayoutManagerPatient = new LinearLayoutManager(getApplicationContext());
        recyclerDialogPatient.setLayoutManager(linearLayoutManagerPatient);


        LinearLayoutManager linearLayoutManagerPatientAdded = new LinearLayoutManager(getApplicationContext());
        recyclerViewPatientAddList.setLayoutManager(linearLayoutManagerPatientAdded);
        recyclerViewPatientAddList.hasFixedSize();


        Window windowPatient = selectPatientDialog.getWindow();
        windowPatient.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //select Patient End


        showProgress = new ShowProgress(SessionEditActivity.this);
        errorMessageDialog = new ErrorMessageDialog(SessionEditActivity.this);
        successMessageDoctorDialog = new SuccessMessageDialog(SessionEditActivity.this);

        if (NetworkUtils.isNetworkAvailable(SessionEditActivity.this)) {
            callListPatientAdded();
        } else
            NetworkUtils.isNetworkNotAvailable(SessionEditActivity.this);

    }


    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linearLayoutPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(SessionEditActivity.this)) {
                    callListPatient();
                } else
                    NetworkUtils.isNetworkNotAvailable(SessionEditActivity.this);
            }
        });

        btnOkDialogPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEdiSession();
                callListPatientAdded();
                // sessionPatientAddedModelArrayList.clear();

                callListPatientAdded();
                editSearchPatient.setText("");
                selectPatientDialog.dismiss();

            }
        });

        btnCancelDialogPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                patientModelArrayListSelected.clear();
                editSearchPatient.setText("");
                selectPatientDialog.dismiss();

            }
        });

        editSearchPatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //  patientMultipleSelecationAdapter.getFilter().filter(editSearchPatient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       /* btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validation()) {
                    if (NetworkUtils.isNetworkAvailable(SessionEditActivity.this)) {
                        callEdiSession();
                    } else
                        NetworkUtils.isNetworkNotAvailable(SessionEditActivity.this);
                }
            }
        });
*/
    }

    private boolean validation() {
        if (patientModelArrayListSelected.size() == 0) {
            errorMessageDialog.showDialog("Please Select Patient");
            return false;
        } else if (patientModelArrayListSelected.size() > no_of_seats) {
            errorMessageDialog.showDialog("Please select less or equal to Seats");
            return false;
        }

        return true;
    }


    private void callListPatient() {
        Call<SuccessModel> call = apiInterface.list_session_patient(doctor_id, user_id, session_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        sessionPatientExistModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {
                                selectPatientDialog.show();
                                sessionPatientExistModelArrayList = successModel.getSessionPatientExistModelArrayList();
                                for (int i = 0; i < sessionPatientExistModelArrayList.size(); i++) {

                                    String getStatus = sessionPatientExistModelArrayList.get(i).getStatus();
                                    if (getStatus.equalsIgnoreCase("1"))
                                    {
                                        {
                                            patientModelArrayListSelected.add(sessionPatientExistModelArrayList.get(i));
                                        }
                                    }
                                }

                                AddAttendeeAdapter addAttendeeAdapter = new AddAttendeeAdapter(getApplicationContext(), sessionPatientExistModelArrayList, SessionEditActivity.this);
                                recyclerDialogPatient.setAdapter(addAttendeeAdapter);

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

        //  showProgress.showDialog();
       /* Call<SuccessModel> call = apiInterface.patientList(doctor_id);

        call.enqueue(new Callback<SuccessModel>()
        {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
             {
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
                                    patientModelArrayListSelected.clear();
                                    patientMultipleSelecationAdapter = new PatientMultipleSelecationAdapter(getApplicationContext(), patientModelArrayList, true, SessionEditActivity.this);
                                    selectPatientDialog.show();
                                    recyclerDialogPatient.setAdapter(patientMultipleSelecationAdapter);
                                    patientMultipleSelecationAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerViewPatient.setVisibility(View.VISIBLE);
                                    patientMultipleSelecationAdapter.notifyDataSetChanged();
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
        });*/
    }

    private void callEdiSession() {
        //  showProgress.showDialog();
        String patient_id = String.valueOf(getPatientIdArray());
        patient_idArrayList.clear();
        String no_of_seat = String.valueOf(no_of_seats);
        Call<SuccessModel> call = apiInterface.editSession(session_id, patient_id, doctor_idAssign, no_of_seat);


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
                                successMessageDoctorDialog.showDialog("Attendee Add Sucessfully");
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

    private void callListPatientAdded() {

        //  showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.listSessionPatientAdded(session_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        sessionPatientAddedModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                sessionPatientAddedModelArrayList = successModel.getSessionPatientAddedModelArrayList();

                                if (sessionPatientAddedModelArrayList.size() != 0) {
                                    linearLayoutPatientAddedList.setVisibility(View.VISIBLE);
                                    sessionPatientAddedListAdapter = new SessionPatientAddedListAdapter(getApplicationContext(), sessionPatientAddedModelArrayList);
                                    recyclerViewPatientAddList.setAdapter(sessionPatientAddedListAdapter);
                                    sessionPatientAddedListAdapter.notifyDataSetChanged();

                                    recyclerViewPatientAddList.addItemDecoration(new DividerItemDecoration(SessionEditActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerViewPatientAddList.setVisibility(View.GONE);
                                    linearLayoutPatientAddedList.setVisibility(View.GONE);
                                    sessionPatientAddedListAdapter.notifyDataSetChanged();
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

  /*  @Override
    public void getPatientDetail(PatientModel patientModel)
    {

        //linearLayoutPatientList.setVisibility(View.VISIBLE);
        patientModelArrayListSelected.add(patientModel);

        if (patientModelArrayListSelected.size() != 0)
        {
            patientMultipleSelecationAdapter = new PatientMultipleSelecationAdapter(SessionEditActivity.this, patientModelArrayListSelected, sessionPatientAddedModelArrayList,false, SessionEditActivity.this);
           // recyclerViewPatient.setAdapter(patientMultipleSelecationAdapter);
           // recyclerViewPatient.setVisibility(View.VISIBLE);

        } else {
            patientMultipleSelecationAdapter.notifyDataSetChanged();
        }

    }*/


    private JSONArray getPatientIdArray() {



        for (int i = 0; i < patientModelArrayListSelected.size(); i++) {

            patient_idArrayList.add(patientModelArrayListSelected.get(i).getPatient_id());
        }
        HashSet<String> hashSet=new HashSet<>();
        hashSet.addAll(patient_idArrayList);
        patient_idArrayList.clear();
        patient_idArrayList.addAll(hashSet);


        JSONArray ppJsonArray = new JSONArray(patient_idArrayList);

        return ppJsonArray;

    }

    @Override
    public void getSessionPatientDetail(SessionPatientExistModel sessionPatientExistModel) {
        //linearLayoutPatientList.setVisibility(View.VISIBLE);
        patientModelArrayListSelected.add(sessionPatientExistModel);

        if (patientModelArrayListSelected.size() != 0) {
            AddAttendeeAdapter addAttendeeAdapter = new AddAttendeeAdapter(SessionEditActivity.this, patientModelArrayListSelected, SessionEditActivity.this);
            // recyclerViewPatient.setAdapter(patientMultipleSelecationAdapter);
            // recyclerViewPatient.setVisibility(View.VISIBLE);

        } else {
            patientMultipleSelecationAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void removePatientAdded(SessionPatientExistModel sessionPatientExistModel) {
        patientModelArrayListSelected.remove(sessionPatientExistModel);
    }
}
