package com.program.taobaounion.presenter.impl;

import com.program.taobaounion.model.Api;
import com.program.taobaounion.model.domain.SelectedContent;
import com.program.taobaounion.model.domain.SelectedPageCategory;
import com.program.taobaounion.presenter.ISeletedPagePresenter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.RetrofitManager;
import com.program.taobaounion.utils.UrlUtils;
import com.program.taobaounion.view.ISelectedPageCallback;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectedPagePresenterImpl implements ISeletedPagePresenter {

    private ISelectedPageCallback mViewCallback=null;
    private final Api mApi;


    public SelectedPagePresenterImpl(){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getCategorise() {
        if (mViewCallback != null) {
            mViewCallback.onLoading();
        }
        //拿到retrofit

        Call<SelectedPageCategory> task = mApi.getSelectedPageCategories();
        task.enqueue(new Callback<SelectedPageCategory>() {
            @Override
            public void onResponse(Call<SelectedPageCategory> call, Response<SelectedPageCategory> response) {
                int code = response.code();
                LogUtils.d(SelectedPagePresenterImpl.this,"result code -->"+code);
                LogUtils.d(SelectedPagePresenterImpl.this,"resulet body"+response.body());
                if (code== HttpsURLConnection.HTTP_OK){
                    SelectedPageCategory result = response.body();
                    //通知ui更新
                    if (mViewCallback != null) {
                        mViewCallback.onCategoriesLoaded(result);
                    }
                }else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<SelectedPageCategory> call, Throwable t) {
                onLoadError();
            }
        });
    }

    private void onLoadError() {
        if (mViewCallback != null) {
            mViewCallback.onError();
        }
    }

    @Override
    public void getContentbyCategory(SelectedPageCategory.DataBean item) {
        int categoryID = item.getFavorites_id();
        LogUtils.d(this,"categoryId -->"+categoryID);
        String targetUrl = UrlUtils.getSelectedPageContentUrl(categoryID);
        Call<SelectedContent> task = mApi.getSelectedPageContent(targetUrl);
        task.enqueue(new Callback<SelectedContent>() {
            @Override
            public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                int code = response.code();
                LogUtils.d(SelectedPagePresenterImpl.this,"result code -->"+code);
                LogUtils.d(SelectedPagePresenterImpl.this,"result body -->"+response.toString());
                if (code== HttpsURLConnection.HTTP_OK){
                    SelectedContent result = response.body();
                    if (mViewCallback != null) {
                        mViewCallback.onContentLoaded(result);
                    }
                }else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<SelectedContent> call, Throwable t) {
                onLoadError();
            }
        });
    }

    @Override
    public void reloadContent() {
        this.getCategorise();
    }

    @Override
    public void registerViewCallback(ISelectedPageCallback callback) {
        this.mViewCallback = callback;
    }

    @Override
    public void unregisterViewCallback(ISelectedPageCallback callback) {
        this.mViewCallback=null;
    }
}
