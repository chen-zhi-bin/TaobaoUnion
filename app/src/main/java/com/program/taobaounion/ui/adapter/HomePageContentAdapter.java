package com.program.taobaounion.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.taobaounion.R;
import com.program.taobaounion.model.domain.HomePagerContent;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {

    private static final String TAG="HomePageContentAdapter";

    List<HomePagerContent.DataBean> mData =new ArrayList<>();

    private int testCount = 1;
    private OnListItemClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        LogUtils.d(this,"onCreateViewHolder....."+testCount);
        testCount++;
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = mData.get(position);
        //设置数据
        holder.setData(dataBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    HomePagerContent.DataBean item = mData.get(position);
                    mItemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<HomePagerContent.DataBean> content) {
        mData.clear();
        LogUtils.d(this,"url-->asdasdadadasa-->"+content.get(1).getPictUrl());
        mData.addAll(content);
        notifyDataSetChanged();
    }

    public void addData(List<HomePagerContent.DataBean> contents) {
        //添加之前拿到原来的size
        int olderSize = mData.size();
        mData.addAll(contents);
        //跟新ui
        notifyItemChanged(olderSize,contents.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        public ImageView cover;

        @BindView(R.id.goods_title)
        public TextView title;

        @BindView(R.id.goods_off_prise)
        public TextView offPriseTv;

        @BindView(R.id.goods_after_off_prise)
        public TextView finalPriseTv;
        @BindView(R.id.goods_original_prise)
        public TextView originaPriseTv;
        @BindView(R.id.goods_sell_count)
        public TextView sellCountTv;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomePagerContent.DataBean dataBean) {
            Context context = itemView.getContext();
            title.setText(dataBean.getTitle());
          //  LogUtils.d(this,"url-->"+dataBean.getPictUrl());//发现没有协议开头
            ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
            int width = layoutParams.width;
            int height = layoutParams.height;
            LogUtils.d(TAG,"width-->"+width+",height-->"+height);
            int coverSize=(width>height?width:height)/2;

            Glide.with(context).load(UrlUtils.getCoverPath(dataBean.getPictUrl(),coverSize)).into(cover);
            Integer couponAmount = dataBean.getCouponAmount();
            String finalPrise = dataBean.getZkFinalPrice();
            LogUtils.d(this,"finalPrise-->"+finalPrise);
            float resultPrise = Float.parseFloat(finalPrise) - couponAmount;
            LogUtils.d(this,"resultPrise-->"+resultPrise);
            finalPriseTv.setText(String.format("%.2f",resultPrise));
            offPriseTv.setText(String.format(context.getString(R.string.text_goods_off_prise),couponAmount));
            originaPriseTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            originaPriseTv.setText(String.format(context.getString(R.string.text_goods_original_prise),finalPrise));
            sellCountTv.setText(String.format(context.getString(R.string.text_goods_sell_count),dataBean.getVolume()));
        }
    }

    public void setOnListItemClickListener(OnListItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface OnListItemClickListener{
        void onItemClick(HomePagerContent.DataBean item);
    }
}
