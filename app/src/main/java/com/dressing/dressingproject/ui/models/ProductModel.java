package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 6.
 */
public class ProductModel
{



    private String ProdutcName;
    private String ProductBrandName;
    private String ProductPrice;
    private String ProductLocation;
    private String ProductImgURL;
    private String MapURL;
    private boolean IsFavorite;

    public ProductModel(String produtcName, String productBrandName, String productPrice, String productLocation, String productImgURL, String mapURL, boolean isFavorite) {
        ProdutcName = produtcName;
        ProductBrandName = productBrandName;
        ProductPrice = productPrice;
        ProductLocation = productLocation;
        ProductImgURL = productImgURL;
        MapURL = mapURL;
        IsFavorite = isFavorite;
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
