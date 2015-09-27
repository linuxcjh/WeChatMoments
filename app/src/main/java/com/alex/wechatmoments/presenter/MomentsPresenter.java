package com.alex.wechatmoments.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alex.wechatmoments.Utils.CommonUtils;
import com.alex.wechatmoments.adapter.MomentsAdapter;
import com.alex.wechatmoments.model.MomentsModel;
import com.alex.wechatmoments.model.UserInfoModel;
import com.alex.wechatmoments.view.IMomentsView;
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

    public IMomentsView momentsView;
    private MomentsAdapter adapter;
    private List<MomentsModel> mDatas;
    private List<MomentsModel> mInitDatas;
    private Context mContext;

    public MomentsPresenter(Context context, IMomentsView momentsView) {

        this.mContext = context;
        this.momentsView = momentsView;
        mInitDatas = new ArrayList<>();
        mDatas = new ArrayList<>();
        adapter = new MomentsAdapter(context, mDatas);
    }


    /**
     * Get user info
     */
    public void getUserInfo(String userName) {

        apiHttp.getUserInfo(userName, new Callback<String>() {
            @Override
            public void success(String userInfo, Response response) {

                String result = userInfo.replace("-", "");// profile-image  ~  profileImage

                UserInfoModel user =
                        CommonUtils.gson.fromJson(result, new TypeToken<UserInfoModel>() {
                        }.getType());

                momentsView.setUserInfo(user);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Get tweets
     *
     * @param userName
     */
    public void getListData(String userName) {

        apiHttp.getListData(userName, new Callback<String>() {
            @Override
            public void success(String resultStr, Response response) {

                mDatas = resultDatas(resultStr);
                for (int i = 0; i < 5; i++) {
                    mInitDatas.add(mDatas.get(i));
                }
                adapter.setDatas(mInitDatas);
                momentsView.showListData(adapter);

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Get result
     *
     * @param resultStr
     * @return
     */
    private List<MomentsModel> resultDatas(String resultStr) {
        String result = resultStr.replace("unknown ", "");

        List<MomentsModel> momentsModels = CommonUtils.gson.fromJson(result, new TypeToken<List<MomentsModel>>() {
        }.getType());

        List<MomentsModel> resultModels = new ArrayList<>();

        for (int i = 0; i < momentsModels.size(); i++) { //ignore the tweet which does not contain a content and images
            if (momentsModels.get(i).getImages() != null || !TextUtils.isEmpty(momentsModels.get(i).getContent())) {
                resultModels.add(momentsModels.get(i));
            }
        }

        return resultModels;
    }


    /**
     * refresh
     */
    public void setRefreshData() {
        adapter.setDatas(mInitDatas);
        adapter.notifyDataSetChanged();
    }

    /**
     * load more
     */
    public void setLoadMoreData() {
        adapter.setDatas(mDatas);
        adapter.notifyDataSetChanged();
    }


}
