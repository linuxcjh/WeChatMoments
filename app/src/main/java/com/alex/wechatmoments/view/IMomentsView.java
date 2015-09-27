package com.alex.wechatmoments.view;

import com.alex.wechatmoments.adapter.MomentsAdapter;
import com.alex.wechatmoments.model.UserInfoModel;

/**
 * Created by Chen on 2015/8/21.
 */
public interface IMomentsView {


    /**
     * Display user info
     */
    void setUserInfo(UserInfoModel usrInfo);

    /**
     * Display tweets in listView
     */
    void setListData(MomentsAdapter adapter);


}
