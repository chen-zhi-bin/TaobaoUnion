package com.program.taobaounion.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
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
import com.program.taobaounion.model.domain.IBaseInfo;
import com.program.taobaounion.model.domain.ILinearItemInfo;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearItemContentAdapter extends RecyclerView.Adapter<LinearItemContentAdapter.InnerHolder> {

    private static final String TAG="HomePageContentAdapter";

    List<ILinearItemInfo> mData =new ArrayList<>();

    private int testCount = 1;
    private OnListItemClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_goods_content, parent, false);
        LogUtils.d(this,"onCreateViewHolder....."+testCount);
        testCount++;
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ILinearItemInfo dataBean = mData.get(position);
        //设置数据
        holder.setData(dataBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<? extends ILinearItemInfo> content) {
        mData.clear();
        mData.addAll(content);
        notifyDataSetChanged();
    }

    public void addData(List<? extends ILinearItemInfo> contents) {
        //添加之前拿到原来的size
        int olderSize = mData.size();
        mData.addAll(contents);
        //跟新ui
        notifyItemChanged(olderSize,contents.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        public ImageView coverIv;

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

        public void setData(ILinearItemInfo dataBean) {
            Context context = itemView.getContext();
            title.setText(dataBean.getTitle());
          //  LogUtils.d(this,"url-->"+dataBean.getPictUrl());//发现没有协议开头
//            ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
//            int width = layoutParams.width;
//            int height = layoutParams.height;
//            LogUtils.d(TAG,"width-->"+width+",height-->"+height);
//            int coverSize=(width>height?width:height)/2;
            String cover = dataBean.getCover();
            if (!TextUtils.isEmpty(cover)) {
                String coverPath = UrlUtils.getCoverPath(dataBean.getCover());
                Glide.with(context).load(UrlUtils.getCoverPath(coverPath)).into(this.coverIv);
            }else {
                coverIv.setImageResource(R.mipmap.ic_launcher);
            }
            long couponAmount = dataBean.getCouponAmount();
            String finalPrise = dataBean.getFinalPrise();
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
        void onItemClick(IBaseInfo item);
    }
}
