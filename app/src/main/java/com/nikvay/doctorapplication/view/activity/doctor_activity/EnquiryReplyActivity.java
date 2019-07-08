package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.EnquiryListModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryReplyActivity extends AppCompatActivity {


    private ImageView iv_close;
    private EnquiryListModel enquiryListModel;
    private TextView textEnquiryId, textEnquiryDate, textName, textPhone_no, textEmail,textTitle,textDescription,textReplyMessage;
    private TextInputEditText textReply;
    private Button btnReply;
    private String reply,enquiry_id,TAG = getClass().getSimpleName();;
    private ErrorMessageDialog errorMessageDialog;
    private ShowProgress showProgress;
    private ApiInterface apiInterface;
    private SuccessMessageDialog successMessageDialog;
    private String strReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_reply);

        find_All_IDs();
        events();

    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close=findViewById(R.id.iv_close);
        textEnquiryId=findViewById(R.id.textEnquiryId);
        textEnquiryDate=findViewById(R.id.textEnquiryDate);
        textName=findViewById(R.id.textName);
        textPhone_no=findViewById(R.id.textPhone_no);
        textEmail=findViewById(R.id.textEmail);
        textTitle=findViewById(R.id.textTitle);
        textDescription=findViewById(R.id.textDescription);
        textReply=findViewById(R.id.textReply);
        btnReply=findViewById(R.id.btnReply);
        textReplyMessage=findViewById(R.id.textReplyMessage);

        errorMessageDialog=new ErrorMessageDialog(EnquiryReplyActivity.this);
        showProgress=new ShowProgress(EnquiryReplyActivity.this);
        successMessageDialog=new SuccessMessageDialog(EnquiryReplyActivity.this);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            enquiryListModel = (EnquiryListModel) bundle.getSerializable(StaticContent.IntentKey.ENQUIRY_REPLY);
            textEnquiryId.setText("Enquiry Id:-"+enquiryListModel.getEnquiry_id());
            textEnquiryDate.setText(enquiryListModel.getEnquiry_date());
            textName.setText(enquiryListModel.getName());
            textPhone_no.setText(enquiryListModel.getPhone_no());
            textEmail.setText(enquiryListModel.getEmail());
            textTitle.setText(enquiryListModel.getTitle());
            textDescription.setText(enquiryListModel.getDescription());
            enquiry_id=enquiryListModel.getEnquiry_id();
            textReplyMessage.setText(enquiryListModel.getReply());
            strReply=enquiryListModel.getReply();
            if(strReply!=null)
            {
               btnReply.setVisibility(View.GONE);
               textReply.setVisibility(View.GONE);
            }

        }

    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reply=textReply.getText().toString().trim();
                if(reply.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Please Enter Your Reply");
                }
                else
                {
                    if (NetworkUtils.isNetworkAvailable(EnquiryReplyActivity.this))
                        addEnquiry();
                    else
                        NetworkUtils.isNetworkNotAvailable(EnquiryReplyActivity.this);
                }
            }
        });

    }

    private void addEnquiry() {

        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.enquiryReply(enquiry_id,reply);
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

                            if (code.equalsIgnoreCase("1")) {

                                successMessageDialog.showDialog("Enquiry Reply Add Successfully");

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
