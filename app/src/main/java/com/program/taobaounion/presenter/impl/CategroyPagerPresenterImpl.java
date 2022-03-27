package com.program.taobaounion.presenter.impl;

import com.program.taobaounion.model.Api;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.presenter.ICateGoryPagerPresenter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.RetrofitManger;
import com.program.taobaounion.utils.UrlUtils;
import com.program.taobaounion.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategroyPagerPresenterImpl implements ICateGoryPagerPresenter {

    private Map<Integer,Integer> pagesInfo = new HashMap<>();
    public static final int DEFAULT_PAGE=1;

    private CategroyPagerPresenterImpl(){

    }
    private static ICateGoryPagerPresenter sInstance =null;

    public static ICateGoryPagerPresenter getInstance(){
        if (sInstance ==null) {
            sInstance =new CategroyPagerPresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onLoading();
            }
        }
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        Api api=retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage==null) {
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId,DEFAULT_PAGE);
        }
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(this,"homePagerUrl-->"+homePagerUrl);
        Call<HomePagerContent> task = api.getHomePagerContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(this,"code-->"+code);
                if (code== HttpURLConnection.HTTP_OK) {
                    HomePagerContent pagerContent = response.body();
                    LogUtils.d(CategroyPagerPresenterImpl.this,"pagerContent-->"+pagerContent);
                    //把数据给到ui
                    handlerHomePageCContentResult(pagerContent,categoryId);
                }else {
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LogUtils.d(this,"t-->"+t.toString());
                handleNetworkError(categoryId);
            }
        });
    }

    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onError();
            }
        }
    }

    private void handlerHomePageCContentResult(HomePagerContent pagerContent, int categoryId) {
        //通知ui层更新数据
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                if (pagerContent==null||pagerContent.getData().size()==0) {
                    callback.onEmpty();
                }else {
                    callback.onContentLoaded(pagerContent.getData());
                }
            }

        }
    }

    @Override
    public void loadMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {
        //判断是否存在
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }

    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {
        callbacks.remove(callback);
    }




}
