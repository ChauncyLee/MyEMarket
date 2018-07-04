package com.chuncy.util;

import android.app.Application;

import com.chuncy.BuildConfig;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.xutils.x;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class MyApplication extends LitePalApplication {
//    {0}
    public static String absURL1="http://s.m.taobao.com/search?event_submit_do_new_search_auction=1&_input_charset=utf-8&searchfrom=1&action=home%3Aredirect_app_action&from=1&q=";
    public static String  getAbsURL2="&sst=1&n=20&buying=buyitnow&m=api4h5&wlsort=10&page=1";
    public static String detailURL1="https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/?data=%7B%22itemNumId%22%3A%22";
    public static String detailURL2="%22%7D&qq-pf-to=pcqq.group";
    public  static String chaneId="";
    public  static String token="95F8F2D99CFC3FC22D1BA4122816087F";
    public  static String putid="http://114.115.139.211:4002";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        LitePal.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
}
