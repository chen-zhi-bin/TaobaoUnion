package com.program.taobaounion.presenter;

import com.program.taobaounion.view.IHomeCallback;

public interface IHomePresenter {
    /*
    获取商品分类
     */
    void getCategories();

    /**
     * 注册ui通知接口
     * @param callback
     */
    void registerCallback(IHomeCallback callback);

    /**
     * 取消注册
     * @param callback
     */
    void unregistereCallback(IHomeCallback callback);
}
