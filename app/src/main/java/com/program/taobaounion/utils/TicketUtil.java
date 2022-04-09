package com.program.taobaounion.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.program.taobaounion.base.BaseActivity;
import com.program.taobaounion.base.BaseApplication;
import com.program.taobaounion.model.domain.IBaseInfo;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.ui.activity.TicketActivity;

public class TicketUtil {

    public static void toTTicketPage(Context context,IBaseInfo baseInfo){
        //处理数据
        String title = baseInfo.getTitle();
        //详情的地址
        String url = baseInfo.getUrl();
        if (TextUtils.isEmpty(url)){
            url=baseInfo.getUrl();
        }
        String cover = baseInfo.getCover();
        //拿到ticketPresenter去加载数据
        ITicketPresenter tickPresenter = PresenterManager.getInstance().getTickPresenter();
        tickPresenter.getTicket(title,url,cover);
        context.startActivity(new Intent(context, TicketActivity.class));
    }

}
