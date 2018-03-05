package ggdesign.limatex;

/**
 * Created by meg3o on 2/25/2018.
 */

public class SubItems {
    private String title;
    private String subtitle;
    private String price;
    private String priceB;
    private Integer imgURL;

    public SubItems(String title, String subtitle, Integer imgURL, String price, String priceB) {
        this.title = title;
        this.subtitle = subtitle;
        this.imgURL = imgURL;
        this.price = price;
        this.priceB = priceB;
    }

    public String getPriceB() {
        return priceB;
    }

    public void setPriceB(String priceB) {
        this.priceB = priceB;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
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
