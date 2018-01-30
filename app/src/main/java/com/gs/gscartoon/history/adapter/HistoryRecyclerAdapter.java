package com.gs.gscartoon.history.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gs.gscartoon.BaseRecyclerAdapter;
import com.gs.gscartoon.BaseRecyclerVH;
import com.gs.gscartoon.R;
import com.gs.gscartoon.history.bean.HistoryBean;
import com.gs.gscartoon.utils.AppConstants;
import com.gs.gscartoon.utils.LogUtil;
import com.gs.gscartoon.utils.OkHttpUtil;
import com.gs.gscartoon.utils.PicassoRoundTransform;
import com.gs.gscartoon.utils.TimeUtil;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by camdora on 16-12-13.
 */

public class HistoryRecyclerAdapter extends BaseRecyclerAdapter<HistoryBean,
        HistoryRecyclerAdapter.HistoryRecyclerHolder> {

    private Context mContext;
    private Picasso mPicasso;

    public HistoryRecyclerAdapter(Context context) {
        super(context);
        mContext = context;

        OkHttpClient okHttpClient = OkHttpUtil.getHeaderOkHttpClientBuilder().build();
        mPicasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_history;
    }

    @Override
    protected HistoryRecyclerHolder createViewHolder(View itemView) {
        HistoryRecyclerHolder mRecyclerHolder = new HistoryRecyclerHolder(itemView);
        return mRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(final HistoryRecyclerHolder holder, int position) {
        HistoryBean bean = mData.get(position);
        if(bean == null){
            LogUtil.e(TAG,"bean==null");
            return;
        }

        //holder.sdvCover.setImageURI(Uri.parse(bean.getCoverUrl()));
        mPicasso.load(bean.getCoverUrl()).placeholder(R.drawable.ic_kuaikan_default_image_vertical)
                .error(R.drawable.ic_kuaikan_default_image_vertical)
                .transform(new PicassoRoundTransform(7))
                .into(holder.ivCover);

        holder.mtvTitle.setText(bean.getComicName());
        holder.tvTime.setText(TimeUtil.timestampToDate(bean.getUpdateTime().getTime()/1000, "yyyy-MM-dd hh:mm:ss"));
        holder.tvSee.setText("看到"+bean.getChapterTitle());

        if(bean.getOrigin() == AppConstants.KUAI_KAN_INT){
            holder.tvOrigin.setText("来源：" + mContext.getString(R.string.kuaikan));
            holder.tvOrigin.setTextColor(mContext.getResources().getColor(R.color.KuaiKanColor));
        }else if(bean.getOrigin() == AppConstants.ZHI_JIA_INT){
            holder.tvOrigin.setText("来源：" + mContext.getString(R.string.zhijia));
            holder.tvOrigin.setTextColor(mContext.getResources().getColor(R.color.ZhiJiaColor));
        }else {//未知
            holder.tvOrigin.setText("来源：" + mContext.getString(R.string.not_set));
            holder.tvOrigin.setTextColor(mContext.getResources().getColor(R.color.BLACK));
        }
    }

    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
        void Continue(View view, int position);
    }

    public class HistoryRecyclerHolder extends BaseRecyclerVH<HistoryBean>
            implements View.OnClickListener{

        private RelativeLayout mRootView;
        private ImageView ivCover;
        private TextView mtvTitle, tvTime, tvSee, tvContinue, tvOrigin;

        public HistoryRecyclerHolder(View itemView) {
            super(itemView);
            mRootView = (RelativeLayout) itemView.findViewById(R.id.rl_item_root_view);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
            mtvTitle = (TextView) itemView.findViewById(R.id.mtv_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvSee = (TextView) itemView.findViewById(R.id.tv_see);
            tvContinue = (TextView) itemView.findViewById(R.id.tv_continue);
            tvOrigin = (TextView) itemView.findViewById(R.id.tv_origin);

            mRootView.setOnClickListener(this);
            tvContinue.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                switch (v.getId()) {
                    case R.id.rl_item_root_view:
                        clickListener.onClick(itemView, getAdapterPosition());
                        break;
                    case R.id.tv_continue:
                        clickListener.Continue(itemView, getAdapterPosition());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}