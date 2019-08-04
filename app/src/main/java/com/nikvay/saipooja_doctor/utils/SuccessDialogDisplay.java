package com.nikvay.saipooja_doctor.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;

public class SuccessDialogDisplay {
    private Context mContext;
    private Dialog dialog;
    private TextView textMessage;


    public SuccessDialogDisplay(Context mContext) {
        this.mContext = mContext;
        this.dialog = new Dialog(mContext);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_success);
        this.textMessage =dialog.findViewById(R.id.textMessage);
        this.dialog.setCancelable(false);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void showDialog(String message) {
        dialog.show();
        textMessage.setText(message);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

            }
        }, 3000);


    }

}
