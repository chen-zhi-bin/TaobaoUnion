package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.view.IOnSellPageCallback;

public interface IOnsellPagePresenter extends IBasePresenter<IOnSellPageCallback> {

    /**
     * 加载特惠内容
     */
    void getOnSellContent();

    /**
     * 重新加载内容
     * @Call 网络出问题调用
     */
    void reLoad();

    /**
     * 加载更多
     */
    void loaderMore();

}
