package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nikvay.doctorapplication.R;

public class PrescriptionActivity extends AppCompatActivity {

    private ImageView iv_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);

    }

    private void events() {

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
