package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.interfaceutils.SelectPatientInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.MedicineListModel;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.ServiceModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.model.TestListModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.MedicineListAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.MyPatientDialogAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.MyServiceDialogAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.TestListAdapter;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class PrescriptionActivity extends AppCompatActivity implements SelectPatientInterface {

    private ImageView iv_close, imgHospitalService, imgSelectPatient;
    private RecyclerView recyclerView_medicine_list, recyclerView_test_list;
    private ApiInterface apiInterface;
    private String user_id, doctor_id, TAG = getClass().getSimpleName(), service_id = "", patient_id, symptomName, diagnosisName,title;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ArrayList<MedicineListModel> medicineListModelArrayList = new ArrayList<>();
    private ArrayList<TestListModel> testListModelArrayList = new ArrayList<>();
    private ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    public static PatientModel patientModel = null;
    private MyPatientDialogAdapter myPatientDialogAdapter;
    private Dialog selectServiceDialog, selectPatientDialog;
    private MyServiceDialogAdapter myServiceDialogAdapter;
    public static ServiceModel serviceModel = null;
    private ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
    private RecyclerView recyclerDialogService, recyclerDialogSC;
    private LinearLayout linearService, linearPatient;
    private TextView textServiceName;
    private ShowProgress showProgress;
    private SelectPatientInterface selectPatientInterface;
    private AutoCompleteTextView textHospitalPatient;
    private LinearLayout linearLayoutMedicine, linearLayoutTest, linearLayoutManual, linearLayoutPrescriptionPhoto;
    private Button btnOkDialogService, btnCancelDialogService, btnOkDialogSC, btnCancelDialogSC, submitPrescription;
    private FloatingActionButton fabAddMedicine, fabAddTest;
    private MedicineListAdapter medicineListAdapter;
    private TestListAdapter testListAdapter;
    static int index = 1;
    static int indexTest = 1;
    private RadioButton radioSexButton;
    private RadioGroup rGenderGroup;
    private String prescriptionType;
    private ImageView iv_prescription_image;
    private TextInputEditText textTitle,edtMedicineName, edtMedicineNote, edtTestNote, edtTestName, edtSymptomName, edtDiagnosisName;
    private EditText editSearchService, editSearchCPatient;

    // =========== Upload image ================
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_GALLERY_REQUEST_CODE = 2;
    Bitmap bitmap;
    Uri fileUri;
    Uri imageUrl;
    String photo;
    private boolean isSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        selectPatientDialog = new Dialog(this);
        showProgress = new ShowProgress(PrescriptionActivity.this);


        selectPatientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectServiceDialog = new Dialog(this);
        selectServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPatientDialog.setContentView(R.layout.dialog_select_patient);
        selectServiceDialog.setContentView(R.layout.dialog_select_service);
        iv_close = findViewById(R.id.iv_close);
        imgHospitalService = findViewById(R.id.imgHospitalService);
        fabAddMedicine = findViewById(R.id.fabAddMedicine);
        textHospitalPatient = findViewById(R.id.textHospitalPatient);
        recyclerView_test_list = findViewById(R.id.recyclerView_test_list);
        edtSymptomName = findViewById(R.id.edtSymptomName);
        edtDiagnosisName = findViewById(R.id.edtDiagnosisName);
        textTitle = findViewById(R.id.textTitle);

        linearLayoutManual = findViewById(R.id.linearLayoutManual);
        linearLayoutPrescriptionPhoto = findViewById(R.id.linearLayoutPrescriptionPhoto);
        iv_prescription_image = findViewById(R.id.iv_prescription_image);


        edtMedicineName = findViewById(R.id.edtMedicineName);
        edtMedicineNote = findViewById(R.id.edtMedicineNote);
        edtTestNote = findViewById(R.id.edtTestNote);
        edtTestName = findViewById(R.id.edtTestName);
        linearLayoutMedicine = findViewById(R.id.linearLayoutMedicine);
        recyclerView_medicine_list = findViewById(R.id.recyclerView_medicine_list);
        fabAddTest = findViewById(R.id.fabAddTest);
        linearLayoutTest = findViewById(R.id.linearLayoutTest);
        submitPrescription = findViewById(R.id.submitPrescription);
        rGenderGroup = findViewById(R.id.rGenderGroup);

        // Patient Dialog Configuration
        imgSelectPatient = findViewById(R.id.imgSelectPatient);
        linearPatient = findViewById(R.id.linearPatient);
        btnOkDialogSC = selectPatientDialog.findViewById(R.id.btnOkDialogSC);
        btnCancelDialogSC = selectPatientDialog.findViewById(R.id.btnCancelDialogSC);
        editSearchCPatient = selectPatientDialog.findViewById(R.id.editSearchCPatient);
        selectPatientDialog.setCancelable(false);
        recyclerDialogSC = selectPatientDialog.findViewById(R.id.recyclerDialogSC);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerDialogSC.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager_recyclerView_medicine_list = new LinearLayoutManager(getApplicationContext());
        recyclerView_medicine_list.setLayoutManager(linearLayoutManager_recyclerView_medicine_list);
        recyclerView_medicine_list.hasFixedSize();

        LinearLayoutManager linearLayoutManager_recyclerView_test_list = new LinearLayoutManager(getApplicationContext());
        recyclerView_test_list.setLayoutManager(linearLayoutManager_recyclerView_test_list);
        recyclerView_test_list.hasFixedSize();

        // Service Dialog Configuration
        linearService = findViewById(R.id.linearService);
        imgHospitalService = findViewById(R.id.imgHospitalService);
        textServiceName = findViewById(R.id.textServiceName);
        // textServiceCost =  findViewById(R.id.textServiceCost);

        btnOkDialogService = selectServiceDialog.findViewById(R.id.btnOkDialogService);
        btnCancelDialogService = selectServiceDialog.findViewById(R.id.btnCancelDialogService);
        editSearchService = selectServiceDialog.findViewById(R.id.editSearchService);
        selectServiceDialog.setCancelable(false);
        recyclerDialogService = selectServiceDialog.findViewById(R.id.recyclerDialogService);
        LinearLayoutManager layoutManagerService = new LinearLayoutManager(getApplicationContext());
        recyclerDialogService.setLayoutManager(layoutManagerService);


        errorMessageDialog = new ErrorMessageDialog(PrescriptionActivity.this);
        successMessageDialog = new SuccessMessageDialog(PrescriptionActivity.this);

        doctorModelArrayList = SharedUtils.getUserDetails(PrescriptionActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

    }


    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgSelectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callListPatient();
            }
        });

        btnOkDialogSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCPatient.setText("");
                selectPatientDialog.dismiss();
                if (patientModel != null) {
                    setPatientData();
                    if (patientModelArrayList.size() > 0) {
                        /*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*/
                    }
                    linearPatient.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearPatient.setVisibility(View.GONE);
                }
            }
        });

        btnCancelDialogSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientModel = null;

                linearPatient.setVisibility(View.GONE);
                editSearchCPatient.setText("");
                selectPatientDialog.dismiss();
            }
        });
        imgHospitalService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServiceList();
            }
        });

        btnOkDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchService.setText("");
                selectServiceDialog.dismiss();
                if (serviceModel != null) {
                    setServiceData();
                    if (serviceModelArrayList.size() > 0) {
                        /*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*/
                    }
                    linearService.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearService.setVisibility(View.GONE);
                }

            }
        });

        btnCancelDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceModel = null;
                linearService.setVisibility(View.GONE);
                editSearchService.setText("");
                selectServiceDialog.dismiss();
            }
        });

        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicine();
            }
        });

        fabAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTest();
            }
        });

        submitPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidation()) {

                    if (prescriptionType.equalsIgnoreCase("Manual")) {
                        if (NetworkUtils.isNetworkAvailable(PrescriptionActivity.this))
                            addPrescription();
                        else
                            NetworkUtils.isNetworkNotAvailable(PrescriptionActivity.this);
                    }else if(prescriptionType.equalsIgnoreCase("Photo"))
                    {
                        if (NetworkUtils.isNetworkAvailable(PrescriptionActivity.this))
                            addPrescriptionPhoto();
                        else
                            NetworkUtils.isNetworkNotAvailable(PrescriptionActivity.this);
                    }

                }
            }
        });


        rGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton = findViewById(checkedId);
                prescriptionType = radioSexButton.getText().toString();
                if(prescriptionType.equalsIgnoreCase("Photo"))
                {
                    linearLayoutPrescriptionPhoto.setVisibility(View.VISIBLE);
                    linearLayoutManual.setVisibility(View.GONE);
                    showPictureDialog();
                }else if(prescriptionType.equalsIgnoreCase("Manual"))
                {
                    linearLayoutManual.setVisibility(View.VISIBLE);
                    linearLayoutPrescriptionPhoto.setVisibility(View.GONE);
                }

            }
        });

        linearLayoutPrescriptionPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        editSearchCPatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myPatientDialogAdapter.getFilter().filter(editSearchCPatient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editSearchService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myServiceDialogAdapter.getFilter().filter(editSearchService.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private boolean isValidation() {

        symptomName = edtSymptomName.getText().toString().trim();
        diagnosisName = edtDiagnosisName.getText().toString().trim();
        title=textTitle.getText().toString().trim();

        if ((patient_id==null)||(patient_id.equalsIgnoreCase(""))) {
            errorMessageDialog.showDialog("Please Select Patient");
            return false;
        }
        if ((prescriptionType==null)||(prescriptionType.equalsIgnoreCase(""))) {
            errorMessageDialog.showDialog("Please Select Prescription Type");
            return false;
        } else if (prescriptionType.equalsIgnoreCase("Manual")) {
            linearLayoutManual.setVisibility(View.VISIBLE);
            if (symptomName.equalsIgnoreCase("")) {
                errorMessageDialog.showDialog("Please Enter Symptom");
                return false;
            } else if (diagnosisName.equalsIgnoreCase("")) {
                errorMessageDialog.showDialog("Please Enter Diagnosis");
                return false;
            } else if (medicineListModelArrayList.size() <= 0) {
                errorMessageDialog.showDialog("Please Enter Medicine");
                return false;
            } else if (testListModelArrayList.size() <= 0) {
                errorMessageDialog.showDialog("Please Enter Test");
                return false;
            }
        } else if (prescriptionType.equalsIgnoreCase("Photo")) {

            linearLayoutPrescriptionPhoto.setVisibility(View.VISIBLE);

            if((title==null)||title.equalsIgnoreCase(""))
            {
                errorMessageDialog.showDialog("Please Enter Title");
                return false;

            } else if ((photo==null)||(photo.equalsIgnoreCase(""))) {
                errorMessageDialog.showDialog("Please Select Photo");
                return false;
            }

        }

        return true;
    }

    private void setPatientData() {
        textHospitalPatient.setText(patientModel.getName());
        patient_id = patientModel.getPatient_id();
    }

    private void callListPatient() {
        //  showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientList(doctor_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        patientModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                patientModelArrayList = successModel.getPatientModelArrayList();

                                if (patientModelArrayList.size() != 0) {

                                    myPatientDialogAdapter = new MyPatientDialogAdapter(getApplicationContext(), patientModelArrayList, true, selectPatientInterface);

                                    selectPatientDialog.show();
                                    Window window = selectPatientDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogSC.setAdapter(myPatientDialogAdapter);
                                    myPatientDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogSC.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

    private void callServiceList() {
        Call<SuccessModel> call = apiInterface.serviceList(doctor_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                //  showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        serviceModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                serviceModelArrayList = successModel.getServiceModelArrayList();

                                if (serviceModelArrayList.size() != 0) {

                                    myServiceDialogAdapter = new MyServiceDialogAdapter(getApplicationContext(), serviceModelArrayList, true, selectPatientInterface);

                                    selectServiceDialog.show();
                                    Window window = selectServiceDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogService.setAdapter(myServiceDialogAdapter);
                                    myServiceDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogService.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
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


    public void addMedicine() {


        String medicineName = edtMedicineName.getText().toString();
        String medicineNote = edtMedicineNote.getText().toString();


        if (medicineName.equalsIgnoreCase("") && medicineNote.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Enter Medicine and  Note");
        } else {
            edtMedicineName.setText("");
            edtMedicineNote.setText("");

            medicineListModelArrayList.add(new MedicineListModel(index, medicineName, medicineNote));
            index++;
            linearLayoutMedicine.setVisibility(View.VISIBLE);
            medicineListAdapter = new MedicineListAdapter(PrescriptionActivity.this, medicineListModelArrayList);
            recyclerView_medicine_list.setAdapter(medicineListAdapter);
            medicineListAdapter.notifyDataSetChanged();
        }
    }


    public void addTest() {
        String testName = edtTestName.getText().toString();
        String testNote = edtTestNote.getText().toString();

        if (testName.equalsIgnoreCase("") && testNote.equalsIgnoreCase("")) {
            errorMessageDialog.showDialog("Please Enter Test and  Note");
        } else {
            edtTestName.setText("");
            edtTestNote.setText("");
            testListModelArrayList.add(new TestListModel(indexTest, testName, testNote));
            indexTest++;
            linearLayoutTest.setVisibility(View.VISIBLE);
            testListAdapter = new TestListAdapter(PrescriptionActivity.this, testListModelArrayList);
            recyclerView_test_list.setAdapter(testListAdapter);
            testListAdapter.notifyDataSetChanged();
        }
    }


    private void setServiceData() {

        service_id = serviceModel.getService_id();
        textServiceName.setText(serviceModel.getS_name());
    }

    @Override
    public void getPatientDetail(PatientModel patientModel) {

    }

    @Override
    public void getServiceDetail(ServiceModel serviceModel) {

    }


    private void addPrescription() {

        String medicineName = String.valueOf(getMedicineName());
        String medicineNote = String.valueOf(getMedicineNote());
        String testName = String.valueOf(getTestName());
        String testNote = String.valueOf(getTestNote());

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.addPrescription(doctor_id, patient_id, user_id, service_id, symptomName, diagnosisName, medicineName, medicineNote, testName, testNote);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response>>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        String errorCode = null, msg = null;

                        if (successModel != null) {
                            errorCode = successModel.getError_code();
                            msg = successModel.getMsg();
                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Prescription Add Successfully");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();

            }
        });
    }


    private JSONArray getMedicineName() {
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < medicineListModelArrayList.size(); i++) {
            mList.add(medicineListModelArrayList.get(i).getMedicineName());
        }
        JSONArray pJsonArray = new JSONArray(mList);
        return pJsonArray;
    }

    private JSONArray getMedicineNote() {
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < medicineListModelArrayList.size(); i++) {
            mList.add(medicineListModelArrayList.get(i).getMedicineTest());
        }
        JSONArray pJsonArray = new JSONArray(mList);
        return pJsonArray;
    }

    private JSONArray getTestName() {
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < testListModelArrayList.size(); i++) {
            mList.add(testListModelArrayList.get(i).getTestName());
        }
        JSONArray pJsonArray = new JSONArray(mList);
        return pJsonArray;
    }

    private JSONArray getTestNote() {
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < testListModelArrayList.size(); i++) {
            mList.add(testListModelArrayList.get(i).getTestNote());
        }
        JSONArray pJsonArray = new JSONArray(mList);
        return pJsonArray;
    }

    // ===================================*** Image Upload Data ***=================================
    private void showPictureDialog() {
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(PrescriptionActivity.this);
        pictureDialog.setTitle("Select Action");
        pictureDialog.setIcon(R.drawable.photo_camera);
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera", "Cancel"};
        pictureDialog.setCancelable(false);

        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takeFromGallery();
                        break;
                    case 1:
                        takeFromCamera();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takeFromCamera() {
        // Check if this device has a camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // Open default camera
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);             // start the image capture Intent
            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);        //100
        } else {
            // no camera on this device
            Toast.makeText(PrescriptionActivity.this, "Camera not supported", LENGTH_LONG).show();
        }
    }

    private void takeFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MY_GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            imageUrl = data.getData();
            if (requestCode == MY_CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");

            } else {
                bitmap = MediaStore.Images.Media.getBitmap(PrescriptionActivity.this.getContentResolver(), imageUrl);
            }
            // ==== User Defined Method ======
            convertToBase64(bitmap); //converting image to base64 string

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertToBase64(final Bitmap bitmap) {
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bAOS);
        byte[] imageBytes = bAOS.toByteArray();
        photo = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv_prescription_image.setImageBitmap(decodedByte);
        isSelect = true;

    }

    // ===========*** End Image Upload Data ***=============


    private void addPrescriptionPhoto() {

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.addPrescriptionDocument(doctor_id,user_id, service_id,patient_id,photo,title);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response>>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        String errorCode = null, msg = null;

                        if (successModel != null) {
                            errorCode = successModel.getError_code();
                            msg = successModel.getMsg();
                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Prescription Document Add Successfully");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();

            }
        });
    }

}
