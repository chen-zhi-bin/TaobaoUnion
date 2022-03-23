package com.program.taobaounion.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.program.taobaounion.R;
import com.program.taobaounion.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG,"title-->"+item.getTitle()+"id-->"+item.getItemId());
                switch (item.getItemId()){
                    case R.id.home:
                        //TODO:
                        Log.d(TAG,"切换到首页");
                        break;
                    case R.id.selected:
                        Log.d(TAG,"切换到精选");
                        break;
//                    case R.id.red_packet:
//                        Log.d(TAG,"切换到特惠");
//                        break;
//                    case R.id.search:
//                        Log.d(TAG,"切换到搜索");
//                        break;
                }
                return true;
            }
        });
    }

    private void initView() {
        mNavigationView = this.findViewById(R.id.main_navigation_bar);

        HomeFragment homeFragment=new HomeFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_page_container,homeFragment);

        transaction.commit();


    }
}