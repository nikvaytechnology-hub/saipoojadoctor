package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.ClassModel;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.ClassAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassActivity extends AppCompatActivity {

    private RecyclerView recyclerClassList;
    private ImageView iv_close, iv_no_data_found;
    private ArrayList<ClassModel> classModelArrayList = new ArrayList<>();
    private ClassAdapter classAdapter;
    private FloatingActionButton fabAddClass;
    ShowProgress showProgress;
    private String TAG = getClass().getSimpleName(), doctor_id, user_id;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private EditText edt_search_class;
    private SwipeRefreshLayout refreshClass;
    SharedPreferences sharedPreferenceslogin;
    SharedPreferences sharedPreferences2;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        sharedPreferences2=getSharedPreferences("login_status",MODE_PRIVATE);
        find_All_IDs();
        events();
        if (NetworkUtils.isNetworkAvailable(ClassActivity.this))
            callClassList();
        else
            NetworkUtils.isNetworkNotAvailable(ClassActivity.this);

    }


    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        fabAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClassActivity.this, NewClassActivity.class);
                startActivity(intent);
            }
        });

        edt_search_class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                classAdapter.getFilter().filter(edt_search_class.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshClass.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(ClassActivity.this))
                    callClassList();
                else
                    NetworkUtils.isNetworkNotAvailable(ClassActivity.this);

                refreshClass.setRefreshing(false);
            }
        });
    }

    private void find_All_IDs()
    {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerClassList = findViewById(R.id.recyclerClassList);
        iv_close = findViewById(R.id.iv_close);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        fabAddClass = findViewById(R.id.fabAddClass);
        refreshClass = findViewById(R.id.refreshClass);
        edt_search_class = findViewById(R.id.edt_search_class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClassActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerClassList.setLayoutManager(linearLayoutManager);
        recyclerClassList.setHasFixedSize(true);

 user=sharedPreferences2.getString("login_status","");

    doctorModelArrayList = SharedUtils.getUserDetails(ClassActivity.this);
    doctor_id = doctorModelArrayList.get(0).getDoctor_id();
    user_id = doctorModelArrayList.get(0).getUser_id();
    errorMessageDialog = new ErrorMessageDialog(ClassActivity.this);
    showProgress = new ShowProgress(ClassActivity.this);





    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        callClassList();
    }


    private void callClassList() {
//        showProgress.showDialog();
        if (user.equals("doctor"))
        {
            SharedPreferences sharedPreferences=getSharedPreferences("user_id",MODE_PRIVATE);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("doctor_id",doctor_id);
            editor.putString("user_id",user_id);
            editor.apply();
            editor.commit();
        }

        Call<SuccessModel> call = apiInterface.listClass(doctor_id,user_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
              //  showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        classModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();

                            if (code.equalsIgnoreCase("1")) {

                                classModelArrayList=successModel.getClassModelArrayList();

                                if(classModelArrayList.size()!=0) {
                                    classAdapter = new ClassAdapter(ClassActivity.this, classModelArrayList);
                                    recyclerClassList.setAdapter(classAdapter);
                                    iv_no_data_found.setVisibility(View.GONE);
                                    classAdapter.notifyDataSetChanged();
                                   // recyclerClassList.addItemDecoration(new DividerItemDecoration(ClassActivity.this, DividerItemDecoration.VERTICAL));;
                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    classAdapter.notifyDataSetChanged();
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
