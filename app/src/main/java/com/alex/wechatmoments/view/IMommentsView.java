package com.alex.wechatmoments.view;

import com.alex.wechatmoments.model.MomentsModel;
import com.alex.wechatmoments.model.UserInfoModel;

import java.util.List;

/**
 * Created by Chen on 2015/8/21.
 */
public interface IMommentsView {


    /**
     * 显示用户信息
     */
    void setUserInfo(UserInfoModel usrInfo);

    /**
     * 显示列表数据
     */
    void showListData(List<MomentsModel> data);


}
