package com.nikvay.doctorapplication.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarCommon {

    public static void displayValidation(View view, String message)
    {
        Snackbar snackbar =  Snackbar
                .make(view,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
