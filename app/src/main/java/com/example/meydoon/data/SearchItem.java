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

    public int getId() {
        return shopId;
    }

    public void setId(int id) {
        this.shopId = shopId;
    }

    public String getName() {
        return shopName;
    }

    public void setName(String name) {
        this.shopName = shopName;
    }

    public String getImge() {
        return shopCategory;
    }

    public void setImge(String image) {
        this.shopCategory = shopCategory;
    }

    public String getStatus() {
        return shopProfilePic;
    }

    public void setStatus(String status) {
        this.shopProfilePic = shopProfilePic;
    }

    public String getProfilePic() {
        return shopCity;
    }

    public void setProfilePic(String profilePic) {
        this.shopCity = shopCity;
    }

    public String getTimeStamp() {
        return shopUrl;
    }

    public void setTimeStamp(String timeStamp) {
        this.shopUrl = shopUrl;
    }

}
