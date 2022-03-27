package com.program.taobaounion.ui.fragment;

import android.view.View;

import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;

public class RedPacketFragment extends BaseFragment {

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_red_packeet;
    }

    protected void initView(View rootView) {
        setupState(State.SUCCESS);
    }
}
