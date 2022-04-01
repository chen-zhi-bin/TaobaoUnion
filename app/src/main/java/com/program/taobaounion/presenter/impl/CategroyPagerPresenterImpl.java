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

    //id和页码
    private Map<Integer,Integer> pagesInfo = new HashMap<>();
    public static final int DEFAULT_PAGE=1;
    private Integer mCurrentPage;

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

        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage==null) {
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId,DEFAULT_PAGE);
        }
        Call<HomePagerContent> task = createTask(categoryId, targetPage);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(this,"code-->"+code);
                if (code== HttpURLConnection.HTTP_OK) {
                    LogUtils.d(this,"--========================================");
                    HomePagerContent pagerContent = response.body();
                    LogUtils.d(CategroyPagerPresenterImpl.this,"pagerContent-->"+pagerContent);
                    //把数据给到ui
                    handlerHomePageContentResult(pagerContent,categoryId);
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

    private Call<HomePagerContent> createTask(int categoryId, Integer targetPage) {
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(this,"Url------->"+homePagerUrl);
        LogUtils.d(this,"homePagerUrl-->"+homePagerUrl);
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        Api api=retrofit.create(Api.class);
        return api.getHomePagerContent(homePagerUrl);
    }

    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onError();
            }
        }
    }

    private void handlerHomePageContentResult(HomePagerContent pagerContent, int categoryId) {
        //通知ui层更新数据
        List<HomePagerContent.DataBean> data = pagerContent.getData();
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                if (pagerContent==null||pagerContent.getData().size()==0) {
                    callback.onEmpty();
                }else {
                    List<HomePagerContent.DataBean> looperData = data.subList(data.size() - 5, data.size());
                    LogUtils.d(this,"looperData size-->"+looperData.size());
                    callback.onLooperListLoaded(looperData);
                    callback.onContentLoaded(data);
                }
            }

        }
    }

    @Override
    public void loadMore(int categoryId) {
        //加载更多数据
        //1.拿到当前页码
        mCurrentPage = pagesInfo.get(categoryId);
        if (mCurrentPage == null) {
            mCurrentPage =1;
        }
        //2.页码++
        mCurrentPage++;
        //加载数据
        Call<HomePagerContent> task = createTask(categoryId, mCurrentPage);
        //4.处理数据结果
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                //结果
                int code = response.code();
                LogUtils.d(CategroyPagerPresenterImpl.this,"respons code ----->"+code);
                if (code==HttpURLConnection.HTTP_OK){
                    HomePagerContent result = response.body();
//                    LogUtils.d(CategroyPagerPresenterImpl.this,"result -->"+result);
                    handleLoaderResult(result,categoryId);
                }else {
                    handleLoaderMoreError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                //请求失败
                LogUtils.d(CategroyPagerPresenterImpl.this,t.toString());
                handleLoaderMoreError(categoryId);
            }
        });
    }

    private void handleLoaderResult(HomePagerContent result, int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                if (result==null||result.getData().size()==0) {
                    callback.onLoaderMoreEmpty();
                }else {
                    callback.onLoaderMoreLoaded(result.getData());
                }
            }
        }
    }

    private void handleLoaderMoreError(int categoryId) {
        mCurrentPage--;
        pagesInfo.put(categoryId,mCurrentPage);
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onLoaderMoreError();
            }
        }
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
