package com.alex.wechatmoments.network;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Chen on 2015/8/21.
 */
public interface IUserBiz {

      String URL = "http://thoughtworks-ios.herokuapp.com";

    @GET("/user/{userName}")
     void getUserInfo(@Path("userName") String userName, Callback<String> cb);


    @GET("/user/{userName}/tweets")
    void getListData(@Path("userName") String userName,Callback<String> cb);


}
