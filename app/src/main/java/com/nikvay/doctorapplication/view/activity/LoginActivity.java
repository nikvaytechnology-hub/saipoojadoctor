package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.SnackbarCommon;

public class LoginActivity extends AppCompatActivity {


    private Button btn_login;
    private TextInputEditText textEmail,textPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        find_All_Ids();
        events();
    }

    private void events() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=textEmail.getText().toString().trim();
                String password=textPassword.getText().toString().trim();
                if(email.equalsIgnoreCase(""))
                {
                    SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches())
                {
                    SnackbarCommon.displayValidation(v,"Enter valid Email");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    SnackbarCommon.displayValidation(v,"Password Can't Be Empty");
                }
                else if(password.length()>15||password.length()<5)
                {
                    SnackbarCommon.displayValidation(v,"Password Length Between 5 to 15");
                }
                else
                {
                    SharedUtils.putSharedUtils(LoginActivity.this);
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void find_All_Ids() {
        textEmail=findViewById(R.id.textEmail);
        textPassword=findViewById(R.id.textPassword);
        btn_login=findViewById(R.id.btn_login);

    }
}
