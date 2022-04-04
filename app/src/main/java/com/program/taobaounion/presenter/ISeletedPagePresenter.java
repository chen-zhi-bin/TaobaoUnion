package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.model.domain.SelectedPageCategory;
import com.program.taobaounion.view.ISelectedPageCallback;

public interface ISeletedPagePresenter extends IBasePresenter<ISelectedPageCallback> {

    /**
     * 获取分类
     */
    void getCategorise();

    /**
     * 根据分类获取分类内容
     * @param item
     */
    void getContentbyCategory(SelectedPageCategory.DataBean item);

    /**
     * 重新加载内容
     */
    void reloadContent();
}
