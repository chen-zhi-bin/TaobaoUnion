package com.program.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.model.domain.Categories;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.presenter.ICateGoryPagerPresenter;
import com.program.taobaounion.presenter.impl.CategroyPagerPresenterImpl;
import com.program.taobaounion.ui.adapter.HomePageContentAdapter;
import com.program.taobaounion.ui.adapter.LooperPagerAdapter;
import com.program.taobaounion.utils.Constants;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.SizeUtils;
import com.program.taobaounion.view.ICategoryPagerCallback;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICateGoryPagerPresenter mCategroyPagerPresenter;
    private int mMaterialId;

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;
    private HomePageContentAdapter mContentAdapter;

    @BindView(R.id.looper_pager)
    public ViewPager looperPager;
    private LooperPagerAdapter mLooperPagerAdapter;

    @BindView(R.id.home_pager_title)
    public TextView currentCategoryTitleTv;

    @BindView(R.id.looper_point_container)
    public LinearLayout looperPointContainer;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment=new HomePagerFragment();
        //
        Bundle bundle=new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initListener() {
        looperPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动

            }

            @Override
            public void onPageSelected(int position) {
                //切换指示器
                //因为postion会越滑越大,所以要求余
                int targetPosition = position%mLooperPagerAdapter.getDataSize();
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //状态改变
            }
        });
    }

    /**
     * 切换指示器
     * @param targetPosition
     */
    private void updateLooperIndicator(int targetPosition) {
        for (int i = 0; i < looperPointContainer.getChildCount(); i++) {
            View point = looperPointContainer.getChildAt(i);
            if (i==targetPosition){
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_noraml);
            }
        }

    }

    @Override
    protected void initView(View rootView) {
        //设置布局管理器
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom=8;
            }
        });
        //设置适配器
        mContentAdapter = new HomePageContentAdapter();
        //设置适配器
        mContentList.setAdapter(mContentAdapter);
//       setupState(State.SUCCESS);

        //轮播图
        //创建适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        //设置适配器
        looperPager.setAdapter(mLooperPagerAdapter);
    }

    @Override
    protected void initPresenter() {
        mCategroyPagerPresenter = CategroyPagerPresenterImpl.getInstance();
        mCategroyPagerPresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();//拿到参数
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);

        LogUtils.d(this,"loadData title-->"+title);
        LogUtils.d(this,"loadData materialId-->"+ mMaterialId);
        if (mCategroyPagerPresenter != null) {
            mCategroyPagerPresenter.getContentByCategoryId(mMaterialId);
        }
        if (currentCategoryTitleTv != null) {
            currentCategoryTitleTv.setText(title);
        }
    }

//    @Override
//    public void getContentByCategoryId(int categoryId) {
//        //根据分配id去加载内容
//    }

    @Override
    protected void relese() {
        if (mCategroyPagerPresenter != null) {
            mCategroyPagerPresenter.unregisterViewCallback(this);
        }
    }


    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> content) {
        //数据列表加载
        mContentAdapter.setData(content);

        setupState(State.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onError() {
        //网络错误
        setupState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public void onLoaderMoreError() {

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        LogUtils.d(this,"looperdata size-->"+contents.size());
        mLooperPagerAdapter.setData(contents);
        //设置到中间点
        //中间点%的数据不一定为0，所以显示的就不是第一个
        int dx = (Integer.MAX_VALUE/2)%contents.size();
        int targetCenterPostion = (Integer.MAX_VALUE/2) - dx;
        looperPager.setCurrentItem(targetCenterPostion);
        LogUtils.d(this,"url-->"+contents.get(0).getPictUrl());

        //添加点
        for (int i = 0; i < contents.size(); i++) {
            View point=new View(getContext());
            //view会加到LinearLayout钟
            int size = SizeUtils.dip2px(getContext(), 8);
            //设置大小
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(size,size);
            point.setLayoutParams(layoutParams);
            point.setBackgroundColor(getContext().getColor(R.color.white));
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(),5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(),5);
            if (i==0){
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_noraml);
            }
            looperPointContainer.addView(point);
        }
    }
}
