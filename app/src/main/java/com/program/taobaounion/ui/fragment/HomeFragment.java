package com.program.taobaounion.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;
import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.model.domain.Categories;
import com.program.taobaounion.presenter.IHomePresenter;
import com.program.taobaounion.presenter.impl.HomePresenterImpl;
import com.program.taobaounion.ui.activity.IMainActivity;
import com.program.taobaounion.ui.activity.MainActivity;
import com.program.taobaounion.ui.activity.ScanQrCodeActivity;
import com.program.taobaounion.ui.adapter.HomePagerAdapter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.PresenterManager;
import com.program.taobaounion.view.IHomeCallback;
import com.vondear.rxfeature.activity.ActivityScanerCode;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout;
    private IHomePresenter mHomePresenter;

    @BindView(R.id.home_pager)
    public ViewPager homePager;

    @BindView(R.id.home_search_input_box)
    public View mSearchInputBox;

    @BindView(R.id.scan_icon)
    public View scanBtn;

    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(homePager);
        //给ViewPager设置适配器
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        homePager.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected void initListener() {
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到扫码界面
                startActivity(new Intent(getContext(), ScanQrCodeActivity.class));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(this,"on create view..");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(this,"on destroy view...");
    }

    @Override
    protected void relese() {
        //取消注册
        if (mHomePresenter != null) {
            mHomePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void initPresenter() {
        //创建Presenter
        mHomePresenter = PresenterManager.getInstance().getHomePresenter();
        mHomePresenter.registerViewCallback(this);
        mSearchInputBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity instanceof IMainActivity) {
                    ((IMainActivity) activity).switch2Search();
                }
            }
        });
    }


    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
    }

    @Override
    protected void loadData() {
        //加载数据
        LogUtils.d(this,"loadData---->");
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
        LogUtils.d(this,"onCategoriesLoaded..");
        setupState(State.SUCCESS);
        //加载的数据就会到这
        if (mHomePagerAdapter != null) {
            //加载全部页面
//            homePager.setOffscreenPageLimit(categories.getData().size());

            mHomePagerAdapter.setCategories(categories);
        }
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    protected void onRetryClick() {
        //网络错误，点击重试
        //重新加载分类
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }
}
