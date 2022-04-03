package com.program.taobaounion.utils;

import com.program.taobaounion.presenter.ICateGoryPagerPresenter;
import com.program.taobaounion.presenter.IHomePresenter;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.presenter.impl.CategroyPagerPresenterImpl;
import com.program.taobaounion.presenter.impl.HomePresenterImpl;
import com.program.taobaounion.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final ICateGoryPagerPresenter mCategroyPagerPresenter;
    private final IHomePresenter mHomePresenter;
    private final ITicketPresenter mTickPresenter;

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

    private PresenterManager(){
        mCategroyPagerPresenter = new CategroyPagerPresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTickPresenter = new TicketPresenterImpl();
    }


}
