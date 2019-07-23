package com.nikvay.doctorapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.view.activity.doctor_activity.SessionEditActivity;

public class AppointmentDialog {

    private Context mContext;
    private Dialog dialog;
    private TextView textMessage;


    public AppointmentDialog(Context mContext) {
        this.mContext = mContext;
        this.dialog = new Dialog(mContext);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_appointment_success);
        this.textMessage =dialog.findViewById(R.id.textMessage);
        this.dialog.setCancelable(false);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void showDialog(final String message) {
        dialog.show();
        textMessage.setText(message);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
/*                Intent intent=new Intent(mContext, SessionEditActivity.class);
                mContext.startActivity(intent);*/
            }
        }, 3000);


    }
}
