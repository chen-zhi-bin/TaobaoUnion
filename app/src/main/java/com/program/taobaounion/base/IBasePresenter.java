package com.program.taobaounion.base;

import com.program.taobaounion.view.IHomeCallback;

public interface IBasePresenter<T> {
    /**
     * 注册ui通知接口
     * @param callback
     */
    void registerViewCallback(T callback);

    /**
     * 取消注册
     * @param callback
     */
    void unregisterViewCallback(T callback);
}
