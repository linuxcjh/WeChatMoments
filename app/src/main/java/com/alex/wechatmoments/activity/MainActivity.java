package com.alex.wechatmoments.activity;

import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.wechatmoments.R;
import com.alex.wechatmoments.Utils.CustomListView;
import com.alex.wechatmoments.Utils.RotateLayout;
import com.alex.wechatmoments.adapter.MomentsAdapter;
import com.alex.wechatmoments.model.UserInfoModel;
import com.alex.wechatmoments.presenter.MomentsPresenter;
import com.alex.wechatmoments.view.IMomentsView;
import com.bumptech.glide.Glide;

/**
 * Moments Activity
 */
public class MainActivity extends ActivityBase implements IMomentsView {

    /**
     * Moments Data manager
     */
    private MomentsPresenter momentsPresenter;
    /**
     * Pull down ListView
     */
    private CustomListView customListView;
    /**
     * Circle progressBar
     */
    private RotateLayout rotateLayout;
    /**
     * User avatar
     */
    private ImageView userAvatarIv;
    /**
     * User name
     */
    private TextView userNameTv;
    /**
     * Profile imageView
     */
    private ImageView profileImage;


    @Override
    public int setContentViewResId() {

        return R.layout.activity_moments_main_layout;
    }

    @Override
    public void findWigetAndListener() {

        customListView = getViewById(R.id.refreshlistview);
        rotateLayout = getViewById(R.id.rotateLayout);

        View headView = getLayoutInflater().from(this).inflate(R.layout.comments_headview_layout, null);// HeadView layout
        userAvatarIv = (ImageView) headView.findViewById(R.id.id_userAvatar);
        userNameTv = (TextView) headView.findViewById(R.id.id_userName);
        profileImage = (ImageView) headView.findViewById(R.id.id_headIv);

        View footView = getLayoutInflater().from(this).inflate(R.layout.footer_layout, null);//FooterView layout

        customListView.setPullHeaderView(headView);
        customListView.setPullFooterView(footView);
        customListView.setRotateLayout(rotateLayout);
        customListView.setCacheColorHint(Color.TRANSPARENT);
        customListView.setDividerHeight(0);

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(CustomListView refreshView) { //ListView RefreshListener
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        momentsPresenter.setRefreshData();
                        customListView.onCompleteRefresh();
                    }
                }, 500);
            }
        });

        customListView.setOnLoadMoreListener(new CustomListView.OnLoadMoreListener<ListView>() {
            @Override
            public void onLoadMore(CustomListView refreshView) {//ListView OnLoadListener
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        momentsPresenter.setLoadMoreData();
                        customListView.onCompleteLoadMore();
                    }
                }, 500);
            }
        });

    }


    /**
     * This appears when the list is empty
     */
    private void setEmptyView(){
        TextView emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("Loading ……");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)customListView.getParent()).addView(emptyView);
        customListView.setEmptyView(emptyView);
    }

    @Override
    public void initData() {
        setEmptyView();
        momentsPresenter = new MomentsPresenter(this,this);
        momentsPresenter.getUserInfo("jsmith");
        momentsPresenter.getListData("jsmith");

    }

    /**
     * Display user info
     * @param infoModel
     */
    @Override
    public void setUserInfo(UserInfoModel infoModel) {

        userNameTv.setText(infoModel.getUsername());
        Glide.with(this).load(infoModel.getAvatar()).placeholder(R.mipmap.default_avatar).into(userAvatarIv);
        Glide.with(this).load(infoModel.getProfileimage()).placeholder(R.mipmap.header_image).into(profileImage);
    }

    /**
     * Display tweets
     * @param adapter
     */
    @Override
    public void setListData(MomentsAdapter adapter) {

        customListView.setAdapter(adapter);

    }

}
