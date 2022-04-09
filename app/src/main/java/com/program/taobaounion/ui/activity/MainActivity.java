 package com.program.taobaounion.ui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseActivity;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.ui.fragment.HomeFragment;
import com.program.taobaounion.ui.fragment.OnSellFragment;
import com.program.taobaounion.ui.fragment.SearchFragment;
import com.program.taobaounion.ui.fragment.SelectedFragment;
import com.program.taobaounion.utils.LogUtils;

import butterknife.BindView;

 public class MainActivity extends BaseActivity implements IMainActivity{

    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private OnSellFragment mRedPacketFragment;
    private SearchFragment mSearchFragment;
    private SelectedFragment mSelectedFragment;
    private FragmentManager mFm;


     @Override
     protected void initPresenter() {

     }

     @Override
     protected void initEvent() {
         initListener();
     }

     @Override
     protected void initView() {
         initFragments();
     }

     @Override
     protected int getLayoutResId() {
         return R.layout.activity_main;
     }

     private void initFragments() {
        mFm = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mRedPacketFragment = new OnSellFragment();
        mSearchFragment = new SearchFragment();
        mSelectedFragment = new SelectedFragment();
        //默认选中推荐，不然会出现空白页
        switchFragment(mHomeFragment);
    }

     /**
      * 跳转到搜索见面
      */
    public void switch2Search(){
//         switchFragment(mSearchFragment);
         //切换导航栏的选中项
        mNavigationView.setSelectedItemId(R.id.search);
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

     /**
      * 上一次显示的Fragment
      */
    private BaseFragment lastOneFragment = null;

    private void switchFragment(BaseFragment targetFragment) {
        //如果上一个Fragment和当前要切换的fragment是同一个，那么不需要切换
        if (lastOneFragment == targetFragment){
            return;
        }
         //修改成add和hide的方式来控制Fragment
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        //如果没有添加就添加
        if (!targetFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_page_container,targetFragment);
        }else {
            fragmentTransaction.show(targetFragment);
        }
        if (lastOneFragment!=null){
            fragmentTransaction.hide(lastOneFragment);
        }
        lastOneFragment = targetFragment;
        fragmentTransaction.commit();


//        //开启事务
//        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
//        fragmentTransaction.replace(R.id.main_page_container,targetFragment);
//        //提交事务
//        fragmentTransaction.commit();
    }

}