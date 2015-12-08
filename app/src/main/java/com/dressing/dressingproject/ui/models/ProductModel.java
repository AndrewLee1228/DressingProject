package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lee on 15. 11. 6.
 */
public class ProductModel implements Serializable
{

    private int selectedNum;
    @SerializedName("itemNum")
    private int ProductNum;
    @SerializedName("itemName")
    private String ProdutcTitleName;
    @SerializedName("itemPrice")
    private int ProductPrice;
    @SerializedName("brandName")
    private String ProductBrandName;
    @SerializedName("productName")
    private String ProductSubName;
    private String mallName;
    private String shopName;
    @SerializedName("itemImg")
    private String ProductImgURL;
    @SerializedName("selectedFlag")
    private int IsFavorite;
    @SerializedName("fittingFlag")
    private int isFit;
    @SerializedName("brandImg")
    private String ProductLogoImgURL;
    @SerializedName("shoppositionImg")
    private String MapURL;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

    public int getSelectedNum() {
        return selectedNum;
    }

    public void setSelectedNum(int selectedNum) {
        this.selectedNum = selectedNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getProductSubName() {
        return ProductSubName;
    }

    public void setProductSubName(String productSubName) {
        ProductSubName = productSubName;
    }


    public String getProductNum() {
        return Integer.toString(ProductNum);
    }

    public void setProductNum(String productNum) {
        ProductNum = Integer.parseInt(productNum);
    }

    public String getProductName() {
        return ProdutcTitleName;
    }

    public void setProdutcTitleName(String produtcTitleName) {
        ProdutcTitleName = produtcTitleName;
    }

    public String getProductBrandName() {
        return ProductBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        ProductBrandName = productBrandName;
    }

    public String getProductPrice() {
        return Integer.toString(ProductPrice);
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = Integer.parseInt(productPrice);
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
        if (IsFavorite > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void setIsFavorite(boolean isFavorite) {
        if (isFavorite)
        {
            IsFavorite = 1;
        }
        else
        {
            IsFavorite = 0;
        }
    }

    public boolean isFit()
    {
        if(isFit >0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setFit(boolean isFit) {
        if(isFit)
        {
            this.isFit = 1;
        }
        else
        {
            this.isFit = 0;
        }
    }
}
