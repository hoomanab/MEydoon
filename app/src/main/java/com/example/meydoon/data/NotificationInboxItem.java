package com.example.meydoon.data;

/**
 * Created by hooma on 2/20/2017.
 */
public class NotificationInboxItem {
    private int shopId;
    private String shopName, shopProfilePic, time, message;

    public NotificationInboxItem() {
    }

    public NotificationInboxItem(int shopId, String shopName, String time, String shopProfilePic,
                      String message) {
        super();
        this.shopId = shopId;
        this.shopName = shopName;
        this.time = time;
        this.shopProfilePic = shopProfilePic;
        this.message = message;
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

    public String getNotificationTime() {
        return time;
    }

    public void setSNotificationTime(String time) {
        this.time = time;
    }

    public String getShopProfilePic() {
        return shopProfilePic;
    }

    public void setShopProfilePic(String shopProfilePic) {
        this.shopProfilePic = shopProfilePic;
    }

    public String getNotificationMessage() {
        return message;
    }

    public void setNotificationMessage(String shopCity) {
        this.message = message;
    }


}
