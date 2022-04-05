package com.program.taobaounion.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseFragment;
import com.program.taobaounion.model.domain.OnSellContent;
import com.program.taobaounion.presenter.IOnsellPagePresenter;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.ui.activity.TicketActivity;
import com.program.taobaounion.ui.adapter.OnSellContentAdapter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.PresenterManager;
import com.program.taobaounion.utils.SizeUtils;
import com.program.taobaounion.utils.ToastUtils;
import com.program.taobaounion.view.IOnSellPageCallback;

import butterknife.BindView;

public class OnSellFragment extends BaseFragment implements IOnSellPageCallback, OnSellContentAdapter.OnSellPageItemClickListener {

    private IOnsellPagePresenter mOnSellPagePresenter;
    public static final int DEFAULT_SPAN_COUNT = 2;

    @BindView(R.id.on_sell_container_list)
    public RecyclerView mContentRv;

    @BindView(R.id.on_sell_refresh_layout)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;


    private OnSellContentAdapter mOnSellContentAdapter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mOnSellPagePresenter = PresenterManager.getInstance().getOnSellPagePresenter();
        mOnSellPagePresenter.registerViewCallback(this);
        mOnSellPagePresenter.getOnSellContent();
    }

    @Override
    protected void relese() {
        super.relese();
        if (mOnSellPagePresenter != null) {
            mOnSellPagePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_on_sell;
    }



    @Override
    protected void initListener() {
        super.initListener();
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //去加载跟多的内容
                if (mOnSellPagePresenter != null) {
                    mOnSellPagePresenter.loaderMore();
                }
            }
        });
        mOnSellContentAdapter.setOnSellPageItemClickListener(this);
    }

    protected void initView(View rootView) {
        mOnSellContentAdapter = new OnSellContentAdapter();
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);    //两列
        mContentRv.setLayoutManager(gridLayoutManager);
        mContentRv.setAdapter(mOnSellContentAdapter);
        mContentRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.left = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.right = SizeUtils.dip2px(getContext(), 2.5f);

            }
        });

        mTwinklingRefreshLayout.setEnableLoadmore(true);
        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableOverScroll(true);
    }

    @Override
    public void onContentLoadedSuccess(OnSellContent result) {
        //数据回来
        setupState(State.SUCCESS);
        //跟新ui
        mOnSellContentAdapter.setData(result);

    }

    @Override
    public void onMoreLoaded(OnSellContent moreResult) {
        //结束刷新
        mTwinklingRefreshLayout.finishLoadmore();
        //添加内容到适配器里
        mOnSellContentAdapter.onMoreLoaded(moreResult);
        ToastUtils.showToast("加载了"+moreResult.getData().getTbkDgOptimusMaterialResponse().getResultList().getMapData().size()+"条数据");
    }

    @Override
    public void onMoreLoadedError() {
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast("网络异常，请稍后重试..");
    }

    @Override
    public void onMoreLoadeEmpty() {
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast("没有更多内容.....");

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
        setupState(State.EMPTY);
    }

    @Override
    public void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
        //特惠列表内容被点击
        //处理数据
        String title = data.getTitle();
        //详情的地址
        String url = data.getCouponClickUrl();
        if (TextUtils.isEmpty(url)){
            url=data.getClickUrl();
        }
        String cover = data.getPictUrl();
        //拿到ticketPresenter去加载数据
        ITicketPresenter tickPresenter = PresenterManager.getInstance().getTickPresenter();
        tickPresenter.getTicket(title,url,cover);
        startActivity(new Intent(getContext(), TicketActivity.class));
    }
}
