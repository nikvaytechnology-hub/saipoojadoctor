package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.ClassAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

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
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerClassList = findViewById(R.id.recyclerClassList);
        iv_close = findViewById(R.id.iv_close);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        fabAddClass = findViewById(R.id.fabAddClass);
        edt_search_class = findViewById(R.id.edt_search_class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClassActivity.this);
        recyclerClassList.setLayoutManager(linearLayoutManager);
        recyclerClassList.setHasFixedSize(true);

        doctorModelArrayList = SharedUtils.getUserDetails(ClassActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();
        errorMessageDialog = new ErrorMessageDialog(ClassActivity.this);
        showProgress = new ShowProgress(ClassActivity.this);




    }

    private void callClassList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.listClass(doctor_id,user_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
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
