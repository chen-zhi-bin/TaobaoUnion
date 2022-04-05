package com.program.taobaounion.model;

import com.program.taobaounion.model.domain.Categories;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.model.domain.OnSellContent;
import com.program.taobaounion.model.domain.SelectedContent;
import com.program.taobaounion.model.domain.SelectedPageCategory;
import com.program.taobaounion.model.domain.TicketParams;
import com.program.taobaounion.model.domain.TicketResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);

    @POST("tpwd")
    Call<TicketResult> getTicket(@Body TicketParams ticketParams);

    @GET("recommend/categories")
    Call<SelectedPageCategory> getSelectedPageCategories();

    @GET
    Call<SelectedContent> getSelectedPageContent(@Url String url);

    @GET
    Call<OnSellContent> getOnSellPageContent(@Url String url);
}
