package com.chuncy.util;

import android.util.Log;

import com.chuncy.bean.DetailsBean;
import com.chuncy.bean.ProductBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class AnalyseJson {

    public static List<ProductBean> analysejson(String result){
        List<ProductBean> list=new ArrayList<ProductBean>();

        try {
            JSONObject json = new JSONObject(result);
            for (int i = 0; i < 50; i++) {
                ProductBean bean=new ProductBean();
                JSONArray jsonArray = json.getJSONArray("listItem");
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.getString("title");
                String sold=jsonObject.getString("sold");
                String location=jsonObject.getString("location");
                String pic_path=jsonObject.getString("img2");
                String price=jsonObject.getString("price");
                String item_id=jsonObject.getString("item_id");
                String shipping=jsonObject.getString("shipping");

                bean.setPicURL("http:"+pic_path);
                bean.setTitle(title);
                bean.setPrice(price);
                bean.setAccountPay(sold);
                bean.setPlace(location);
                bean.setItem_id(item_id);
                bean.setShipping(shipping);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static DetailsBean analyseDetailsJson(String result){
        DetailsBean bean=new DetailsBean();

        try {
            JSONObject json = new JSONObject(result);
            String ja = json.getString("data");
                JSONObject json1=new JSONObject(ja);
            JSONObject item=json1.getJSONObject("item");


/*JSONArray jsonArray = json.getJSONArray("listItem");
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.getString("title");*/
                String title = item.getString("title");
                String subtitle=item.getString("subtitle");
                JSONArray images=item.getJSONArray("images");
                List<String> imageurls=new ArrayList<String>();
                for(int i=0;i<images.length();i++){
                    imageurls.add("http:"+images.getString(i));
                }


                bean.setTitle(title);
                bean.setSubtitle(subtitle);
                bean.setImages(imageurls);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static ProductBean analyseDetailJson(String result){
        List<ProductBean> list=new ArrayList<ProductBean>();
        ProductBean bean=new ProductBean();

        try {
            JSONObject json = new JSONObject(result);
            JSONObject jsondata=json.getJSONObject("data");
            JSONObject jsonitem=jsondata.getJSONObject("item");
            String title=jsonitem.getString("title");
            String strImg=jsonitem.getString("images");
            String h5moduleDescUrl=jsonitem.getString("h5moduleDescUrl");
          /*  Pattern getNumPattern = Pattern.compile("\"\\S+\",\"");
            Matcher m = getNumPattern.matcher(strImg);
            String theNum = "";
            while (m.find()) {
                theNum = m.group();
            }
            theNum=theNum.substring(5,theNum.length()-3);
            Log.i("qqq", theNum);*/

            bean.setTitle(title);
            bean.setPicURL(strImg);
            //此处的place用来代替了详细页面最下面的图片的url
            bean.setPlace(h5moduleDescUrl);
            Log.i("imgurl", strImg);
            list.add(bean);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return bean;
    }


}
