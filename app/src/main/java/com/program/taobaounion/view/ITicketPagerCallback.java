package com.program.taobaounion.view;

import com.program.taobaounion.base.IBaseCallback;
import com.program.taobaounion.model.domain.TicketResult;

public interface ITicketPagerCallback extends IBaseCallback {

    /**
     * 淘口令加载结果
     * @param cover
     * @param result
     */
    void onTicketLoaded(String cover, TicketResult result);
}
