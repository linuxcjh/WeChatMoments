package com.alex.wechatmoments.service;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Chen on 2015/8/21.
 */
public interface IMomentsBiz {

    String URL = "http://thoughtworks-ios.herokuapp.com";

    /**
     * Get user info
     * @param userName
     * @param cb
     */
    @GET("/user/{userName}")
     void getUserInfo(@Path("userName") String userName, Callback<String> cb);

    /**
     * Get tweets
     * @param userName
     * @param cb
     */
    @GET("/user/{userName}/tweets")
    void getListData(@Path("userName") String userName,Callback<String> cb);


}
