package com.nikvay.doctorapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;

public class SuccessMessageDoctorDialog {


    private Context mContext;
    private Dialog dialog;
    private TextView textMesageDialog;
    private SuccessDialogClosed successDialogClosed;
    private boolean isNotifyme = false;

    public SuccessMessageDoctorDialog(Context mContext) {
        this.mContext = mContext;
        this.dialog = new Dialog(mContext);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_success);
        this.textMesageDialog = (TextView) dialog.findViewById(R.id.textMessage);
    }

    public SuccessMessageDoctorDialog(Context mContext, boolean isNotifyme) {
        this.mContext = mContext;
        this.dialog = new Dialog(mContext);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_success);
        this.successDialogClosed = (SuccessDialogClosed) mContext;
        this.textMesageDialog = (TextView) dialog.findViewById(R.id.textMessage);
        this.isNotifyme = isNotifyme;
    }

    public void showDialog(String message, final boolean isToclose) {
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isToclose) {
                    if (isNotifyme) {
                        successDialogClosed.dialogClosed(true);
                    }
                }
            }
        });
        textMesageDialog.setText(message);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (isToclose) {
        }
        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (isToclose) {

                }
            }
        }, 3000);

    }

}
