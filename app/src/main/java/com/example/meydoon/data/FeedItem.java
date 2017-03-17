package com.example.meydoon.data;

/**
 * Created by hooma on 2/8/2017.
 */
public class FeedItem {
    private int productId, shopId;
    private String shopName, productDescription, productImage, shopProfilePic, timeStamp, productTitle, shopPhoneNumber, shopTelegramId;
    private Boolean isShipable;

    public FeedItem() {
    }

    public FeedItem(int productId, int shopId, String shopName, String productImage, String productDescription,
                    String shopProfilePic, String timeStamp, String productTitle,
                    String shopPhoneNumber, String shopTelegramId, Boolean isShipable) {
        super();
        this.shopId = shopId;
        this.productId = productId;
        this.shopName = shopName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.shopProfilePic = shopProfilePic;
        this.timeStamp = timeStamp;
        this.productTitle = productTitle;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopTelegramId = shopTelegramId;
        this.isShipable = isShipable;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getShopProfilePic() {
        return shopProfilePic;
    }

    public void setShopProfilePic(String shopProfilePic) {
        this.shopProfilePic = shopProfilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getShopTelegramId() {
        return shopTelegramId;
    }

    public void setShopTelegramId(String shopTelegramId) {
        this.productTitle = shopTelegramId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Boolean getShipableStatus(){
        return isShipable;
    }

    public void setShipableStatus(Boolean isShipable){
        this.isShipable = isShipable;
    }
}
