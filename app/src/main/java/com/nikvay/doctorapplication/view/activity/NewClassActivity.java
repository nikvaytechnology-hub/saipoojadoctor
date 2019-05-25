package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;

public class NewClassActivity extends AppCompatActivity {

    private TextView textClass, textDuration, textSeats, textCost, textSave,textTitleName;
    private ImageView iv_close_activity;
    private String textClassName;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        find_All_IDs();
        events();
    }

    private void events() {
        iv_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_close_activity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        });

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {

                    successMessageDialog.showDialog("New Customer Add");

                }

            }
        });

    }
    private boolean validation() {

        textClassName = textClass.getText().toString().trim();

        if (textClassName.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Class Name Can't Empty");
            return false;
        }

        return true;
    }

    private void find_All_IDs() {
        iv_close_activity = findViewById(R.id.iv_close);


        textClass = findViewById(R.id.textClass);
        textDuration = findViewById(R.id.textDuration);
        textSeats = findViewById(R.id.textSeats);
        textCost = findViewById(R.id.textCost);
        textSave = findViewById(R.id.textSave);
        textTitleName = findViewById(R.id.textTitleName);


        errorMessageDialog = new ErrorMessageDialog(NewClassActivity.this);
        successMessageDialog = new SuccessMessageDialog(NewClassActivity.this);



    }
}
