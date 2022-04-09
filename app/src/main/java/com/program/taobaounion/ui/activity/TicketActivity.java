package com.program.taobaounion.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.program.taobaounion.R;
import com.program.taobaounion.base.BaseActivity;
import com.program.taobaounion.model.domain.TicketResult;
import com.program.taobaounion.presenter.ITicketPresenter;
import com.program.taobaounion.utils.LogUtils;
import com.program.taobaounion.utils.PresenterManager;
import com.program.taobaounion.utils.ToastUtils;
import com.program.taobaounion.utils.UrlUtils;
import com.program.taobaounion.view.ITicketPagerCallback;

import butterknife.BindView;

public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private ITicketPresenter mTickPresenter;
    private boolean mHasTaobaoApp = false;

    @BindView(R.id.ticket_cover)
    public ImageView mCover;
    @BindView(R.id.ticket_back_press)
    public View backPress;

    @BindView(R.id.ticket_code)
    public EditText mTicketCode;

    @BindView(R.id.ticket_copy_or_open_btn)
    public TextView mOpenOrCopyBtn;

    @BindView(R.id.ticket_cover_loading)
    public View loadingView;

    @BindView(R.id.ticket_load_retry)
    public View retryLoadText;

    @Override
    protected void initPresenter() {
        mTickPresenter = PresenterManager.getInstance().getTickPresenter();
        if (mTickPresenter != null) {
            mTickPresenter.registerViewCallback(this);
        }
        //判断是否有安装淘宝
        //{act=android.intent.action.MAIN
        // cat=[android.intent.category.LAUNCHER]
        // flg=0x10200000
        // cmp=com.taobao.taobao/com.taobao.tao.welcome.Welcome
        //包名：com.taobao.taobao
        //检查是否安装
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao", PackageManager.MATCH_UNINSTALLED_PACKAGES);
            mHasTaobaoApp = packageInfo!=null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mHasTaobaoApp = false;
        }
        LogUtils.d(this,"mHasTaoApp-->"+mHasTaobaoApp);
        //根据这个值去修改ui
        mOpenOrCopyBtn.setText(mHasTaobaoApp?"打开淘宝领券":"复制淘口令");
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOpenOrCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制淘口令
                //拿到内容
                String ticketCode = mTicketCode.getText().toString().trim();
                LogUtils.d(TicketActivity.this,"ticketCode -->"+ticketCode);
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //复制到粘贴搬板
                ClipData clipData = ClipData.newPlainText("sob_taobao_ticket_code", ticketCode);
                cm.setPrimaryClip(clipData);

                //判断有没有淘宝，有就打开淘宝，没有就提示复制成功
                if (mHasTaobaoApp){
                    Intent taobaoIntent = new Intent();
//                    taobaoIntent.setAction("android.intent.action.MAIN");
//                    taobaoIntent.addCategory("android.intent.category.LAUNCHER");

                    //com.taobao.taobao/com.taobao.tao.TBMainActivity
                    ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.TBMainActivity");
                    taobaoIntent.setComponent(componentName);
                    startActivity(taobaoIntent);
                }else {
                    ToastUtils.showToast("已经复制，粘贴分享，或打开淘宝");
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tiket;
    }

    @Override
    protected void relese() {
        if (mTickPresenter != null) {
            mTickPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {
        //设置图片
        if (mCover != null&&!TextUtils.isEmpty(cover)) {
//            ViewGroup.LayoutParams layoutParams = mCover.getLayoutParams();
//            int tagetWidth = layoutParams.width/2;
//            LogUtils.d(this,"cover with -->"+tagetWidth);
            String coverPath = UrlUtils.getCoverPath(cover);
            Glide.with(this).load(coverPath).into(mCover);
        }
        if (TextUtils.isEmpty(cover)){
            mCover.setImageResource(R.mipmap.no_image);
        }
        LogUtils.d(this,"result-->"+result);
        LogUtils.d(this,"result.getData().getTbkTpwdCreateResponse-->"+result.getData().getTbk_tpwd_create_response());
        //设置code
        if (result != null&&result.getData().getTbk_tpwd_create_response()!=null) {
            mTicketCode.setText(result.getData().getTbk_tpwd_create_response().getData().getModel());
            LogUtils.d(this,"Model -->"+result.getData().getTbk_tpwd_create_response().getData().getModel());
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoading() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEmpty() {

    }
}
