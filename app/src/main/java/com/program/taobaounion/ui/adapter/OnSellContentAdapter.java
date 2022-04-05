package com.program.taobaounion.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.taobaounion.R;
import com.program.taobaounion.model.domain.OnSellContent;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnSellContentAdapter extends RecyclerView.Adapter<OnSellContentAdapter.InnerHolder> {

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mData = new ArrayList<>();
    private OnSellPageItemClickListener mContentItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_sell_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
    //绑定数据
        OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean = mData.get(position);
        holder.setData(mapDataBean);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContentItemClickListener != null) {
                    mContentItemClickListener.onSellItemClick(mapDataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(OnSellContent result) {
        this.mData.clear();
        this.mData.addAll(result.getData().getTbkDgOptimusMaterialResponse().getResultList().getMapData());
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     * @param moreResult
     */
    public void onMoreLoaded(OnSellContent moreResult) {
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> moreData = moreResult.getData().getTbkDgOptimusMaterialResponse().getResultList().getMapData();
        //原数据长度
        int olderDataSiez=this.mData.size();
        this.mData.addAll(moreData);
        notifyItemRangeChanged(olderDataSiez-1,moreData.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.on_sell_cover)
        public ImageView cover;

        @BindView(R.id.on_sell_content_title_tv)
        public TextView titleTv;

        @BindView(R.id.on_sell_origin_prise_tv)
        public TextView originPriseTv;

        @BindView(R.id.on_sell_off_prise_tv)
        public TextView offPriseTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public  void setData(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data){
            titleTv.setText(data.getTitle());
//            LogUtils.d(this,"pic url-->"+data.getPictUrl());
            String coverPath = UrlUtils.getCoverPath(data.getPictUrl());
            Glide.with(cover.getContext()).load(coverPath).into(cover);
            String originPrice = data.getZkFinalPrice();
            originPriseTv.setText("￥"+originPrice+" ");
            originPriseTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            int couponAmount = data.getCouponAmount();
            float originPriseFloat = Float.parseFloat(originPrice);
            float finalPrise = originPriseFloat - couponAmount;
            offPriseTv.setText("券后价:"+String.format("%.2f",finalPrise));
        }
    }

    public void setOnSellPageItemClickListener(OnSellPageItemClickListener listener){
        this.mContentItemClickListener = listener;
    }

    public interface OnSellPageItemClickListener{

        void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data);
    }
}
