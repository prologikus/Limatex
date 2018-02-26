package ggdesign.limatex;

import android.graphics.drawable.Drawable;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by meg3o on 2/25/2018.
 */

public class Categories {
    private String title;
    private String subtitle;
    private Integer imgURL;

    public Categories(String title, String subtitle, Integer imgURL) {
        this.title = title;
        this.subtitle = subtitle;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.title = subtitle;
    }

    public Integer getImgURL() {
        return imgURL;
    }

    public void setImgURL(Integer imgURL) {
        this.imgURL = imgURL;
    }
}
