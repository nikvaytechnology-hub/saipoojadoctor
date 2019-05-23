package com.nikvay.doctorapplication.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;

import java.util.ArrayList;


public class PatientFragment extends Fragment {

    Context mContext;
    private RecyclerView recyclerPatientList;
    ArrayList<PatientModel> patientModelArrayList=new ArrayList<>();
    private PatientAdapter patientAdapter;
    private FloatingActionButton fabAddPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();
        return view;
    }

    private void find_All_IDs(View view) {
        recyclerPatientList=view.findViewById(R.id.recyclerPatientList);
        fabAddPatient=view.findViewById(R.id.fabAddPatient);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerPatientList.setLayoutManager(linearLayoutManager);

        for(int i=1;i<=5;i++)
        {

            patientModelArrayList.add(new PatientModel("1","Akshay","jatharnihalp@gmail.com","9503873045","Natepute","25-05-2019"));
        }

        patientAdapter=new PatientAdapter(mContext,patientModelArrayList);
        recyclerPatientList.setAdapter(patientAdapter);
        recyclerPatientList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerPatientList.setHasFixedSize(true);

    }

    private void events() {
        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Add Patient", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
