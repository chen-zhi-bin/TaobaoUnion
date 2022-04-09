package com.program.taobaounion.view;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.model.domain.Histories;
import com.program.taobaounion.model.domain.SearchRecommend;
import com.program.taobaounion.model.domain.SearchResult;

import java.util.List;

public interface ISearchPageCallback extends IBaseCallback {

    /**
     * 搜索历史
     * @param histories
     */
    void onHistoriesLoaded(Histories histories);

    /**
     * 历史记录删除
     */
    void onHistoriesDeleted();

    /**
     * 搜索结果:成功
     * @param result
     */
    void onSearchSuccess(SearchResult result);

    /**
     * 加载到了更多内容
     * @param result
     */
    void onMoreLoaded(SearchResult result);

    /**
     * 加载更多时网络错误
     */
    void onMoreLoadedError();

    /**
     * 没有更多内容
     */
    void onMoreLoadedEmpty();

    /**
     * 获取推荐词
     * @param recommendWords
     */
    void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommendWords);
}
