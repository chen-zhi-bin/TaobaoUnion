package com.program.taobaounion.presenter.impl;

import com.program.taobaounion.model.Api;
import com.program.taobaounion.model.domain.Categories;
import com.program.taobaounion.presenter.IHomePresenter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.RetrofitManger;
import com.program.taobaounion.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback mCallback = null;

    @Override
    public void getCategories() {
        if (mCallback != null) {
            mCallback.onLoading();
        }
        //加载分类数据
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //数据结果
                int code = response.code();
                LogUtils.d(HomePresenterImpl.this, "code-->" + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    Categories categories = response.body();
                    if (mCallback != null) {
                        if (categories == null || categories.getData().size() == 0) {
                            mCallback.onEmpty();
                        }else {
                            mCallback.onCategoriesLoaded(categories);
                        }
//                    LogUtils.d(HomePresenterImpl.this,"categories-->"+categories.toString());
                    }
                } else {
                    //请求失败
                    LogUtils.i(this, "请求失败..");
                    if (mCallback != null) {
                        mCallback.onNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                //加载失败结果
                LogUtils.e(this, "请求错误.." + t);
                if (mCallback != null) {
                    mCallback.onNetworkError();
                }
            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregistereCallback(IHomeCallback callback) {
        mCallback = null;
    }
}
