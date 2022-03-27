package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.view.IHomeCallback;

public interface IHomePresenter extends IBasePresenter<IHomeCallback> {
    /*
    获取商品分类
     */
    void getCategories();


}
