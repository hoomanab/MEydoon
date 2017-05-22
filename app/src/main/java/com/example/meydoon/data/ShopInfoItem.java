package com.example.meydoon.data;

import java.text.DecimalFormat;

/**
 * Created by hooma on 2/8/2017.
 */
public class ShopInfoItem {
    private int shopFollowerCounter, shopId, shopUserId, shopProductCounter, shopIsVerified;
    private Boolean viewerIsOwner, viewerIsFollower;
    private String shopName, shopProfilePic, shopPhoneNumber,
            shopTelegramId, shopCity, shopAddress, shopRegisterDate, shopCategoryName, shopDescription;


    public ShopInfoItem() {
    }

    public ShopInfoItem(int shopId, int shopFollowerCounter, int shopProductCounter,
                        int shopIsVerified, int shopUserId, Boolean viewerIsOwner, Boolean viewerIsFollower, String shopName, String shopAddress,
                    String shopProfilePic, String shopCategoryName, String shopPhoneNumber, String shopTelegramId,
                    String shopRegisterDate, String shopCity, String shopDescription) {
        super();
        this.shopId = shopId;
        this.shopUserId = shopUserId;
        this.shopFollowerCounter = shopFollowerCounter;
        this.shopProductCounter = shopProductCounter;
        this.shopIsVerified = shopIsVerified;
        this.viewerIsOwner = viewerIsOwner;
        this.viewerIsFollower = viewerIsFollower;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopCategoryName = shopCategoryName;
        this.shopProfilePic = shopProfilePic;
        this.shopDescription = shopDescription;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopTelegramId = shopTelegramId;
        this.shopRegisterDate = shopRegisterDate;
        this.shopCity = shopCity;
    }

    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getShopUserId() {
        return shopUserId;
    }
    public void setShopUserId(int shopUserId) {
        this.shopUserId = shopUserId;
    }

    public int getShopFollowerCounter() {
        return shopFollowerCounter;
    }
    public void setShopFollowerCounter(int shopFollowerCounter) {
        this.shopFollowerCounter = shopFollowerCounter;
    }

    public int getShopProductCounter() {
        return shopProductCounter;
    }
    public void setShopProductCounter(int shopProductCounter) {
        this.shopProductCounter = shopProductCounter;
    }

    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopIsVerified() {
        return shopIsVerified;
    }
    public void setShopIsVerified(int shopIsVerified) {
        this.shopIsVerified = shopIsVerified;
    }

    public Boolean getViewerIsOwnerStatus() {
        return viewerIsOwner;
    }
    public void setViewerIsOwnerStatus(Boolean viewerIsOwner) {
        this.viewerIsOwner = viewerIsOwner;
    }

    public Boolean getviewerIsFollowerStatus() {
        return viewerIsFollower;
    }
    public void setViewerIsFollowerStatus(Boolean viewerIsFollower) {
        this.viewerIsFollower = viewerIsFollower;
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
        this.shopTelegramId = shopTelegramId;
    }

    public String getShopAddress() {
        return shopAddress;
    }
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopRegisterDate(){
        return shopRegisterDate;
    }
    public void setShopRegisterDate(String shopRegisterDate){
        this.shopRegisterDate = shopRegisterDate;
    }

    public String getShopCategoryName(){
        return shopCategoryName;
    }
    public void setShopCategoryName(String shopCategoryName){
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCity(){
        return shopCity;
    }
    public void setShopCity(String shopCity){
        this.shopCity = shopCity;
    }

    public String getShopProfilePic(){
        return shopProfilePic;
    }
    public void setShopProfilePic(String shopProfilePic){
        this.shopProfilePic = shopProfilePic;
    }

    public String getShopDescription(){
        return shopDescription;
    }
    public void setShopDescription(String shopDescription){
        this.shopDescription = shopDescription;
    }

}
