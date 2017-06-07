package com.example.meydoon.data;

/**
 * Created by hooma on 2/23/2017.
 */
public class ProfileGridItem {
    private int productId;
    private String productImage, productPrice, productTitle, productRegisterDate;

    public ProfileGridItem() {
    }

    public ProfileGridItem(int productId, String productImage, String productPrice, String productTitle, String productRegisterDate) {
        super();
        this.productId = productId;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productRegisterDate = productRegisterDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductRegisterDate() {
        return productRegisterDate;
    }

    public void setProductRegisterDate(String productRegisterDate) {
        this.productRegisterDate = productRegisterDate;
    }
}
