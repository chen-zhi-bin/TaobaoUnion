package com.program.taobaounion.utils;

import com.program.taobaounion.presenter.ICateGoryPagerPresenter;
import com.program.taobaounion.presenter.IHomePresenter;
import com.program.taobaounion.presenter.IOnsellPagePresenter;
import com.program.taobaounion.presenter.ISeletedPagePresenter;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.presenter.impl.CategroyPagerPresenterImpl;
import com.program.taobaounion.presenter.impl.HomePresenterImpl;
import com.program.taobaounion.presenter.impl.OnSellPagePresenterImpl;
import com.program.taobaounion.presenter.impl.SelectedPagePresenterImpl;
import com.program.taobaounion.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final ICateGoryPagerPresenter mCategroyPagerPresenter;
    private final IHomePresenter mHomePresenter;
    private final ITicketPresenter mTickPresenter;
    private final ISeletedPagePresenter mSelectedPagePresenter;
    private final IOnsellPagePresenter mOnSellPagePresenter;

    public ICateGoryPagerPresenter getCategroyPagerPresenter() {
        return mCategroyPagerPresenter;
    }

    public IHomePresenter getHomePresenter() {
        return mHomePresenter;
    }

    public ITicketPresenter getTickPresenter() {
        return mTickPresenter;
    }

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public ISeletedPagePresenter getSelectedPagePresenter() {
        return mSelectedPagePresenter;
    }

    public IOnsellPagePresenter getOnSellPagePresenter() {
        return mOnSellPagePresenter;
    }

    private PresenterManager(){
        mCategroyPagerPresenter = new CategroyPagerPresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTickPresenter = new TicketPresenterImpl();
        mSelectedPagePresenter = new SelectedPagePresenterImpl();
        mOnSellPagePresenter = new OnSellPagePresenterImpl();
    }


}
