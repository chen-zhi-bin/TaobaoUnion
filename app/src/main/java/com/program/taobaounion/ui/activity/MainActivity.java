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
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.ui.fragment.HomeFragment;
import com.program.taobaounion.ui.fragment.RedPacketFragment;
import com.program.taobaounion.ui.fragment.SearchFragment;
import com.program.taobaounion.ui.fragment.SelectedFragment;
import com.program.taobaounion.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

 public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SearchFragment mSearchFragment;
    private SelectedFragment mSelectedFragment;
    private FragmentManager mFm;
     private Unbinder mBind;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ui绑定
         mBind = ButterKnife.bind(this);
        initFragments();
        initListener();
    }

    private void initFragments() {
        mFm = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
        mSelectedFragment = new SelectedFragment();
        //默认选中推荐，不然会出现空白页
        switchFragment(mHomeFragment);
    }

     @Override
     protected void onDestroy() {
         super.onDestroy();
         if (mBind != null) {
             mBind.unbind();
         }
     }

     private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        LogUtils.d(this,"切换到首页");
                        switchFragment(mHomeFragment);
                        break;
                    case R.id.selected:
                        LogUtils.d(this,"切换到精选");
                        switchFragment(mSelectedFragment);
                        break;
                    case R.id.red_packet:
                        LogUtils.d(this,"切换到特惠");
                        switchFragment(mRedPacketFragment);
                        break;
                    case R.id.search:
                        LogUtils.d(this,"切换到搜索");
                        switchFragment(mSearchFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void switchFragment(BaseFragment targetFragment) {
        //开启事务
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.replace(R.id.main_page_container,targetFragment);
        //提交事务
        fragmentTransaction.commit();
    }

}