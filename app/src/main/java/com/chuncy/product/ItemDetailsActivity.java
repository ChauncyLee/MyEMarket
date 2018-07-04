package com.chuncy.product;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chuncy.adapter.SearchAdapter;
import com.chuncy.bean.DetailsBean;
import com.chuncy.bean.ProductBean;
import com.chuncy.util.AnalyseJson;
import com.chuncy.util.DBUtils;
import com.chuncy.util.MyApplication;
import com.squareup.picasso.Picasso;
//import com.allandroidprojects.ecomsample.fragments.ImageListFragment;
//import com.allandroidprojects.ecomsample.fragments.ViewPagerActivity;
//import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.chuncy.adapter.ViewPagerPicAdapter;
import com.chuncy.buy.MainActivity;
import com.chuncy.R;
import com.chuncy.notification.NotificationCountSetClass;
import com.chuncy.option.CartListActivity;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    private ViewPager mViewPage;
    private ArrayList<String> mListUrl;
    public static HashMap<String,String> data=new HashMap<String,String>();
    private TextView details_title,details_price,details_shipping,details_subtitle,details_place;
    private WebView webview;
    private DetailsBean bean;
    private ImageView details_wish_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        details_title=(TextView)findViewById(R.id.details_title);
        details_price=(TextView)findViewById(R.id.details_price);
        details_shipping=(TextView)findViewById(R.id.details_shipping);
        details_subtitle=(TextView)findViewById(R.id.details_subtitle);
        details_place=(TextView)findViewById(R.id.details_place);
        details_wish_icon=(ImageView)findViewById(R.id.details_wish_icon);

        /**
         * 获取传过来的值
         */
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("item");

        final String item_id=bundle.getString("item_id");
        final String title=bundle.getString("title");
        final String price =bundle.getString("price");
        final String place=bundle.getString("place");
        final String accountPay=bundle.getString("accountPay");
        final String picURL=bundle.getString("picURL");
        final String shipping=bundle.getString("shipping");
        data.put("item_id",item_id);
        data.put("title",title);
        data.put("price",price);
        data.put("place",place);
        data.put("accountPay",accountPay);
        data.put("picURL",picURL);
        data.put("shipping",shipping);
        details_title.setText(title);
        details_shipping.setText("快递 "+shipping);
        details_price.setText("RMB "+price);
        details_place.setText("\u2022 "+place);
        List<ProductBean> tlist=new ArrayList<ProductBean>();
        DBUtils dbutil=new DBUtils(ItemDetailsActivity.this);
        tlist= dbutil.select(item_id);
        if(tlist.size()!=0){
            details_wish_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_18dp));
        }else{
            details_wish_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_18dp));
        }


        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                //imageUrlUtils.addCartListImageUri(stringImageUri);
                Toast.makeText(ItemDetailsActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
               // imageUrlUtils.addCartListImageUri(stringImageUri);
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));

            }
        });

        /**
         * 点击收藏
         */
        View home = findViewById(R.id.details_wish);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<ProductBean>mlist=new ArrayList<ProductBean>();
                DBUtils dbutils=new DBUtils(ItemDetailsActivity.this);
                mlist= dbutils.select(item_id);

                if(mlist.size()==0) {
                    dbutils.saveToDb(data);
                    details_wish_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_18dp));
                    Toast.makeText(ItemDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbutils.deleteItem(mlist.get(0).getId());
                    details_wish_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_18dp));
                    Toast.makeText(ItemDetailsActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                }
            }
        });
        getDetails(item_id);

    }

    /**
     * 通过http请求获取商品详细信息
     * @param item_id
     */
    void getDetails(String item_id){
/**
 * 获取json数据
 */
        RequestParams params = new RequestParams(MyApplication.detailURL1 + item_id + MyApplication.detailURL2);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                /**
                 * analyseJson是自己写的解析json工具类
                 */
                bean= AnalyseJson.analyseDetailsJson(result);
                details_subtitle.setText("•"+bean.getSubtitle());
                setmViewPage(bean.getImages());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", "error1" );
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });



    }

    void setmViewPage(List<String> Urls){
//        List<String> mlist=new ArrayList<String>();
//        mlist.add("http://img.alicdn.com/imgextra/i4/890482188/TB2m263okKWBuNjy1zjXXcOypXa_!!890482188.jpg");
//        mlist.add("http://img.alicdn.com/imgextra/i2/890482188/TB2rV.LeljTBKNjSZFwXXcG4XXa_!!890482188.jpg");
//        mlist.add("http://img.alicdn.com/imgextra/i3/890482188/TB2NQL.oeuSBuNjSsplXXbe8pXa_!!890482188.jpg");
        mViewPage = (ViewPager) findViewById(R.id.main_viewpage);
        ArrayList<SimpleDraweeView> list = new ArrayList<SimpleDraweeView>();
        //获取屏幕的高度,取其三分之一
        int height = getWindowManager().getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams layoutParams = mViewPage.getLayoutParams();
        int viewPageHeight = height/3;
        //设置ViewPager的高度为整个屏幕的3分之一
        layoutParams.height=viewPageHeight;

        for(int i=0;i<Urls.size();i++){
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(this);
            simpleDraweeView.setImageURI(Uri.parse(Urls.get(i)));
            simpleDraweeView.setAspectRatio(1.5f);//设置宽高比
            simpleDraweeView.setMaxHeight(viewPageHeight);
            simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            list.add(simpleDraweeView);
        }

        ViewPagerPicAdapter adapter = new ViewPagerPicAdapter(list,this);
        mViewPage.setAdapter(adapter);
    }

}
