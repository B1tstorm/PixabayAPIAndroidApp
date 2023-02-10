package de.fh_kiel.iue.mob.models;

import android.graphics.Bitmap;

public class Picture {
    private String image;
    private String likes;
    private String views;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
