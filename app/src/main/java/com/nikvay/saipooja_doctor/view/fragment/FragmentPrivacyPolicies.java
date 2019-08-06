package com.nikvay.saipooja_doctor.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikvay.doctorapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPrivacyPolicies extends Fragment {


    public FragmentPrivacyPolicies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_privacy_policies, container, false);
    }

}
