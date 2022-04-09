package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.view.ISearchPageCallback;

public interface ISearchPresenter extends IBasePresenter<ISearchPageCallback> {

    /**
     * 获取搜索历史
     */
    void getHistories();

    /**
     * 删除搜索历史
     */
    void delHistories();

    /**
     *搜索
     * @param keyword
     */
    void doSearch(String keyword);

    /**
     * 重试
     */
    void research();

    /**
     * 获取更多
     */
    void loaderMore();

    /**
     * 获取推荐词
     */
    void getRecommendWords();
}
