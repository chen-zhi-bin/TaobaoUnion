package com.program.taobaounion.base;

import android.app.Application;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.program.taobaounion.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //把app变灰    <==>
//        ColorMatrix  cm = new ColorMatrix();
//        cm.setSaturation(0);        //设置饱和度
//        Paint paint = new Paint();
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        View contentContainer = getWindow().getDecorView();
//        contentContainer.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
        //            <==>

        mBind = ButterKnife.bind(this);
        initView();
        initEvent();
        initPresenter();
    }

    protected abstract void initPresenter();

    /**
     * 需要的时候覆写
     */
    protected void initEvent() {

    }

    protected abstract void initView();

    protected abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        this.relese();
    }

    /**
     * 释放资源
     */
    protected void relese() {

    }

}
