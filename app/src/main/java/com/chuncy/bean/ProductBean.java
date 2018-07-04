package com.chuncy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class ProductBean extends LitePalSupport {

    private int id;
    private String item_id;
    private String title;
    private String price;
    private String place;
    private String accountPay;
    private String picURL;
    private String shipping;

    public void setId(int id) {
        this.id = id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setAccountPay(String accountPay) {
        this.accountPay = accountPay;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPlace() {
        return place;
    }

    public String getAccountPay() {
        return accountPay;
    }

    public String getPicURL() {
        return picURL;
    }

    public String getShipping() {
        return shipping;
    }

    public int getId() {
        return id;
    }
}


