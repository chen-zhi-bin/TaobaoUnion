package com.program.taobaounion.view;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.presenter.ICateGoryPagerPresenter;

import java.util.List;

public interface ICategoryPagerCallback extends IBaseCallback {

    /**
     * 数据加载回来
     * @param content
     */
    void onContentLoaded(List<HomePagerContent.DataBean> content);

    int getCategoryId();



    /**
     * 加载更多时错误
     */
    void onLoaderMoreError();

    /**
     * 没有更多内容
     */
    void onLoaderMoreEmpty();

    /**
     * 加载了更多内容
     * @param contents
     */
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 轮播图内容加载
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);

}
