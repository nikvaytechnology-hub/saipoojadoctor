package com.nikvay.doctorapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.nikvay.doctorapplication.R;

public class ShowProgress {
    private Dialog dialog;
    private Context context;

    public ShowProgress(Context context) {
        this.context = context;
    }

    public ShowProgress showDialog() {

        if (dialog != null) {
            dialog = null;
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource (android.R.color.transparent);
        dialog.show();
        return null;
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
