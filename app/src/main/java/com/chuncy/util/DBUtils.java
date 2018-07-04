package com.chuncy.util;

import android.content.Context;
import android.util.Log;

import com.chuncy.bean.ProductBean;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class DBUtils {


    private Context mcontext;
    public DBUtils(Context context){
        this.mcontext=context;
    }
    public  void saveToDb(HashMap<String,String> map){
        String item_id= map.get("item_id");
        String title=map.get("title");
        String price=map.get("price");
        String place=map.get("place");
        String accountPay=map.get("accountPay");
        String picURL=map.get("picURL");
        String shipping=map.get("shipping");
        ProductBean bean=new ProductBean();
        bean.setItem_id(item_id);
        bean.setTitle(title);
        bean.setPrice(price);
        bean.setPlace(place);
        bean.setAccountPay(accountPay);
        bean.setPicURL(picURL);
        bean.setShipping(shipping);
        bean.saveThrows();


    }

    /**
     * 根据id查询
     * @param item_id
     * @return
     */
    public List<ProductBean> select(String item_id){
        List<ProductBean> mlist = LitePal.where("item_id==?",item_id).find(ProductBean.class);

        return mlist;
    }

    /**
     * 全部查询
     * @return
     */
    public List<ProductBean> selectall(){
        List<ProductBean> mlist = LitePal.findAll(ProductBean.class);
        return mlist;
    }

    /**
     * 删除制定数据表的数据
     * @param id
     * @return
     */
    public boolean deleteItem(int id){
        LitePal.delete(ProductBean.class, id);
        return true;
    }


}
