package com.alex.wechatmoments.view;

import com.alex.wechatmoments.adapter.MomentsAdapter;
import com.alex.wechatmoments.model.UserInfoModel;

/**
 * Created by Chen on 2015/8/21.
 */
public interface IMomentsView {


    /**
     * 显示用户信息
     */
    void setUserInfo(UserInfoModel usrInfo);

    /**
     * 显示列表数据
     */
    void showListData(MomentsAdapter adapter);


}
