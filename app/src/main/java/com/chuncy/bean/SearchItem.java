package com.chuncy.bean;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class SearchItem   {

    private String image_search;
    private String search_item_name;
    private String search_item_describe;
    private String search_item_freight;
    private String search_item_price;

    public void setImage_search(String image_search) {
        this.image_search = image_search;
    }

    public void setSearch_item_name(String search_item_name) {
        this.search_item_name = search_item_name;
    }

    public void setSearch_item_describe(String search_item_describe) {
        this.search_item_describe = search_item_describe;
    }

    public void setSearch_item_freight(String search_item_freight) {
        this.search_item_freight = search_item_freight;
    }

    public void setSearch_item_price(String search_item_price) {
        this.search_item_price = search_item_price;
    }

    public String getImage_search() {
        return image_search;
    }

    public String getSearch_item_name() {
        return search_item_name;
    }

    public String getSearch_item_describe() {
        return search_item_describe;
    }

    public String getSearch_item_freight() {
        return search_item_freight;
    }

    public String getSearch_item_price() {
        return search_item_price;
    }
}
