package com.example.meydoon.data;

import java.text.DecimalFormat;

/**
 * Created by hooma on 2/8/2017.
 */
public class FeedItem {
    private int productId, shopId, isShipable;
    private String shopName, productDescription, productImage,
            shopProfilePic, productTitle, shopPhoneNumber,
            shopTelegramId, productRegisterDate, shopCity, productPrice;


    public FeedItem() {
    }

    public FeedItem(int productId, int shopId, String productPrice, String shopName, String productImage, String productDescription,
                    String shopProfilePic, String productTitle,
                    String shopPhoneNumber, String shopTelegramId, int isShipable,
                    String productRegisterDate, String shopCity) {
        super();
        this.shopId = shopId;
        this.productId = productId;
        this.shopName = shopName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.shopProfilePic = shopProfilePic;
        this.productTitle = productTitle;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopTelegramId = shopTelegramId;
        this.isShipable = isShipable;
        this.productPrice = productPrice;
        this.productRegisterDate = productRegisterDate;
        this.shopCity = shopCity;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
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

    public int getShipableStatus(){
        return isShipable;
    }

    public void setShipableStatus(int isShipable){
        this.isShipable = isShipable;
    }

    public String getProductRegisterDate(){
        return productRegisterDate;
    }

    public void setProductRegisterDate(String productRegisterDate){
        this.productRegisterDate = productRegisterDate;
    }

    public String getShopCity(){
        return shopCity;
    }

    public void setShopCity(String shopCity){
        this.shopCity = shopCity;
    }

}
