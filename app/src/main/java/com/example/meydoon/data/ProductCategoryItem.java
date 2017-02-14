package com.example.meydoon.data;

/**
 * Created by hooma on 2/13/2017.
 */
public class ProductCategoryItem {
    private int productCategoryId;
    private String productCategoryName;

    public ProductCategoryItem() {
    }

    public ProductCategoryItem(int productCategoryId, String productCategoryName) {
        super();
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }


}
