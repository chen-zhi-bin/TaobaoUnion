package com.program.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.model.domain.Categories;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.presenter.ICateGoryPagerPresenter;
import com.program.taobaounion.presenter.impl.CategroyPagerPresenterImpl;
import com.program.taobaounion.ui.adapter.HomePageContentAdapter;
import com.program.taobaounion.utils.Constants;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.view.ICategoryPagerCallback;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICateGoryPagerPresenter mCategroyPagerPresenter;
    private int mMaterialId;

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;
    private HomePageContentAdapter mContentAdapter;

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
        //TODO:加载数据
        LogUtils.d(this,"loadData title-->"+title);
        LogUtils.d(this,"loadData materialId-->"+ mMaterialId);
        if (mCategroyPagerPresenter != null) {
            mCategroyPagerPresenter.getContentByCategoryId(mMaterialId);
        }
    }

//    @Override
//    public void getContentByCategoryId(int categoryId) {
//        //根据分配id去加载内容
//        //TODO:
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

    }
}
