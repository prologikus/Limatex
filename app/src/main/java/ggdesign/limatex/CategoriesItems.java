package ggdesign.limatex;

/**
 * Created by meg3o on 2/25/2018.
 */

public class CategoriesItems {
    private String title;
    private String subtitle;
    private String price;
    private Integer count;
    private Integer imgURL;

    public CategoriesItems(String title, String subtitle, Integer imgURL, String price, Integer count) {
        this.title = title;
        this.subtitle = subtitle;
        this.imgURL = imgURL;
        this.price = price;
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
