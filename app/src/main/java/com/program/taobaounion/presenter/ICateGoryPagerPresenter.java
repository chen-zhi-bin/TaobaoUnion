package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.view.ICategoryPagerCallback;

public interface ICateGoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {
    /**
     * 根据分类id去获取对应的分类内容内容
     * @param categoryId
     */
    void getContentByCategoryId(int categoryId);

    void loadMore(int categoryId);

    void reload(int categoryId);

}
