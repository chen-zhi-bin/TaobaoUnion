package com.program.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.program.taobaounion.R;
import com.program.taobaounion.model.domain.SelectedPageCategory;
import com.program.taobaounion.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectedPageLeftAdapter extends RecyclerView.Adapter<SelectedPageLeftAdapter.InnerHolder> {

    private List<SelectedPageCategory.DataBean> mData=new ArrayList<>();

    private int mCurrentSelectedPostion = 0;
    private OnLeftItemClickListener mItemclicklistener =null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_left, parent, false);

        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TextView itemTv = holder.itemView.findViewById(R.id.left_categroy_tv);
        if (mCurrentSelectedPostion==position){
            itemTv.setBackgroundColor(itemTv.getResources().getColor(R.color.colorEEEEEE,null));
        }else {
            itemTv.setBackgroundColor(itemTv.getResources().getColor(R.color.white,null));
        }
        SelectedPageCategory.DataBean dataBean = mData.get(position);
        LogUtils.d(SelectedPageLeftAdapter.this,"databean =="+dataBean);
        itemTv.setText(dataBean.getFavorites_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemclicklistener != null&&mCurrentSelectedPostion!=position) {
                    //修改当前选中的位置
                    mCurrentSelectedPostion = position;
                    mItemclicklistener.onLeftItemClick(dataBean);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 设置数据
     * @param categories
     */
    public void setData(SelectedPageCategory categories) {
        List<SelectedPageCategory.DataBean> data = categories.getData();
        if (data != null) {
            this.mData=data;
//            this.mData.addAll(data);
            notifyDataSetChanged();
        }
        if (mData.size()>0){
            mItemclicklistener.onLeftItemClick(mData.get(mCurrentSelectedPostion));
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnLeftItemClickListener(OnLeftItemClickListener listener){

        this.mItemclicklistener = listener;
    }

    public interface OnLeftItemClickListener{
        void  onLeftItemClick(SelectedPageCategory.DataBean item);
    }

}
