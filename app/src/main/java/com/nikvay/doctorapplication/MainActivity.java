package com.nikvay.doctorapplication;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.nikvay.doctorapplication.model.DrawerItem;
import com.nikvay.doctorapplication.utils.Logout_Application;
import com.nikvay.doctorapplication.utils.RecyclerItemClickListener;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.DrawerItemAdapter;
import com.nikvay.doctorapplication.view.fragment.HomeFragment;
import com.nikvay.doctorapplication.view.fragment.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ImageView iv_menu_toolbar;
    String fragmentName = null;
    Fragment fragmentInstance;
    FragmentManager fragmentManager;

    private boolean doubleBackToExitPressedOnce = false;
    ArrayList<DrawerItem> drawerItemArrayList;
    public static RecyclerView.Adapter drawerItemAdapter;
    private RecyclerView recyclerViewDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        find_All_IDs();
        loadFragment(new HomeFragment());
        events();


    }

    private void find_All_IDs() {
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu_toolbar=findViewById(R.id.iv_menu_toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        recyclerViewDrawer=findViewById(R.id.recyclerViewDrawer);




        drawerItemArrayList = new ArrayList<>();
        drawerItemArrayList.add(new DrawerItem(R.drawable.home, StaticContent.DrawerItem.HOME));
         drawerItemArrayList.add(new DrawerItem(R.drawable.profile_image, StaticContent.DrawerItem.MY_ACCOUNT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.logout, StaticContent.DrawerItem.LOGOUT));



        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewDrawer.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        drawerItemAdapter = new DrawerItemAdapter(MainActivity.this, drawerItemArrayList);
        recyclerViewDrawer.setAdapter(drawerItemAdapter);
        drawerItemAdapter.notifyDataSetChanged();


    }

    public void loadFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();
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
    }
    private void displayActivity(String name) {


        switch (name) {
            case StaticContent.DrawerItem.HOME:
                loadFragment(new HomeFragment());
                break;

            case StaticContent.DrawerItem.MY_ACCOUNT:
                loadFragment(new ProfileFragment());
                break;

            case StaticContent.DrawerItem.LOGOUT:
                logoutApplication();
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
    private void logoutApplication() {
        Logout_Application logout_application=new Logout_Application(MainActivity.this);
        logout_application.showDialog();
    }

}
