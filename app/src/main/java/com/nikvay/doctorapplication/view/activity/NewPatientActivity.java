package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.fragment.HomeFragment;

public class NewPatientActivity extends AppCompatActivity {

    private TextView textName, textEmail, textPhone, textAddress, textSave,textTitleName;
    private String email, name, address, phone,edit;
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    private ImageView iv_close_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        textName = findViewById(R.id.textName);
        textAddress = findViewById(R.id.textAddress);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textPhone);
        textSave = findViewById(R.id.textSave);
        textTitleName = findViewById(R.id.textTitleName);
        iv_close_activity = findViewById(R.id.iv_close);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            edit=bundle.getString("EDIT");
            textTitleName.setText("Update Customer");
            textSave.setText("Update");
        }

        errorMessageDialog = new ErrorMessageDialog(NewPatientActivity.this);
        successMessageDialog = new SuccessMessageDialog(NewPatientActivity.this);

    }

    private void events() {
        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {

                    successMessageDialog.showDialog("New Customer Add");

                }

            }
        });
        iv_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private boolean validation() {

        email = textEmail.getText().toString().trim();
        name = textName.getText().toString().trim();
        if (email.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Email ID Can't Be Empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()) {
            errorMessageDialog.showDialog("Enter valid Email");
            return false;
        } else if (name.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Name Can't Be Empty");
            return false;
        }

        return true;
    }
}
