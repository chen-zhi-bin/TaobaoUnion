package com.program.taobaounion.presenter;

import com.program.taobaounion.base.IBasePresenter;
import com.program.taobaounion.view.ITicketPagerCallback;

import java.net.URL;

public interface ITicketPresenter extends IBasePresenter<ITicketPagerCallback> {

    /**
     * 获取优惠券，生成淘口令
     * @param title
     * @param url
     * @param cover
     */
    void getTicket(String title, String url,String cover);

}
