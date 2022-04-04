package com.program.taobaounion.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.model.domain.SelectedContent;
import com.program.taobaounion.model.domain.SelectedPageCategory;
import com.program.taobaounion.presenter.ISeletedPagePresenter;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.ui.activity.TicketActivity;
import com.program.taobaounion.ui.adapter.SelectedPageContentAdapter;
import com.program.taobaounion.ui.adapter.SelectedPageLeftAdapter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.PresenterManager;
import com.program.taobaounion.utils.SizeUtils;
import com.program.taobaounion.view.ISelectedPageCallback;

import java.util.List;

import butterknife.BindView;

public class SelectedFragment extends BaseFragment implements ISelectedPageCallback, SelectedPageLeftAdapter.OnLeftItemClickListener, SelectedPageContentAdapter.OnSelectedPageContentItemClickListener {

    private ISeletedPagePresenter mSelectedPagePresenter;

    @BindView(R.id.left_category_list)
    public RecyclerView leftCategoryList;

    @BindView(R.id.right_container_list)
    public RecyclerView rightCategoryList;

    private SelectedPageLeftAdapter mLeftAdapter;
    private SelectedPageContentAdapter mRightAdapter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mSelectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        mSelectedPagePresenter.registerViewCallback(this);
        mSelectedPagePresenter.getCategorise();
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selecter;
    }

    protected void initView(View rootView) {
        setupState(State.SUCCESS);
        leftCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter = new SelectedPageLeftAdapter();
        leftCategoryList.setAdapter(mLeftAdapter);

        rightCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightAdapter = new SelectedPageContentAdapter();
        rightCategoryList.setAdapter(mRightAdapter);
        rightCategoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int topAndBottom = SizeUtils.dip2px(getContext(), 4);
                int leftAndRight = SizeUtils.dip2px(getContext(), 6);
                outRect.top = topAndBottom;
                outRect.left = leftAndRight;
                outRect.right = leftAndRight;
                outRect.bottom = topAndBottom;
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLeftAdapter.setOnLeftItemClickListener(this);
        mRightAdapter.setOnSelectedPageContentItemClickListener(this);
    }


    @Override
    public void retry() {
        //重试
        if (mSelectedPagePresenter != null) {
            mSelectedPagePresenter.reloadContent();
        }
    }

    @Override
    protected void relese() {
        super.relese();
        if (mSelectedPagePresenter != null) {
            mSelectedPagePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onCategoriesLoaded(SelectedPageCategory categories) {
        setupState(State.SUCCESS);
        mLeftAdapter.setData(categories);

        //分类内容
        LogUtils.d(this, "onCategoriesLoaded -->" + categories);
        //TODO:更新ui
        //根据当前选中的分类，获取分类详情内容
//        List<SelectedPageCategory.DataBean> data = categories.getData();
//
//        mSelectedPagePresenter.getContentbyCategory(data.get(0));
    }

    @Override
    public void onContentLoaded(SelectedContent content) {
        mRightAdapter.setData(content);
        //切换分类后 切换到当前分类的顶部
        rightCategoryList.scrollToPosition(0);
//        LogUtils.d(this,"onContentLoaded --->"+content.getData().
//                getTbkDgOptimusMaterialResponse()
//        .getResultList()
//        .getMapData()
//        .get(0).getTitle());

    }

    @Override
    public void onError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onLeftItemClick(SelectedPageCategory.DataBean item) {
        //左边的分类点击了
        mSelectedPagePresenter.getContentbyCategory(item);
        LogUtils.d(this, "current selected item ->" + item.getFavorites_title());

    }

    @Override
    public void onContentItemClick(SelectedContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean item) {
        //内容点击了
        //处理数据
        String title = item.getTitle();
        //详情的地址
        String url = item.getCouponClickUrl();
        if (TextUtils.isEmpty(url)) {
            url = item.getClickUrl();
        }
        String cover = item.getPictUrl();
        //拿到ticketPresenter去加载数据
        ITicketPresenter tickPresenter = PresenterManager.getInstance().getTickPresenter();
        tickPresenter.getTicket(title, url, cover);
        startActivity(new Intent(getContext(), TicketActivity.class));
    }
}
