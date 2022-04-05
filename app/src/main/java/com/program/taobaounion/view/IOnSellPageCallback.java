package com.program.taobaounion.view;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.model.domain.OnSellContent;

public interface IOnSellPageCallback extends IBaseCallback {


    /**
     * 特惠内容
     * @param result
     */
    void onContentLoadedSuccess(OnSellContent result);


    /**
     * 加载更多结果
     * @param moreResult
     */
    void onMoreLoaded(OnSellContent moreResult);

    /**
     * 加载更多失败
     */
    void onMoreLoadedError();

    /**
     * 没有更多内容
     */
    void onMoreLoadeEmpty();


}
