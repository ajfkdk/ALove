package com.example.myapp_ui.domain;

public class image_info {
    private String image_url;
    private String imageid;

    public image_info() {
    }

    @Override
    public String toString() {
        return "image_info{" +
                "image_url='" + image_url + '\'' +
                ", imageid='" + imageid + '\'' +
                '}';
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public image_info(String image_url, String imageid) {
        this.image_url = image_url;
        this.imageid = imageid;
    }
}
