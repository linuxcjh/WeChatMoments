package com.alex.wechatmoments;

import android.app.Application;

import com.alex.wechatmoments.Utils.CommonUtils;

/**
 * Created by chen on 27/9/15.
 */
public class CustomApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.setContext(this);
        CommonUtils.isNetworkConnected(this);
    }
}
