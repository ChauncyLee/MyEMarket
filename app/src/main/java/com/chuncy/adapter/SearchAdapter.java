package com.chuncy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chuncy.R;
import com.chuncy.bean.ProductBean;
import com.chuncy.bean.SearchItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chauncy on 2018/7/3.
 */
public class SearchAdapter extends BaseAdapter  {
    /**
     * 上下文
     * 容器
     * 布局加载器
     * 数据源
     */

    private Context context;
    private List<ProductBean> mylist =new ArrayList<>();
    private LayoutInflater inflater;
    private ProductBean bean;
    //设置内部空间响应事件
//    private Calback mCallback;

//    /**
//     * 自定义接口，用于回调按钮点击事件到Activity
//     */
//    public interface Calback {
//        public void click(View v);
//    }
    public SearchAdapter(Context context, List<ProductBean> mylist){
        this.context=context;
        this.mylist=mylist;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        mCallback = callback;
    }
    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder=null;
        if(convertView==null){
            vHolder=new ViewHolder();

            convertView=inflater.inflate(R.layout.layout_search_item,null);
            vHolder.search_item_itemid=(TextView) convertView.findViewById(R.id.search_item_itemid);
            vHolder.img_search= (SimpleDraweeView) convertView.findViewById(R.id.image_search);
            vHolder.eah_item_name= (TextView) convertView.findViewById(R.id.search_item_name);
            vHolder.eah_item_freight= (TextView) convertView.findViewById(R.id.search_item_freight);
            vHolder.eah_item_price= (TextView) convertView.findViewById(R.id.search_item_price);
            vHolder.search_item_place=(TextView)convertView.findViewById(R.id.search_item_place);
            vHolder.search_item_accountPay=(TextView)convertView.findViewById(R.id.search_item_accountPay);
            convertView.setTag(vHolder);
        }
        else{
            vHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        bean= (ProductBean) getItem(position);
        vHolder.search_item_itemid.setText(bean.getItem_id());
        vHolder.img_search.setImageURI(bean.getPicURL());
        vHolder.eah_item_name.setText(bean.getTitle());
        vHolder.eah_item_freight.setText("快递："+bean.getShipping());
        vHolder.search_item_place.setText(bean.getPlace());
        vHolder.eah_item_price.setText("RMB "+bean.getPrice());
        vHolder.search_item_accountPay.setText("销量："+bean.getAccountPay());

        return convertView;

    }

    /* 优化*/
    class ViewHolder {
        private TextView search_item_itemid;
        private SimpleDraweeView img_search;
        private TextView eah_item_name;
        private TextView eah_item_freight;
        private TextView eah_item_price;
        private TextView search_item_place;
        private TextView search_item_accountPay;
    }

//    @Override
//    public void onClick(View v) {
//        mCallback.click(v);
//    }


}
