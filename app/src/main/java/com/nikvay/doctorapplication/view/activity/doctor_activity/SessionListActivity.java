package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.SessionListModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.AddInstructor;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.ServiceListAdapter;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.SessionListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import io.fabric.sdk.android.services.settings.SessionSettingsData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionListActivity extends AppCompatActivity {
    private ImageView iv_close,iv_no_data_found;
    private RecyclerView recyclerSessionList;
    private ArrayList<SessionListModel> sessionListModelArrayList=new ArrayList<>();
    private SessionListAdapter sessionListAdapter;
   // private FloatingActionButton fabAddSession;
    private ClassModel classModel;
    private TextView tv_addSession;
    private ShowProgress showProgress;
    String sClass_id,class_name;
    private String class_id,TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private SwipeRefreshLayout refreshSession;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    String status;
    //  private EditText edt_search_session;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        sharedPreferences2=getSharedPreferences("login_status",MODE_PRIVATE);
    status=sharedPreferences2.getString("login_status","");

        class_id=getIntent().getStringExtra("class_id");
        class_name = getIntent().getStringExtra("class_name");
        sharedPreferences=getSharedPreferences("className",MODE_PRIVATE);
        tv_addSession=findViewById(R.id.tv_newSession);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close=findViewById(R.id.iv_close);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        recyclerSessionList=findViewById(R.id.recyclerSessionList);
        //fabAddSession=findViewById(R.id.fabAddSession);
        refreshSession=findViewById(R.id.refreshSession);
        //edt_search_session=findViewById(R.id.edt_search_session);
        showProgress=new ShowProgress(SessionListActivity.this);
        errorMessageDialog=new ErrorMessageDialog(SessionListActivity.this);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            class_id=classModel.getClass_id();
        }




        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SessionListActivity.this);
        recyclerSessionList.setLayoutManager(linearLayoutManager);
        recyclerSessionList.hasFixedSize();


        if (NetworkUtils.isNetworkAvailable(SessionListActivity.this))
            callSessionList();
        else
            NetworkUtils.isNetworkNotAvailable(SessionListActivity.this);




    }


    private void events() {

        tv_addSession.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (status.equals("admin"))
                {
                    Intent intent=new Intent(SessionListActivity.this, AddInstructor.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(SessionListActivity.this,SessionDetailsActivity.class);
                    startActivity(intent);
                }


            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

/*        fabAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SessionListActivity.this,SessionDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);
            }
        });*/
        refreshSession.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(SessionListActivity.this))
                    callSessionList();
                else
                    NetworkUtils.isNetworkNotAvailable(SessionListActivity.this);


                refreshSession.setRefreshing(false);
            }
        });


     /*   edt_search_session.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                sessionListAdapter.getFilter().filter(edt_search_session.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
    }

    private void callSessionList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.listSession(class_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        sessionListModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                sessionListModelArrayList=successModel.getSessionListModelArrayList();

                                if(sessionListModelArrayList.size()!=0) {
                                    Collections.reverse(sessionListModelArrayList);
                                    sessionListAdapter=new SessionListAdapter(SessionListActivity.this,sessionListModelArrayList);
                                    recyclerSessionList.setAdapter(sessionListAdapter);
                                    iv_no_data_found.setVisibility(View.GONE);
                                    sessionListAdapter.notifyDataSetChanged();

                                    // recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));
                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    sessionListAdapter.notifyDataSetChanged();
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
