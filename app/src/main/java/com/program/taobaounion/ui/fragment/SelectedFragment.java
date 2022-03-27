package com.program.taobaounion.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;

public class SelectedFragment extends BaseFragment {


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selecter;
    }

    protected void initView(View rootView) {
        setupState(State.SUCCESS);
    }
}
