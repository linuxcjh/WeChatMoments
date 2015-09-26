package com.alex.wechatmoments.model;

import java.util.List;

/**
 * Created by chen on 25/9/15.
 */
public class MomentsModel {


    private String content;
    private List<ImageModel> images;
    private SenderModel sender;
    private List<CommentModel> comments;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    public SenderModel getSender() {
        return sender;
    }

    public void setSender(SenderModel sender) {
        this.sender = sender;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
}
