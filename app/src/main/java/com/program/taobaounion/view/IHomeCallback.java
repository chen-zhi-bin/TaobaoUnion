package com.program.taobaounion.view;

import com.program.taobaounion.model.domain.Categories;

public interface IHomeCallback {
    void onCategoriesLoaded(Categories categories);

    void onNetworkError();

    void onLoading();

    void onEmpty();
}
