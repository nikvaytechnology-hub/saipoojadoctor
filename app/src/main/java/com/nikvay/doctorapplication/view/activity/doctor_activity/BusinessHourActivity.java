package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.DoctorTimeModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessHourActivity extends AppCompatActivity {


  private LinearLayout ll_iv_Sunday, ll_iv_Monday, ll_iv_Tuesday, ll_iv_Wednesday, ll_iv_Thirsday, ll_iv_Friday, ll_iv_Saturaday;

  private TextView from_iv_Sunday, to_iv_Sunday, from_iv_Monday, to_iv_Monday, from_iv_Tuesday, to_iv_Tuesday, from_iv_Wednesday, to_iv_Wednesday, from_iv_Thirsday,
          to_iv_Thirsday, from_iv_Friday, to_iv_Friday, from_iv_Saturaday, to_iv_Saturaday, textSave;

  private boolean isStartDate = false;
  private String status, state;
  private ImageView iv_back;
  private ArrayList<DoctorTimeModel> selectDoctorTimeModelArrayList = new ArrayList<>();

  private FloatingActionButton fabCancleAllApt;

  private DoctorTimeModel doctorTimeModel1;
  private DoctorTimeModel doctorTimeModel2;
  private DoctorTimeModel doctorTimeModel3;
  private DoctorTimeModel doctorTimeModel4;
  private DoctorTimeModel doctorTimeModel5;
  private DoctorTimeModel doctorTimeModel6;
  private DoctorTimeModel doctorTimeModel7;

  private JSONArray selectJSONArray, unSelectJSONArray;
  private SuccessMessageDialog successMessageDialog;
  private ErrorMessageDialog errorMessageDialog;

  private Switch sw_Sunday, sw_Monday, sw_Tuesday, sw_Wednesday,
          sw_Thirsday, sw_Friday, sw_Saturaday;
  private String day;
  private String startTimeSunday, startTimeMonday, startTimeTuesday, startTimeWednesday, startTimeThirsday,
          startTimeFriday, startTimeSaturaday;
  private String endTimeSunday, endTimeMonday, endTimeTuesday, endTimeWednesday,
          endTimeThirsday, endTimeFriday, endTimeSaturaday;
  private Spinner spinnerTimeSlot;

  private ApiInterface apiInterface;
  private String TAG = getClass().getSimpleName();
  private String startTime = "00:00", endTime = "00:00";
  private String dayStatus = null, Evening = null, Whole_Day = null, mTitle = "", timeSlot, slotTime, doctor_id = "", user_id = "";
  private int hours, converstartminute, converendminute;
  private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_business_hour);
    find_All_ID();
    event();
  }

  private void find_All_ID() {
    from_iv_Sunday = findViewById(R.id.from_iv_Sunday);
    from_iv_Monday = findViewById(R.id.from_iv_Monday);
    from_iv_Tuesday = findViewById(R.id.from_iv_Tuesday);
    from_iv_Wednesday = findViewById(R.id.from_iv_Wednesday);
    from_iv_Thirsday = findViewById(R.id.from_iv_Thirsday);
    from_iv_Friday = findViewById(R.id.from_iv_Friday);
    from_iv_Saturaday = findViewById(R.id.from_iv_Saturaday);
    spinnerTimeSlot = findViewById(R.id.spinnerTimeSlot);


    fabCancleAllApt = findViewById(R.id.fabCancleAllApt);

    to_iv_Sunday = findViewById(R.id.to_iv_Sunday);
    to_iv_Monday = findViewById(R.id.to_iv_Monday);
    to_iv_Tuesday = findViewById(R.id.to_iv_Tuesday);
    to_iv_Wednesday = findViewById(R.id.to_iv_Wednesday);
    to_iv_Thirsday = findViewById(R.id.to_iv_Thirsday);
    to_iv_Friday = findViewById(R.id.to_iv_Friday);
    to_iv_Saturaday = findViewById(R.id.to_iv_Saturaday);

    sw_Sunday = findViewById(R.id.sw_Sunday);
    sw_Monday = findViewById(R.id.sw_Monday);
    sw_Tuesday = findViewById(R.id.sw_Tuesday);
    sw_Wednesday = findViewById(R.id.sw_Wednesday);
    sw_Thirsday = findViewById(R.id.sw_Thirsday);
    sw_Friday = findViewById(R.id.sw_Friday);
    sw_Saturaday = findViewById(R.id.sw_Saturaday);

    apiInterface = ApiClient.getClient().create(ApiInterface.class);

    doctorModelArrayList = SharedUtils.getUserDetails(BusinessHourActivity.this);
    doctor_id = doctorModelArrayList.get(0).getDoctor_id();
    user_id = doctorModelArrayList.get(0).getUser_id();

    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      String day;
      day = bundle.getString(StaticContent.IntentKey.TIME_SLOT);

      if (day.equalsIgnoreCase("1")) {
        dayStatus = "Morning";
      }
      if (day.equalsIgnoreCase("2")) {
        dayStatus = "Evening";
      }
      if (day.equalsIgnoreCase("3")) {
        dayStatus = "Whole_Day";
      }
    }


    doctorTimeModel1 = new DoctorTimeModel();
    doctorTimeModel2 = new DoctorTimeModel();
    doctorTimeModel3 = new DoctorTimeModel();
    doctorTimeModel4 = new DoctorTimeModel();
    doctorTimeModel5 = new DoctorTimeModel();
    doctorTimeModel6 = new DoctorTimeModel();
    doctorTimeModel7 = new DoctorTimeModel();


    iv_back = findViewById(R.id.iv_close);
    textSave = findViewById(R.id.textSave);
    successMessageDialog = new SuccessMessageDialog(BusinessHourActivity.this);
    errorMessageDialog = new ErrorMessageDialog(BusinessHourActivity.this);

  }


  private void event() {

    iv_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    fabCancleAllApt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(BusinessHourActivity.this, CancelAllAptActivity.class);
        startActivity(intent);
      }
    });

    spinnerTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slotTime = spinnerTimeSlot.getSelectedItem().toString();

        if (!slotTime.equalsIgnoreCase("Time Slot")) {

          StringTokenizer stringTokenizer = new StringTokenizer(slotTime, " ");

          timeSlot = stringTokenizer.nextToken().trim();
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    textSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (doValidation()) {
          if (selectDoctorTimeModelArrayList.size() != 0) {
            //  Toast.makeText(BusinessHourActivity.this, "Data" + selectDoctorTimeModelArrayList.get(0).getDay_Id(), Toast.LENGTH_SHORT).show();
            //    successMessageDialog.showDialog("Data Added Successfully");

            callAddTimeSlot();
          } else {
            errorMessageDialog.showDialog("Please Select Day Slot");
          }
        }


      }
    });

    sw_Sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        status = "1";
        if (doValidation()) {
          if (isChecked) {

            doctorTimeModel1.setStartTime(startTimeSunday);
            doctorTimeModel1.setEndTime(endTimeSunday);
            doctorTimeModel1.setDay("Sun");
            doctorTimeModel1.setDay_Id("1");
            doctorTimeModel1.setTimeSlot(timeSlot);

            selectDoctorTimeModelArrayList.add(doctorTimeModel1);
            state = "1";
            disableFields(state);
            Log.d("You are :", "Checked");
          } else {
            state = "1";
            EnableFields(state);
            selectDoctorTimeModelArrayList.remove(doctorTimeModel1);
            Log.d("You are :", " Not Checked");
          }
        }


      }
    });

    sw_Monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        status = "2";
        if (doValidation()) {

          if (isChecked) {

            if (startTimeMonday.equalsIgnoreCase("") || endTimeMonday.equalsIgnoreCase("")) {
              errorMessageDialog.showDialog("Plz select time");
            }
            doctorTimeModel2.setStartTime(startTimeMonday);
            doctorTimeModel2.setEndTime(endTimeMonday);
            doctorTimeModel2.setDay("Mon");
            doctorTimeModel2.setDay_Id("2");
            doctorTimeModel2.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel2);
            state = "2";
            disableFields(state);
            Log.d("You are :", "Checked");
          } else {
            state = "2";
            EnableFields(state);
            selectDoctorTimeModelArrayList.remove(doctorTimeModel2);
            Log.d("You are :", " Not Checked");
          }
        }

      }
    });

    sw_Tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        status = "3";
        if (doValidation()) {

          if (isChecked) {
            doctorTimeModel3.setStartTime(startTimeTuesday);
            doctorTimeModel3.setEndTime(endTimeTuesday);
            doctorTimeModel3.setDay("Tue");
            doctorTimeModel3.setDay_Id("3");
            doctorTimeModel3.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel3);
            state = "3";
            disableFields(state);
            Log.d("You are :", "Checked");
          }
        } else {
          state = "3";
          EnableFields(state);
          selectDoctorTimeModelArrayList.remove(doctorTimeModel3);

          Log.d("You are :", " Not Checked");
        }
      }
    });

    sw_Wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (doValidation()) {

          if (isChecked) {
            doctorTimeModel4.setStartTime(startTimeWednesday);
            doctorTimeModel4.setEndTime(endTimeWednesday);
            doctorTimeModel4.setDay("Wed");
            doctorTimeModel4.setDay_Id("4");
            doctorTimeModel4.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel4);
            state = "4";
            disableFields(state);
            Log.d("You are :", "Checked");
          }
        } else {
          state = "4";
          EnableFields(state);
          selectDoctorTimeModelArrayList.remove(doctorTimeModel4);

          Log.d("You are :", " Not Checked");
        }

      }
    });

    sw_Thirsday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (doValidation()) {

          if (isChecked) {
            doctorTimeModel5.setStartTime(startTimeThirsday);
            doctorTimeModel5.setEndTime(endTimeThirsday);
            doctorTimeModel5.setDay("Thu");
            doctorTimeModel5.setDay_Id("5");
            doctorTimeModel5.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel5);
            state = "5";
            disableFields(state);
            Log.d("You are :", "Checked");
          }
        } else {
          state = "5";
          EnableFields(state);
          selectDoctorTimeModelArrayList.remove(doctorTimeModel5);

          Log.d("You are :", " Not Checked");
        }
      }
    });

    sw_Friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (doValidation()) {

          if (isChecked) {
            doctorTimeModel6.setStartTime(startTimeFriday);
            doctorTimeModel6.setEndTime(endTimeFriday);
            doctorTimeModel6.setDay("Fri");
            doctorTimeModel6.setDay_Id("6");
            doctorTimeModel6.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel6);
            state = "6";
            disableFields(state);
            Log.d("You are :", "Checked");
          }
        } else {
          state = "6";
          EnableFields(state);
          selectDoctorTimeModelArrayList.remove(doctorTimeModel6);

          Log.d("You are :", " Not Checked");
        }
      }
    });

    sw_Saturaday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (doValidation()) {

          if (isChecked) {
            doctorTimeModel7.setStartTime(startTimeSaturaday);
            doctorTimeModel7.setEndTime(endTimeSaturaday);
            doctorTimeModel7.setDay("Sat");
            doctorTimeModel7.setDay_Id("7");
            doctorTimeModel7.setTimeSlot(timeSlot);
            selectDoctorTimeModelArrayList.add(doctorTimeModel7);
            state = "7";
            disableFields(state);
            Log.d("You are :", "Checked");
          }
        } else {
          state = "7";
          EnableFields(state);
          selectDoctorTimeModelArrayList.remove(doctorTimeModel7);

          Log.d("You are :", " Not Checked");
        }
      }
    });

    from_iv_Sunday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "1";
        day = "Sun";
        showTimnePickerDialog();
      }
    });
    to_iv_Sunday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "1";
        day = "Sun";
        showTimnePickerDialog();
      }
    });
    from_iv_Monday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        status = "2";
        day = "Mon";
        isStartDate = true;
        showTimnePickerDialog();
      }
    });
    to_iv_Monday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "2";
        day = "Mon";
        showTimnePickerDialog();
      }
    });

    from_iv_Tuesday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "3";
        day = "Tue";
        showTimnePickerDialog();
      }
    });
    to_iv_Tuesday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "3";
        day = "Tue";
        showTimnePickerDialog();
      }
    });

    from_iv_Wednesday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "4";
        day = "Wed";
        showTimnePickerDialog();
      }
    });
    to_iv_Wednesday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "4";
        day = "Wed";

        showTimnePickerDialog();
      }
    });

    from_iv_Thirsday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "5";
        day = "Thu";

        showTimnePickerDialog();
      }
    });
    to_iv_Thirsday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "5";
        day = "Thu";

        showTimnePickerDialog();
      }
    });

    from_iv_Friday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "6";
        day = "Fri";

        showTimnePickerDialog();
      }
    });
    to_iv_Friday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "6";
        day = "Fri";

        showTimnePickerDialog();
      }
    });

    from_iv_Saturaday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = true;
        status = "7";
        day = "Sat";
        showTimnePickerDialog();
      }
    });
    to_iv_Saturaday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isStartDate = false;
        status = "7";
        day = "Sat";
        showTimnePickerDialog();
      }
    });
  }

  private void callAddTimeSlot() {
    String day = String.valueOf(getDay());
    String startTime = String.valueOf(getStarTime());
    String endTime = String.valueOf(getEndTime());

    Call<SuccessModel> call = apiInterface.addNewTimeSlot(doctor_id, user_id, dayStatus, timeSlot, day, startTime, endTime);

    call.enqueue(new Callback<SuccessModel>() {
      @Override
      public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

        String str_response = new Gson().toJson(response.body());
        Log.e("" + TAG, "Response<<<<<<<<<<  " + str_response);

        try {
          if (response.isSuccessful()) {
            SuccessModel BusinessModel = response.body();
            String message = null, errorCode = null;

            if (BusinessModel != null) {
              message = BusinessModel.getMsg();
              errorCode = BusinessModel.getError_code();

              if (errorCode.equalsIgnoreCase("1")) {
                successMessageDialog.showDialog("Business Hours Add succesfully ");
                //finish();
              } else {
                errorMessageDialog.showDialog("Parameter is missing");
              }
            }
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(Call<SuccessModel> call, Throwable t) {

      }
    });
  }

  private boolean doValidation() {
    if (status == "1") {
      if (startTimeSunday == null || endTimeSunday == null) {
        errorMessageDialog.showDialog("please select Sunday Start And End Time");
        return false;
      }
      return true;
    }

    if (status == "2") {
      if (startTimeMonday == null || endTimeMonday == null) {
        errorMessageDialog.showDialog("please select Monday Start And End Time");
        return false;
      }
      return true;
    }

    if (status == "3") {
      if (startTimeTuesday == null || endTimeTuesday == null) {
        errorMessageDialog.showDialog("please select Tuesday Start And End Time");
        return false;
      }
      return true;
    }

    if (status == "4") {
      if (startTimeWednesday == null || endTimeWednesday == null) {
        errorMessageDialog.showDialog("please select Wednesday Start And End Time");
        return false;
      }
      return true;
    }
    if (status == "5") {
      if (startTimeThirsday == null || endTimeThirsday == null) {
        errorMessageDialog.showDialog("please select Thursday Start And End Time");
        return false;
      }
      return true;
    }
    if (status == "6") {
      if (startTimeFriday == null || endTimeFriday == null) {
        errorMessageDialog.showDialog("please select Friday Start And End Time");
        return false;
      }
      return true;
    }
    if (status == "7") {
      if (startTimeSaturaday == null || endTimeSaturaday == null) {
        errorMessageDialog.showDialog("please select saturday Start And End Time");
        return false;
      }
      return true;
    } else if (((selectDoctorTimeModelArrayList.size() != 0) && (timeSlot == null))) {
      errorMessageDialog.showDialog("Please Select Time Slot");
      return false;
    }

    return true;
  }


  private void showTimnePickerDialog() {
    final Calendar myCalender = Calendar.getInstance();
    int hour = myCalender.get(Calendar.HOUR_OF_DAY);
    int minute = myCalender.get(Calendar.MINUTE);

    TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {

          try {
            if (status == "1") {
              if (isStartDate) {
                from_iv_Sunday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeSunday = h + ":" + m;

                //  Toast.makeText(BusinessHourActivity.this,"Time in minute"+startTimeSunday,Toast.LENGTH_LONG).show();


              } else {
                to_iv_Sunday.setText((hourOfDay + ":" + minute));
                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeSunday = h + ":" + m;

                //  Toast.makeText(BusinessHourActivity.this,"Time in minute"+endTimeSunday,Toast.LENGTH_LONG).show();


              }
            }

            if (status == "2") {
              if (isStartDate) {
                from_iv_Monday.setText((hourOfDay + ":" + minute));
                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeMonday = h + ":" + m;

              } else {
                to_iv_Monday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeMonday = h + ":" + m;
              }
            }

            if (status == "3") {
              if (isStartDate) {
                from_iv_Tuesday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeTuesday = h + ":" + m;

              } else {
                to_iv_Tuesday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeTuesday = h + ":" + m;

              }
            }

            if (status == "4") {
              if (isStartDate) {
                from_iv_Wednesday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeWednesday = h + ":" + m;

              } else {
                to_iv_Wednesday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeWednesday = h + ":" + m;

              }
            }

            if (status == "5") {
              if (isStartDate) {
                from_iv_Thirsday.setText((hourOfDay + ":" + minute));
                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeThirsday = h + ":" + m;

              } else {
                to_iv_Thirsday.setText((hourOfDay + ":" + minute));
                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeThirsday = h + ":" + m;
              }
            }

            if (status == "6") {
              if (isStartDate) {
                from_iv_Friday.setText((hourOfDay + ":" + minute));
                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeFriday = h + ":" + m;

              } else {
                to_iv_Friday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeFriday = h + ":" + m;

              }
            }

            if (status == "7") {
              if (isStartDate) {
                from_iv_Saturaday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converstartminute = hours + minute;

                int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                startTimeSaturaday = h + ":" + m;


              } else {
                to_iv_Saturaday.setText((hourOfDay + ":" + minute));

                hours = hourOfDay * 60;
                converendminute = hours + minute;

                int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                endTimeSaturaday = h + ":" + m;

              }
            }

          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      }
    };

    TimePickerDialog timePickerDialog = new TimePickerDialog(BusinessHourActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
    timePickerDialog.setTitle("Choose hour:");
    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    timePickerDialog.show();
  }

  private void disableFields(String state) {
    if (state == "1") {
      from_iv_Sunday.setEnabled(false);
      to_iv_Sunday.setEnabled(false);

      to_iv_Sunday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Sunday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "2") {
      from_iv_Monday.setEnabled(false);
      to_iv_Monday.setEnabled(false);

      to_iv_Monday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Monday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "3") {
      from_iv_Tuesday.setEnabled(false);
      to_iv_Tuesday.setEnabled(false);

      to_iv_Tuesday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Tuesday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "4") {
      from_iv_Wednesday.setEnabled(false);
      to_iv_Wednesday.setEnabled(false);

      to_iv_Wednesday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Wednesday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "5") {
      from_iv_Thirsday.setEnabled(false);
      to_iv_Thirsday.setEnabled(false);

      to_iv_Thirsday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Thirsday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "6") {
      from_iv_Friday.setEnabled(false);
      to_iv_Friday.setEnabled(false);

      to_iv_Friday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Friday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    } else if (state == "7") {
      from_iv_Saturaday.setEnabled(false);
      to_iv_Saturaday.setEnabled(false);

      to_iv_Saturaday.setTextColor(getResources().getColor(android.R.color.darker_gray));
      from_iv_Saturaday.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }
  }

  private void EnableFields(String state) {
    if (state == "1") {
      from_iv_Sunday.setEnabled(true);
      to_iv_Sunday.setEnabled(true);

      to_iv_Sunday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Sunday.setTextColor(getResources().getColor(android.R.color.black));

    } else if (state == "2") {
      from_iv_Monday.setEnabled(true);
      to_iv_Monday.setEnabled(true);

      to_iv_Monday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Monday.setTextColor(getResources().getColor(android.R.color.black));

    } else if (state == "3") {
      from_iv_Tuesday.setEnabled(true);
      to_iv_Tuesday.setEnabled(true);

      to_iv_Tuesday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Tuesday.setTextColor(getResources().getColor(android.R.color.black));

    } else if (state == "4") {
      from_iv_Wednesday.setEnabled(true);
      to_iv_Wednesday.setEnabled(true);

      to_iv_Wednesday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Wednesday.setTextColor(getResources().getColor(android.R.color.black));
    } else if (state == "5") {
      from_iv_Thirsday.setEnabled(true);
      to_iv_Thirsday.setEnabled(true);

      to_iv_Thirsday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Thirsday.setTextColor(getResources().getColor(android.R.color.black));

    } else if (state == "6") {
      from_iv_Friday.setEnabled(true);
      to_iv_Friday.setEnabled(true);

      to_iv_Friday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Friday.setTextColor(getResources().getColor(android.R.color.black));

    } else if (state == "7") {
      from_iv_Saturaday.setEnabled(true);
      to_iv_Saturaday.setEnabled(true);

      to_iv_Saturaday.setTextColor(getResources().getColor(android.R.color.black));
      from_iv_Saturaday.setTextColor(getResources().getColor(android.R.color.black));

    }
  }


  private JSONArray getDay() {
    List<String> dayList = new ArrayList<>();
    for (int i = 0; i < selectDoctorTimeModelArrayList.size(); i++) {
      dayList.add(selectDoctorTimeModelArrayList.get(i).getDay());
    }
    JSONArray pJsonArray = new JSONArray(dayList)                                                                           ;
    return pJsonArray;
  }

  private JSONArray getStarTime() {
    List<String> startTimeList = new ArrayList<>();
    for (int i = 0; i < selectDoctorTimeModelArrayList.size(); i++) {
      startTimeList.add(selectDoctorTimeModelArrayList.get(i).getStartTime());
    }
    JSONArray pJsonArray = new JSONArray(startTimeList);
    return pJsonArray;
  }

  private JSONArray getEndTime() {
    List<String> endTimeList = new ArrayList<>();
    for (int i = 0; i < selectDoctorTimeModelArrayList.size(); i++) {
      endTimeList.add(selectDoctorTimeModelArrayList.get(i).getEndTime());
    }
    JSONArray pJsonArray = new JSONArray(endTimeList);
    return pJsonArray;
  }


}
