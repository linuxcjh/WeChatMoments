package com.alex.wechatmoments.presenter;

import android.text.TextUtils;

import com.alex.wechatmoments.Utils.CommonUtils;
import com.alex.wechatmoments.model.MomentsModel;
import com.alex.wechatmoments.model.UserInfoModel;
import com.alex.wechatmoments.view.IMommentsView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Chen on 2015/8/21.
 */
public class MomentsPresenter extends BasePresenter {

    public IMommentsView mommentsView;

    public MomentsPresenter(IMommentsView mommentsView) {

        this.mommentsView = mommentsView;
    }


    /**
     * Obtain user info
     */
    public void getUserInfo(String userName) {

        apiHttp.getUserInfo(userName, new Callback<String>() {
            @Override
            public void success(String userInfo, Response response) {

                String result = userInfo.replace("-", "");

                UserInfoModel user =
                        CommonUtils.gson.fromJson(result, new TypeToken<UserInfoModel>() {
                        }.getType());

                mommentsView.setUserInfo(user);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    /**
     * Obtain tweets
     *
     * @param userName
     */
    public void getListData(String userName) {

        apiHttp.getListData(userName, new Callback<String>() {
            @Override
            public void success(String resutlStr, Response response) {

                String result = resutlStr.replace("unknown ", "");

                List<MomentsModel> momentsModels = CommonUtils.gson.fromJson(result, new TypeToken<List<MomentsModel>>() {
                }.getType());

                List<MomentsModel> resultModels = new ArrayList<MomentsModel>();

                for (int i = 0; i < momentsModels.size(); i++) {
                    if (momentsModels.get(i).getImages() != null || !TextUtils.isEmpty(momentsModels.get(i).getContent())) {
                        resultModels.add(momentsModels.get(i));
                    }
                }

                mommentsView.showListData(resultModels);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
