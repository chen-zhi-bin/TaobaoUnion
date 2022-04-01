package com.lcodecore.tkrefreshlayout.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;


public class TbNestedScrollView extends NestedScrollView {
    private int mHeaderHeight = 498;

    private int originScroll = 0;
    private RecyclerView mRecyclerView;

    public TbNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderHeight(int headerHeight){
        this.mHeaderHeight = headerHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        LogUtils.d(this,"dx ==>"+dx);
//        LogUtils.d(TbNestedScrollView.this,"dy ==>"+dy);
        if (target instanceof RecyclerView){
            this.mRecyclerView = (RecyclerView)target;
        }
        //小于就不动
        if (originScroll<mHeaderHeight){
            scrollBy(dx,dy);                 //滑动多少
//        scrollTo();                 //到某一点
            consumed[0] = dx;
            consumed[1] = dy;
        }

        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LogUtils.d(this,"vertical-->"+t);
        this.originScroll = t;
        super.onScrollChanged(l, t, oldl, oldt);

    }


    /**
     * 判断子类是否已经滑动到了底部
     * @return
     */
    public boolean isInBottom() {
        if (mRecyclerView != null) {
            //判断是否到底部
            boolean isBottom = !mRecyclerView.canScrollVertically(1);
            //isBottom没到底部就是false
            return isBottom;
        }
        return false;
    }
}
