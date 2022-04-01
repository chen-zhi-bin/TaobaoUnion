package com.program.taobaounion.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.program.taobaounion.R;
import com.program.taobaounion.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

//测试
public class TestActivity extends Activity {

    @BindView(R.id.test_navigtion_bar)
    public RadioGroup navigationBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_test);
        ButterKnife.bind(this);
        initListenter();

    }

    public void showToast(View view){
        Toast.makeText(this,"测试..",Toast.LENGTH_SHORT).show();
    }

    private void initListenter() {

        navigationBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                LogUtils.d(TestActivity.class,"checkedId-->"+checkedId);
                switch (checkedId){
                    case R.id.test_home:
                        LogUtils.d(TestActivity.class,"切换到首页");
                        break;
                    case R.id.test_select:
                        LogUtils.d(TestActivity.class,"切换到精选");
                        break;
                    case R.id.test_red_packet:
                        LogUtils.d(TestActivity.class,"切换到特惠");
                        break;
                    case R.id.test_search:
                        LogUtils.d(TestActivity.class,"切换到搜索");
                        break;

                }
            }
        });
    }
}
