package com.alex.wechatmoments.presenter;


import com.alex.wechatmoments.Utils.CommonUtils;
import com.alex.wechatmoments.adapter.RetrofitStringConverter;
import com.alex.wechatmoments.network.IMomentsBiz;

import retrofit.RestAdapter;

/**
 * Created by Chen on 2015/8/21.
 */
public class BasePresenter {

    public RestAdapter restAdapterHttp =new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new RetrofitStringConverter(CommonUtils.gson)).setEndpoint(IMomentsBiz.URL).build();
    public IMomentsBiz apiHttp=restAdapterHttp.create(IMomentsBiz.class);

}
