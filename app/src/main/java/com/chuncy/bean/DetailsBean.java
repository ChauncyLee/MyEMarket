package com.chuncy.bean;

import java.util.List;

/**
 * Created by Chauncy on 2018/7/4.
 */
public class DetailsBean {

    private String title;
    private String subtitle;
    private List<String> images;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<String> getImages() {
        return images;
    }
}
