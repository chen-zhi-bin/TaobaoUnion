package com.program.taobaounion.view;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.model.domain.SelectedContent;
import com.program.taobaounion.model.domain.SelectedPageCategory;

public interface ISelectedPageCallback extends IBaseCallback {

    /**
     * 分类内容
     * @param categories
     */
    void onCategoriesLoaded(SelectedPageCategory categories);

    /**
     * 内容
     * @param content
     */
    void onContentLoaded(SelectedContent content);
}
