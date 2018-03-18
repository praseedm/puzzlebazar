package com.p6c.project.puzzlebazar.model;

public class ScoreObj {
    int score;
    String category;
    String Uid;
    String userName;
    String photoUri;

    public ScoreObj() {
    }

    public ScoreObj(String uid, String userName,String photoUri,int score, String category) {
        this.score = score;
        this.category = category;
        Uid = uid;
        this.userName = userName;
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
