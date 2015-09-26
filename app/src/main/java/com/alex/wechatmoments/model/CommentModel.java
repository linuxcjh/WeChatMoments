package com.alex.wechatmoments.model;

/**
 * Created by chen on 26/9/15.
 */
public class CommentModel {

    private   String content;
    private SenderModel sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderModel getSender() {
        return sender;
    }

    public void setSender(SenderModel sender) {
        this.sender = sender;
    }
}
