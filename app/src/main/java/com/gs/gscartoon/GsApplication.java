package com.gs.gscartoon;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.gs.gscartoon.utils.AppConstants;
import com.gs.gscartoon.utils.FrescoUtil;
import com.gs.gscartoon.utils.OkHttpUtil;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import io.realm.Realm;
import okhttp3.OkHttpClient;

/**
 * Created by camdora on 16-12-26. 12:00
 */

public class GsApplication extends Application {
    private final static String TAG = "GsApplication";

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Realm.init(this);//Realm 库进行初始化
        ImagePipelineConfig imagePipelineConfig = FrescoUtil.getConfig();
        if(imagePipelineConfig == null){
            Fresco.initialize(mContext);
        }else {
            Fresco.initialize(mContext, imagePipelineConfig);
        }

        //+++++++++Picasso 使用单例模式++++++++
        OkHttpClient okHttpClient = OkHttpUtil.getHeaderOkHttpClientBuilder().build();
        Picasso mPicasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        Picasso.setSingletonInstance(mPicasso);
        //---------Picasso 使用单例模式--------

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //++++初始化友盟统计
        UMConfigure.init(this, AppConstants.UMENG_APPKEY, AppConstants.UMENG_CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setEncryptEnabled(true);//设置日志加密
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计功能
        //----初始化友盟统计
    }

    public static Context getAppContext(){
        return mContext;
    }

}
