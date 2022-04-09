package com.program.taobaounion.presenter.impl;

import com.program.taobaounion.model.Api;
import com.program.taobaounion.model.domain.Histories;
import com.program.taobaounion.model.domain.SearchRecommend;
import com.program.taobaounion.model.domain.SearchResult;
import com.program.taobaounion.presenter.ISearchPresenter;
import com.program.taobaounion.utils.JsonCacheUtil;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.RetrofitManager;
import com.program.taobaounion.view.ISearchPageCallback;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchPresenterImpl implements ISearchPresenter {

    private final Api mApi;
    private ISearchPageCallback mSearchViewCallback = null;
    private String mCurrentKeyword = null;
    private final JsonCacheUtil mJsonCacheUtil;

    public SearchPresenterImpl() {
        RetrofitManager instance = RetrofitManager.getInstance();
        Retrofit retrofit = instance.getRetrofit();
        mApi = retrofit.create(Api.class);
        mJsonCacheUtil = JsonCacheUtil.getInstance();
    }

    private static final int DEFAULT_PAGE = 0;
    /**
     * 当前页
     */
    private int mCurrentPage = DEFAULT_PAGE;

    @Override
    public void getHistories() {
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORIES, Histories.class);
        if (mSearchViewCallback!=null) {
            mSearchViewCallback.onHistoriesLoaded(histories);
        }
    }

    @Override
    public void delHistories() {
        mJsonCacheUtil.delCache(KEY_HISTORIES);
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onHistoriesDeleted();
        }
    }

    public static final String KEY_HISTORIES="key_histories";

    public static final int DEFAULT_HISTORIES_SIZE = 10;
    private int mHistoriesMaxSize = DEFAULT_HISTORIES_SIZE;
    /**
     * 添加历史记录
     * @param history
     */
    private void saveHistory(String history){
//        this.delHistories();
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORIES,Histories.class);
        //如果说已经在了，就干掉，然后再添加
        List<String> historiesList = null;
        if(histories != null && histories.getHistories() != null) {
            historiesList = histories.getHistories();
            if(historiesList.contains(history)) {
                historiesList.remove(history);
            }
        }
        //去重完成
        //处理没有数据的情况
        if(historiesList == null) {
            historiesList = new ArrayList<>();
        }
        if(histories == null) {
            histories = new Histories();
        }
        histories.setHistories(historiesList);
        //对个数进行限制
        if(historiesList.size() > mHistoriesMaxSize) {
            historiesList = historiesList.subList(0,mHistoriesMaxSize);
        }
        //添加记录
        historiesList.add(history);
        //保存记录
        mJsonCacheUtil.saveCache(KEY_HISTORIES,histories);
    }

    @Override
    public void doSearch(String keyword) {
        if (mCurrentKeyword == null || !mCurrentKeyword.equals(keyword)) {
            this.saveHistory(keyword);
            this.mCurrentKeyword = keyword;
        }
        //更新ui状态
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onLoading();
        }
        Call<SearchResult> task = mApi.doSearch(mCurrentPage, keyword);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, "do search code -->" + code);
                LogUtils.d(SearchPresenterImpl.this, "do search data -->" + response.body());
                if (code == HttpsURLConnection.HTTP_OK) {
                    handleSearchResult(response.body());
                } else {
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
                onError();
            }
        });
    }

    private void onError() {
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onError();
        }
    }

    private void handleSearchResult(SearchResult body) {
        if (mSearchViewCallback != null) {
            if (isResultEmpty(body)) {
                //数据为空
                mSearchViewCallback.onEmpty();
            } else {
                mSearchViewCallback.onSearchSuccess(body);
            }
        }

    }

    private boolean isResultEmpty(SearchResult result) {
        try {
            return result == null || result.getData().getTbkDgMaterialOptionalResponse().getResultList().getMapData().size() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void research() {
        if (mCurrentKeyword == null) {
            if (mSearchViewCallback != null) {
                mSearchViewCallback.onEmpty();
            }
        } else {
            //可以重新搜索
            this.doSearch(mCurrentKeyword);
        }
    }

    @Override
    public void loaderMore() {
        mCurrentPage++;
        //进行搜索
        if (mCurrentKeyword ==null) {
            //数据为空
            if (mSearchViewCallback != null) {
                mSearchViewCallback.onEmpty();
            }
        }else {
            doSearchMore();
        }
    }

    private void doSearchMore() {
        Call<SearchResult> task = mApi.doSearch(mCurrentPage, mCurrentKeyword);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, "do search more code -->" + code);
                if (code == HttpsURLConnection.HTTP_OK) {
                    handleMoreSearchResult(response.body());
                } else {
                    onLoadeMoreError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
                onLoadeMoreError();
            }
        });
    }

    /**
     * 处理加载更多的结果
     * @param result
     */
    private void handleMoreSearchResult(SearchResult result) {
        if (mSearchViewCallback != null) {
            if (isResultEmpty(result)) {
                //数据为空
                mSearchViewCallback.onMoreLoadedEmpty();
            } else {
                mSearchViewCallback.onMoreLoaded(result);
            }
        }
    }

    /**
     * 加载更多失败
     */
    private void onLoadeMoreError() {
        mCurrentPage--;
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onMoreLoadedError();
        }
    }

    @Override
    public void getRecommendWords() {
        Call<SearchRecommend> task = mApi.getRecommendWords();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, "getRecommend code-->" + code);
                LogUtils.d(SearchPresenterImpl.this, "getRecommend Data-->" + response.body().getData());
                if (code == HttpsURLConnection.HTTP_OK) {
                    //处理结果
                    if (mSearchViewCallback != null) {
                        mSearchViewCallback.onRecommendWordsLoaded(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void registerViewCallback(ISearchPageCallback callback) {
        this.mSearchViewCallback = callback;
    }

    @Override
    public void unregisterViewCallback(ISearchPageCallback callback) {
        this.mSearchViewCallback = null;
    }
}
