package com.alex.wechatmoments.presenter;


import com.alex.wechatmoments.Utils.CommonUtils;
import com.alex.wechatmoments.adapter.RetrofitStringConverter;
import com.alex.wechatmoments.network.IUserBiz;

import retrofit.RestAdapter;

/**
 * Created by Chen on 2015/8/21.
 */
public class BasePresenter {

    /* HTTP REQUEST Request of json type */
//    public RestAdapter restAdapterHttp =new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(CommonUtils.gson)).setEndpoint(IUserBiz.URL).build();

    public RestAdapter restAdapterHttp =new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new RetrofitStringConverter(CommonUtils.gson)).setEndpoint(IUserBiz.URL).build();
    public IUserBiz apiHttp=restAdapterHttp.create(IUserBiz.class);


    /* HTTPS REQUEST Request of json type */
//    public RestAdapter restAdapterHttps =new RestAdapter.Builder().setClient(new ApacheClient(HttpsClient.newHttpsClient())).setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(IUserBiz.URL).build();
//    public IUserBiz apiHttps=restAdapterHttps.create(IUserBiz.class);

}
