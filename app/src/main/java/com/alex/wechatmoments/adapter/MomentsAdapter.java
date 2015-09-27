package com.alex.wechatmoments.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.wechatmoments.R;
import com.alex.wechatmoments.Utils.CommonUtils;
import com.alex.wechatmoments.Utils.CustomTextView;
import com.alex.wechatmoments.model.MomentsModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Moments Adapter
 */
public class MomentsAdapter extends BaseAdapter {

    private final int ALL_PICTURES_SIZE = 90;
    private final int ZERO_PICTURES_SIZE = 0;

    private LayoutInflater mLayoutInflater;
    private List<MomentsModel> momentsModels;
    private MomentsModel model;
    private Context mContext;


    public MomentsAdapter(Context context, List<MomentsModel> momentsModels) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.momentsModels = momentsModels;

    }

    public void setDatas(List<MomentsModel> data) {

        this.momentsModels = data;
    }

    @Override
    public int getCount() {
        return momentsModels != null ? momentsModels.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mLayoutInflater.inflate(R.layout.comments_item_layout, null);

            holder.avatarImageView = (ImageView) convertView.findViewById(R.id.id_avatarIv);
            holder.senderTv = (TextView) convertView.findViewById(R.id.id_senderTv);
            holder.contentTv = (CustomTextView) convertView.findViewById(R.id.id_contentTv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.id_timeTv);
            holder.operateImageView = (ImageView) convertView.findViewById(R.id.id_operateIv);
            holder.commentsGridLayout = (GridLayout) convertView.findViewById(R.id.id_commentsGridLayout);
            holder.imagesGridLayaout = (GridLayout) convertView.findViewById(R.id.id_gridLayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = momentsModels.get(position);

        holder.imagesGridLayaout.removeAllViews();

        if (model.getSender() != null) {//set user info
            Glide.with(mContext).load(model.getSender().getAvatar()).placeholder(R.mipmap.default_avatar).into(holder.avatarImageView);
            holder.senderTv.setText(model.getSender().getUsername());

        }
        if(TextUtils.isEmpty(model.getContent())){//set content
            holder.contentTv.setVisibility(View.GONE);
        }else{
            holder.contentTv.setVisibility(View.VISIBLE);
            holder.contentTv.setText(model.getContent());
        }

        setImagesLayout(model, holder.imagesGridLayaout);//set images
        setCommentsLayout(model,holder.commentsGridLayout);//set comments

        return convertView;
    }

    /**
     * Comments layout
     * @param model
     * @param commentsGridLayout
     */
    private void setCommentsLayout(MomentsModel model,GridLayout commentsGridLayout){
        commentsGridLayout.removeAllViews();


        if (model.getComments() != null && model.getComments().size() > 0) {
            commentsGridLayout.setColumnCount(1);
            commentsGridLayout.setRowCount(model.getComments().size());

            for (int i = 0; i < model.getComments().size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.moments_item_comments_layout, null);
                final TextView userNameTv = (TextView) view.findViewById(R.id.id_userName);
                final TextView contentTv = (TextView) view.findViewById(R.id.id_content);
                userNameTv.setText(model.getComments().get(i).getSender().getUsername() + "：");
                contentTv.setText(model.getComments().get(i).getContent());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, userNameTv.getText().toString()+contentTv.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
                view.setPadding(20,10,20,10);
                commentsGridLayout.addView(view);
            }
        }

    }

    /**
     * Images Layout
     *
     * @param model
     * @param imagesGridLayaout
     */
    private void setImagesLayout(MomentsModel model, GridLayout imagesGridLayaout) {

        imagesGridLayaout.removeAllViews();

        if (model.getImages() != null && model.getImages().size() > 0) {

            int column = 3;
            int pictureSize = ALL_PICTURES_SIZE;
            int row = model.getImages().size() / column + (model.getImages().size() % column == 0 ? 0 : 1);

            imagesGridLayaout.setRowCount(row);

            if (model.getImages().size() == 4) { // Four pictures
                column = 2;
            } else if (model.getImages().size() == 1) { //a picture
                column = 1;
                pictureSize = ZERO_PICTURES_SIZE;
            }
            imagesGridLayaout.setColumnCount(column);
            int count = 0;
            for (int i = 0; i < row; i++)
                for (int j = 0; j < column; j++) {

                    if (model.getImages().size() > count) {

                        View view = LayoutInflater.from(mContext).inflate(R.layout.moments_item_pictrue_layout, null);
                        ImageView imageView = (ImageView) view.findViewById(R.id.id_picture);

                        if (pictureSize != ZERO_PICTURES_SIZE) {
                            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                            layoutParams.width = CommonUtils.dpToPx(mContext, pictureSize);
                            layoutParams.height = CommonUtils.dpToPx(mContext, pictureSize);
                            imageView.setLayoutParams(layoutParams);
                        }

                        Glide.with(mContext).load(model.getImages().get(count).getUrl()).placeholder(R.mipmap.default_avatar).into(imageView);
//                        Glide.with(mContext).load("http://farm1.staticflickr.com/134/325376313_4ed1988001.jpg").placeholder(R.mipmap.default_avatar).into(imageView);

                        final String url = model.getImages().get(count).getUrl();
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "" + url, Toast.LENGTH_SHORT).show();
                            }
                        });
                        count++;
                        GridLayout.Spec rowSpec = GridLayout.spec(i);     //Set rows and columns
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.setGravity(Gravity.FILL);

                        imagesGridLayaout.addView(view, params);
                    }

                }
        }
    }
}

final class ViewHolder {

    public ImageView avatarImageView;
    public TextView senderTv;
    public CustomTextView contentTv;
    public TextView timeTv;
    public ImageView operateImageView;
    public GridLayout commentsGridLayout;
    public GridLayout imagesGridLayaout;
}
