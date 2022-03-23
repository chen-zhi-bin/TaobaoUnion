package com.program.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.program.taobaounion.R;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          //创建view
           return loadRootView(inflater,container,savedInstanceState);
    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //得到id
        int resId = getRootViewResId();
        //返回view
        return inflater.inflate(resId, container, false);
    }

    protected abstract int getRootViewResId();
}
