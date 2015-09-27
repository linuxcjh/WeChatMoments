package com.alex.wechatmoments;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.wechatmoments.Utils.PullListView;
import com.alex.wechatmoments.Utils.RotateLayout;
import com.alex.wechatmoments.adapter.MomentsAdapter;
import com.alex.wechatmoments.model.MomentsModel;
import com.alex.wechatmoments.model.UserInfoModel;
import com.alex.wechatmoments.presenter.MomentsPresenter;
import com.alex.wechatmoments.view.IMommentsView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActivityBase implements IMommentsView{

    private MomentsPresenter momentsPresenter;
    private PullListView pullToRefreshListView;
    private RotateLayout rotateLayout;
    private MomentsAdapter adapter;
    private List<MomentsModel> mDatas;
    private ImageView userAvatarIv;
    private TextView  userNameTv;
    private ImageView profileImage;


    @Override
    public int setContentViewResId() {

        return R.layout.activity_main_test;
    }

    @Override
    public void findWigetAndListener() {

        pullToRefreshListView=getViewById(R.id.refreshlistview);
        rotateLayout=getViewById(R.id.rotateLayout);

        View headView = getLayoutInflater().from(this).inflate(R.layout.comments_headview_layout,null);
        View footView = getLayoutInflater().from(this).inflate(R.layout.footlayout,null);


        userAvatarIv=(ImageView)headView.findViewById(R.id.id_userAvatar);
        userNameTv = (TextView)headView.findViewById(R.id.id_userName);
        profileImage = (ImageView)headView.findViewById(R.id.id_headIv);

        pullToRefreshListView.setPullHeaderView(headView);
        pullToRefreshListView.setPullFooterView(footView);
        pullToRefreshListView.setRotateLayout(rotateLayout);
        pullToRefreshListView.setCacheColorHint(Color.TRANSPARENT);
        pullToRefreshListView.setDividerHeight(0);

        pullToRefreshListView.setOnRefreshListener(new PullListView.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pullToRefreshListView.onCompleteRefresh();
                    }
                }, 500);
            }
        });

        pullToRefreshListView.setOnLoadMoreListener(new PullListView.OnLoadMoreListener<ListView>() {
            @Override
            public void onLoadMore(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pullToRefreshListView.onCompleteRefresh();
                    }
                }, 500);
            }
        });

    }


    @Override
    public void initData() {

        mDatas =new ArrayList<>();
        adapter =new MomentsAdapter(this,mDatas);
        pullToRefreshListView.setAdapter(adapter);

        momentsPresenter = new MomentsPresenter(this);
        momentsPresenter.getUserInfo("jsmith");
        momentsPresenter.getListData("jsmith");

    }

    @Override
    public void setUserInfo(UserInfoModel infoModel) {

        userNameTv.setText(infoModel.getUsername());
        Glide.with(this).load(infoModel.getAvatar()).placeholder(R.mipmap.ic_launcher).into(userAvatarIv);
        Glide.with(this).load(infoModel.getProfileimage()).into(profileImage);
    }

    @Override
    public void showListData(List<MomentsModel> data) {

        this.mDatas = data;
        adapter.setDatas(data);
        adapter.notifyDataSetChanged();

    }

}
