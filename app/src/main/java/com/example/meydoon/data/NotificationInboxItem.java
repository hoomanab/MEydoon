package com.example.meydoon.data;

/**
 * An Object including a notification's elements!
 */
public class NotificationInboxItem {
    private int shopId, notificationId;
    private String shopName, shopProfilePic, time, messageTitle, message;

    public NotificationInboxItem() {
    }

    public NotificationInboxItem(int shopId, int notificationId, String shopName, String time, String shopProfilePic,
                      String messageTitle, String message) {
        super();
        this.shopId = shopId;
        this.notificationId = notificationId;
        this.shopName = shopName;
        this.time = time;
        this.shopProfilePic = shopProfilePic;
        this.messageTitle = messageTitle;
        this.message = message;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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

    public void setNotificationTime(String time) {
        this.time = time;
    }

    public String getShopProfilePic() {
        return shopProfilePic;
    }

    public void setShopProfilePic(String shopProfilePic) {
        this.shopProfilePic = shopProfilePic;
    }

    public String getNotificationMessageTitle() {
        return messageTitle;
    }

    public void setNotificationMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getNotificationMessage() {
        return message;
    }

    public void setNotificationMessage(String message){
        this.message = message;
    }

}
