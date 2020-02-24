package com.my.redditclone.model;


public class Topic {
    String postId;
    String postTitle;
    String postDescription;
    int postUpVote;
    int postDownVote;
    String postCreateDateTimeStamp;
    String postEditDateTimeStamp;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getPostUpVote() {
        return postUpVote;
    }

    public void setPostUpVote(int postUpVote) {
        this.postUpVote = postUpVote;
    }

    public int getPostDownVote() {
        return postDownVote;
    }

    public void setPostDownVote(int postDownVote) {
        this.postDownVote = postDownVote;
    }

    public String getPostCreateDateTimeStamp() {
        return postCreateDateTimeStamp;
    }

    public void setPostCreateDateTimeStamp(String postCreateDateTimeStamp) {
        this.postCreateDateTimeStamp = postCreateDateTimeStamp;
    }

    public String getPostEditDateTimeStamp() {
        return postEditDateTimeStamp;
    }

    public void setPostEditDateTimeStamp(String postEditDateTimeStamp) {
        this.postEditDateTimeStamp = postEditDateTimeStamp;
    }
}
