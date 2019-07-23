package com.nikvay.doctorapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.DrawerItem;
import com.nikvay.doctorapplication.utils.LogoutApplicationDialog;
import com.nikvay.doctorapplication.utils.RecyclerItemClickListener;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.AdminMainActivity;
import com.nikvay.doctorapplication.view.activity.common_activity.LoginActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.EnquiryActivity;
import com.nikvay.doctorapplication.view.activity.doctor_activity.PatientActivity;
import com.nikvay.doctorapplication.view.adapter.DrawerItemAdapter;
import com.nikvay.doctorapplication.view.fragment.doctor_fragment.AppointmentFragment;
import com.nikvay.doctorapplication.view.fragment.doctor_fragment.HomeFragment;
import com.nikvay.doctorapplication.view.fragment.doctor_fragment.NotificationFragment;
import com.nikvay.doctorapplication.view.fragment.doctor_fragment.ProfileFragment;
import com.nikvay.doctorapplication.view.fragment.doctor_fragment.SettingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{


    private DrawerLayout drawerLayout;
    private ImageView iv_menu_toolbar, iv_notification;
    private String fragmentName = null, doctor_id, is_super_admin;
    private Fragment fragmentInstance;
    private FragmentManager fragmentManager;
    private TextView textTitleName, textName, textEmail;

    private boolean doubleBackToExitPressedOnce = false;
    ArrayList<DrawerItem> drawerItemArrayList;
    public static RecyclerView.Adapter drawerItemAdapter;
    private RecyclerView recyclerViewDrawer;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private LinearLayout ll_header_profile;
    private String doctorName, email;

    //Logout Application
    private Dialog dialog;
    private TextView txt_cancel, txt_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        find_All_IDs();
        events();


    }

    private void find_All_IDs() {
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu_toolbar = findViewById(R.id.iv_menu_toolbar);
        recyclerViewDrawer = findViewById(R.id.recyclerViewDrawer);
        iv_notification = findViewById(R.id.iv_notification);
        textTitleName = findViewById(R.id.textTitleName);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        ll_header_profile = findViewById(R.id.ll_header_profile);

        doctorModelArrayList = SharedUtils.getUserDetails(MainActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        doctorName = doctorModelArrayList.get(0).getName();
        email = doctorModelArrayList.get(0).getEmail();
        is_super_admin = doctorModelArrayList.get(0).getIs_super_admin();
        textName.setText(doctorName);
        //textEmail.setText(email);

        //Handle Null Pointer
        is_super_admin = is_super_admin == null ? "" : is_super_admin;


        drawerItemArrayList = new ArrayList<>();
        drawerItemArrayList.add(new DrawerItem(R.drawable.home, StaticContent.DrawerItem.DASHBOARD));
        drawerItemArrayList.add(new DrawerItem(R.drawable.profile_image, StaticContent.DrawerItem.MY_ACCOUNT));
        if (is_super_admin.equalsIgnoreCase("1")) {
            drawerItemArrayList.add(new DrawerItem(R.drawable.admin, StaticContent.DrawerItem.ADMIN_PANEL));
        }
        drawerItemArrayList.add(new DrawerItem(R.drawable.appointment, StaticContent.DrawerItem.APPOINTMENT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.my_customer, StaticContent.DrawerItem.MY_PATIENT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_settings, StaticContent.DrawerItem.SETTINGS));
        drawerItemArrayList.add(new DrawerItem(R.drawable.enquiry, StaticContent.DrawerItem.ENQUIRY));

        drawerItemArrayList.add(new DrawerItem(R.drawable.logout, StaticContent.DrawerItem.LOGOUT));

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewDrawer.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        drawerItemAdapter = new DrawerItemAdapter(MainActivity.this, drawerItemArrayList);
        recyclerViewDrawer.setAdapter(drawerItemAdapter);
        drawerItemAdapter.notifyDataSetChanged();


        //Logout Application
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        txt_cancel = dialog.findViewById(R.id.txt_cancel);
        txt_logout = dialog.findViewById(R.id.txt_logout);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);




        loadFragment(new HomeFragment());

        Bundle bundle = getIntent().getExtras();

        Intent intent = getIntent();

        if (intent != null) {
            String title = intent.getStringExtra("TITLE");
            String description = intent.getStringExtra("DESCRIPTION");
            String redirectId = intent.getStringExtra("REDIRECT_ID");
            // Toast.makeText(this, redirectId, Toast.LENGTH_SHORT).show();
            redirectNotification(redirectId);
        }


    }


    public void loadFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();
        switch (fragmentName) {

            case "NotificationFragment":
                textTitleName.setText(StaticContent.DrawerItem.NOTIFICATION);
                break;

            case "HomeFragment":
                textTitleName.setText(StaticContent.DrawerItem.DASHBOARD);
                break;

            case "PatientFragment":
                textTitleName.setText(StaticContent.DrawerItem.MY_PATIENT);
                break;


            case "ProfileFragment":
                textTitleName.setText(StaticContent.DrawerItem.MY_ACCOUNT);
                break;

            case "AppointmentFragment":
                textTitleName.setText(StaticContent.DrawerItem.APPOINTMENT);
                break;

            case "ActivityFragment":
                textTitleName.setText(StaticContent.DrawerItem.ACTIVITY);
                break;


            case "SettingFragment":
                textTitleName.setText(StaticContent.DrawerItem.SETTINGS);
                break;


            default:
                textTitleName.setText(" ");

        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }

    private void events() {
        iv_menu_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        recyclerViewDrawer.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        displayActivity(drawerItemArrayList.get(position).getCategoryType());
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                })
        );

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationFragment());
            }
        });

        ll_header_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                loadFragment(new ProfileFragment());
            }
        });


        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedUtils.removeSharedUtils(MainActivity.this);
                SharedUtils.clearShareUtils(MainActivity.this);
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void displayActivity(String name) {


        switch (name) {
            case StaticContent.DrawerItem.DASHBOARD:
                loadFragment(new HomeFragment());
                break;

            case StaticContent.DrawerItem.MY_ACCOUNT:
                loadFragment(new ProfileFragment());
                break;

            case StaticContent.DrawerItem.MY_PATIENT:
                Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                startActivity(intent);
                break;

            case StaticContent.DrawerItem.APPOINTMENT:
                loadFragment(new AppointmentFragment());
                break;

            case StaticContent.DrawerItem.SETTINGS:
                loadFragment(new SettingFragment());
                break;

            case StaticContent.DrawerItem.ENQUIRY:
                Intent intent_enquiry = new Intent(MainActivity.this, EnquiryActivity.class);
                startActivity(intent_enquiry);
                break;

            case StaticContent.DrawerItem.LOGOUT:
                dialog.show();
                break;

            case StaticContent.DrawerItem.ADMIN_PANEL:
                Intent admin_dashboard_intent = new Intent(MainActivity.this, AdminMainActivity.class);
                startActivity(admin_dashboard_intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentName.equals(fragmentInstance.getClass().getSimpleName())) {
                if (fragmentName.equals("HomeFragment")) {
                    doubleBackPressLogic();
                } else
                    loadFragment(new HomeFragment());
            }
        }
    }

    // ============ End Double tab back press logic =================
    private void doubleBackPressLogic() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private void redirectNotification(String redirectId) {

        if (redirectId != null) {
            switch (redirectId) {
                case "1":
                    loadFragment(new AppointmentFragment());
                    break;
            }
        }
    }

}
