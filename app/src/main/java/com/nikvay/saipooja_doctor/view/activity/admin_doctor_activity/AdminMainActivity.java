package com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.DrawerItem;
import com.nikvay.saipooja_doctor.utils.LogoutApplicationDialog;
import com.nikvay.saipooja_doctor.utils.RecyclerItemClickListener;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.fragment.admin_doctor_fragment.AdminHomeFragment;
import com.nikvay.saipooja_doctor.view.fragment.doctor_fragment.SettingFragment;
import com.nikvay.saipooja_doctor.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.view.adapter.DrawerItemAdapter;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    private DrawerLayout drawerAdmin_layout;
    private ImageView iv_menu_toolbar,iv_notification;
    private Fragment fragmentInstance;
    private FragmentManager fragmentManager;
    ArrayList<DrawerItem> drawerItemArrayList;
    private String fragmentName = null,doctor_id,is_super_admin;

    private boolean doubleBackToExitPressedOnce = false;
    public static RecyclerView.Adapter drawerItemAdapter;
    private RecyclerView recyclerViewDrawer;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private TextView textTitleName,textAdminName,textAdminEmail;
    private LinearLayout ll_header_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        drawerAdmin_layout = findViewById(R.id.drawerAdmin_layout);
        iv_menu_toolbar = findViewById(R.id.iv_menu_toolbar);
        iv_notification = findViewById(R.id.iv_notification);
        recyclerViewDrawer = findViewById(R.id.recyclerViewDrawer);
        textTitleName = findViewById(R.id.textTitleName);
        textAdminName = findViewById(R.id.textAdminName);
        textAdminEmail = findViewById(R.id.textAdminEmail);
        ll_header_profile = findViewById(R.id.ll_header_profile);


        doctorModelArrayList= SharedUtils.getUserDetails(AdminMainActivity.this);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();
        is_super_admin=doctorModelArrayList.get(0).getIs_super_admin();
        textAdminName.setText(doctorModelArrayList.get(0).getName());
        textAdminEmail.setText(doctorModelArrayList.get(0).getEmail());


        drawerItemArrayList = new ArrayList<>();
        drawerItemArrayList.add(new DrawerItem(R.drawable.home, StaticContent.DrawerItem.DASHBOARD));
        drawerItemArrayList.add(new DrawerItem(R.drawable.doctor, StaticContent.DrawerItemAdmin.MY_DASHBOARD));
        drawerItemArrayList.add(new DrawerItem(R.drawable.appointment, StaticContent.DrawerItemAdmin.APPOINTMENT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.my_customer, StaticContent.DrawerItemAdmin.PATIENT));
      //  drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_settings, StaticContent.DrawerItemAdmin.SETTINGS));
        drawerItemArrayList.add(new DrawerItem(R.drawable.logout, StaticContent.DrawerItem.LOGOUT));


        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminMainActivity.this);
        recyclerViewDrawer.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.addItemDecoration(new DividerItemDecoration(AdminMainActivity.this, DividerItemDecoration.VERTICAL));

        drawerItemAdapter = new DrawerItemAdapter(AdminMainActivity.this, drawerItemArrayList);
        recyclerViewDrawer.setAdapter(drawerItemAdapter);
        drawerItemAdapter.notifyDataSetChanged();



        loadAdminFragment(new AdminHomeFragment());



    }

    private void events() {
        iv_menu_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerAdmin_layout.openDrawer(GravityCompat.START);
            }
        });


        recyclerViewDrawer.addOnItemTouchListener(new RecyclerItemClickListener(AdminMainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        displayActivity(drawerItemArrayList.get(position).getCategoryType());
                        if (drawerAdmin_layout.isDrawerOpen(GravityCompat.START)) {
                            drawerAdmin_layout.closeDrawer(GravityCompat.START);
                        }
                    }
                })
        );

    }


    public void loadAdminFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();
        switch (fragmentName) {

            case "AdminHomeFragment":
                textTitleName.setText(StaticContent.DrawerItem.ADMIN_PANEL);
                iv_notification.setVisibility(View.GONE);
                break;


            case "DepartmentFragment":
                textTitleName.setText(StaticContent.DrawerItemAdmin.DEPARTMENT);
                iv_notification.setVisibility(View.GONE);
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

    private void displayActivity(String name) {


        switch (name) {
            case StaticContent.DrawerItemAdmin.DASHBOARD:
                loadAdminFragment(new AdminHomeFragment());
                break;

            case StaticContent.DrawerItemAdmin.MY_DASHBOARD:
                Intent intent_my_dashboard=new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent_my_dashboard);
                break;

            case StaticContent.DrawerItemAdmin.PATIENT:
                Intent intent_patient=new Intent(AdminMainActivity.this, AllPatientListActivity.class);
                startActivity(intent_patient);
                break;


            case StaticContent.DrawerItemAdmin.LOGOUT:
                logoutApplication();
                break;
            case  StaticContent.DrawerItemAdmin.SETTINGS:
                new SettingFragment();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (drawerAdmin_layout.isDrawerOpen(GravityCompat.START)) {
            drawerAdmin_layout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentName.equals(fragmentInstance.getClass().getSimpleName())) {
                if (fragmentName.equals("AdminHomeFragment")) {
                    doubleBackPressLogic();
                } else
                    loadAdminFragment(new AdminHomeFragment());
            }
        }
    }

    // ============ End Double tab back press logic =================
    private void doubleBackPressLogic() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(AdminMainActivity.this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
        else
            {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void logoutApplication()
    {
        LogoutApplicationDialog logout_application = new LogoutApplicationDialog(AdminMainActivity.this);
        logout_application.showDialog();
    }
}
