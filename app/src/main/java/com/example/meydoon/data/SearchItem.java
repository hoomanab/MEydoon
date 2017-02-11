package com.example.meydoon.data;

/**
 * Created by hooma on 2/10/2017.
 */
public class SearchItem {
    private int shopId;
    private String shopName, shopCategory, shopProfilePic, shopCity, shopUrl;

    public SearchItem() {
    }

    public SearchItem(int shopId, String shopName, String shopCategory, String shopProfilePic,
                    String shopCity, String shopUrl) {
        super();
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopCategory = shopCategory;
        this.shopProfilePic = shopProfilePic;
        this.shopCity = shopCity;
        this.shopUrl = shopUrl;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopProfilePic() {
        return shopProfilePic;
    }

    public void setShopProfilePic(String shopProfilePic) {
        this.shopProfilePic = shopProfilePic;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

}
