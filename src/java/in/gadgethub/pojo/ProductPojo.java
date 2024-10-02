/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

import java.io.InputStream;

/**
 *
 * @author sandeepkumar
 */
public class ProductPojo {
    private String prodId;
    private String prodName;
    private String prodType;
    private String prodInfo;
    private double prodPrice;
    private int prodQuantity;
    private InputStream prodimage;

    public ProductPojo() {
    }
    
    
    public ProductPojo(String prodId, String prodName, String prodType, String prodInfo, double prodPrice, int prodQuantity, InputStream prodimage) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodType = prodType;
        this.prodInfo = prodInfo;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
        this.prodimage = prodimage;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdInfo() {
        return prodInfo;
    }

    public void setProdInfo(String prodInfo) {
        this.prodInfo = prodInfo;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public InputStream getProdimage() {
        return prodimage;
    }

    public void setProdimage(InputStream prodimage) {
        this.prodimage = prodimage;
    }

    @Override
    public String toString() {
        return "ProductPojo{" + "prodId=" + prodId + ", prodName=" + prodName + ", prodType=" + prodType + ", prodInfo=" + prodInfo + ", prodPrice=" + prodPrice + ", prodQuantity=" + prodQuantity + ", prodimage=" + prodimage + '}';
    }
    
}
