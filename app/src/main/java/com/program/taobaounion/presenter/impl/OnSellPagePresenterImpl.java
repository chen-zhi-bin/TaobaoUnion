package com.program.taobaounion.presenter.impl;

import com.program.taobaounion.model.Api;
import com.program.taobaounion.model.domain.OnSellContent;
import com.program.taobaounion.model.domain.TicketResult;
import com.program.taobaounion.presenter.IOnsellPagePresenter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.RetrofitManager;
import com.program.taobaounion.utils.UrlUtils;
import com.program.taobaounion.view.IOnSellPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnSellPagePresenterImpl implements IOnsellPagePresenter {
    public static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    private IOnSellPageCallback mOnSellPageCallback=null;
    private final Api mApi;

    public OnSellPagePresenterImpl(){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }
    @Override
    public void getOnSellContent() {
        if (mIsLoading) {
            return;
        }
        mIsLoading=true;
        //通知ui状态为加载中..
        if (mOnSellPageCallback != null) {
            mOnSellPageCallback.onLoading();
        }
        //获取特惠内容


        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        LogUtils.d(OnSellPagePresenterImpl.this,"url-->"+targetUrl);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        LogUtils.d(OnSellPagePresenterImpl.this,"task -->"+task);
        task.enqueue(new Callback<OnSellContent>() {

            @Override
            public void onResponse(Call<OnSellContent> call,Response<OnSellContent> response) {
                mIsLoading = false;
                int code = response.code();
                LogUtils.d(OnSellPagePresenterImpl.this,"result code == > " + code);
                if(code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    OnSellContent result = response.body();
                    onSuccess(result);
                } else {
                    //请求失败
                    onError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call,Throwable t) {
                //失败
                LogUtils.d(OnSellPagePresenterImpl.this,"ttt-->"+t.toString());
                LogUtils.d(OnSellPagePresenterImpl.this,"ttt-->"+t.getMessage());
                onError();
            }
        });

    }

    private void onSuccess(OnSellContent result) {
        if (mOnSellPageCallback != null) {
            try {
                if (isEmpty(result)){
                    onEmpty();
                }else {
                    mOnSellPageCallback.onContentLoadedSuccess(result);
                }
            }catch (Exception e){
                e.printStackTrace();
                onEmpty();
            }
        }
    }

    private boolean isEmpty(OnSellContent content){
        int size = content.getData().getTbkDgOptimusMaterialResponse().getResultList().getMapData().size();
        return size == 0;
    }

    private void onEmpty(){
        if (mOnSellPageCallback != null) {
            mOnSellPageCallback.onEmpty();
        }
    }

    private void onError() {
        if (mOnSellPageCallback != null) {
            mOnSellPageCallback.onError();
        }
    }

    @Override
    public void reLoad() {
        //重新加载
        this.getOnSellContent();
    }

    /**
     * 当前状态
     */
    private boolean mIsLoading = false;

    @Override
    public void loaderMore() {
        if (mIsLoading){
            return;
        }
        mIsLoading = true;
        //加载更多
        mCurrentPage++;
        //去加载更多内容
        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call,Response<OnSellContent> response) {
                mIsLoading = false;
                int code = response.code();
                LogUtils.d(OnSellPagePresenterImpl.this,"result code-->"+code);
                if(code == HttpURLConnection.HTTP_OK) {
                    OnSellContent result = response.body();
                    onMoreLoaded(result);
                } else {
                    onMoreLoadError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call,Throwable t) {
                LogUtils.d(OnSellPagePresenterImpl.this,"result -->"+t.getMessage());
                onMoreLoadError();
            }
        });
    }


    private void onMoreLoadError() {
        mIsLoading=false;
        mCurrentPage--;
        mOnSellPageCallback.onMoreLoadedError();
    }

    /**
     * 加载更多结果，通知ui
     * @param result
     */
    private void onMoreLoaded(OnSellContent result) {
        if (mOnSellPageCallback != null) {
            if (isEmpty(result)) {
                mCurrentPage--;
                mOnSellPageCallback.onMoreLoadeEmpty();
            }else {
                mOnSellPageCallback.onMoreLoaded(result);
            }
        }
    }

    @Override
    public void registerViewCallback(IOnSellPageCallback callback) {
        this.mOnSellPageCallback=callback;
    }

    @Override
    public void unregisterViewCallback(IOnSellPageCallback callback) {
        this.mOnSellPageCallback=null;
    }
}
