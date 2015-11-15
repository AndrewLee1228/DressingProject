package com.dressing.dressingproject.ui.models;

import java.io.Serializable;

/**
 * Created by lee on 15. 11. 6.
 */
public class ProductModel implements Serializable
{


    private String ProdutcName;
    private String ProductBrandName;
    private String ProductPrice;
    private String ProductLocation;
    private String ProductImgURL;
    private String ProductLogoImgURL;
    private String MapURL;
    private String ProductTitle;



    private String ProductNum;

    private boolean IsFavorite;

    public ProductModel(String productTitle, String produtcName, String productBrandName, String productPrice, String productLocation, String productImgURL, String mapURL,String productLogoImgURL,String productNum, boolean isFavorite) {
        ProductTitle = productTitle;
        ProdutcName = produtcName;
        ProductBrandName = productBrandName;
        ProductPrice = productPrice;
        ProductLocation = productLocation;
        ProductImgURL = productImgURL;
        ProductLogoImgURL = productLogoImgURL;
        MapURL = mapURL;
        ProductNum = productNum;
        IsFavorite = isFavorite;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductNum() {
        return ProductNum;
    }

    public void setProductNum(String productNum) {
        ProductNum = productNum;
    }

    public String getProdutcName() {
        return ProdutcName;
    }

    public void setProdutcName(String produtcName) {
        ProdutcName = produtcName;
    }

    public String getProductBrandName() {
        return ProductBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        ProductBrandName = productBrandName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductLocation() {
        return ProductLocation;
    }

    public void setProductLocation(String productLocation) {
        ProductLocation = productLocation;
    }

    public String getProductImgURL() {
        return ProductImgURL;
    }

    public void setProductImgURL(String productImgURL) {
        ProductImgURL = productImgURL;
    }

    public String getProductLogoImgURL() {
        return ProductLogoImgURL;
    }

    public void setProductLogoImgURL(String productLogoImgURL) {
        ProductLogoImgURL = productLogoImgURL;
    }

    public String getMapURL() {
        return MapURL;
    }

    public void setMapURL(String mapURL) {
        MapURL = mapURL;
    }

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        IsFavorite = isFavorite;
    }
}
