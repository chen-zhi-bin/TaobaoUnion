package com.program.taobaounion.model;

import com.program.taobaounion.model.domain.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();
}
