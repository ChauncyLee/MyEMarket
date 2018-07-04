package com.chuncy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuncy.R;
import com.chuncy.adapter.SearchAdapter;
import com.chuncy.bean.ProductBean;
import com.chuncy.buy.MainActivity;
import com.chuncy.option.WishlistActivity;
import com.chuncy.product.ItemDetailsActivity;
import com.chuncy.util.AnalyseJson;
import com.chuncy.util.DBUtils;
import com.chuncy.util.MyApplication;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chauncy on 2018/7/4.
 */
public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static MainActivity mActivity;
    private List<ProductBean> beanlist;
    private Context context;
    private SimpleStringRecyclerViewAdapter mAdapter;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        Fresco.initialize(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        String[] items=null;
        beanlist=new ArrayList<ProductBean>();
/**
 * 获取json数据
 */
        RequestParams params = new RequestParams(MyApplication.absURL1 + "潮流" + MyApplication.getAbsURL2);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                /**
                 * analyseJson是自己写的解析json工具类
                 */
                beanlist= AnalyseJson.analysejson(result);

                //设置RecyclerView管理器
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                //初始化适配器
                mAdapter = new SimpleStringRecyclerViewAdapter(rv,beanlist);
                //设置添加或删除item时的动画，这里使用默认动画
                rv.setItemAnimator(new DefaultItemAnimator());
                //设置适配器
                rv.setAdapter(mAdapter);

                ArrayList<String> wishlistImageUri =new ArrayList<String>();//c需要修改


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", "error" );
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, beanlist));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private  List<ProductBean> mlist;
        private RecyclerView mRecyclerView;
        public static HashMap<String,String> data=new HashMap<String,String>();



        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView index_item_shipping;
            public final TextView index_item_title;
            public final TextView index_item_price;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                index_item_shipping=(TextView)view.findViewById(R.id.index_item_shipping);
                index_item_title=(TextView)view.findViewById(R.id.index_item_title);
                index_item_price=(TextView)view.findViewById(R.id.index_item_price);

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, List<ProductBean> list) {
            mlist = list;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.index_item_price.setText("RMB "+mlist.get(position).getPrice());
            holder.index_item_shipping.setText("快递 "+mlist.get(position).getShipping());
            holder.index_item_title.setText(mlist.get(position).getTitle());
            holder.mImageView.setImageURI(mlist.get(position).getPicURL());

            data.put("item_id",mlist.get(position).getItem_id());
            data.put("title",mlist.get(position).getTitle());
            data.put("price",mlist.get(position).getPrice());
            data.put("place",mlist.get(position).getPlace());
            data.put("accountPay",mlist.get(position).getAccountPay());
            data.put("picURL",mlist.get(position).getPicURL());
            data.put("shipping",mlist.get(position).getShipping());

            //查看详细信息
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item_id=mlist.get(position).getItem_id();
                    String title=mlist.get(position).getTitle();
                    String price =mlist.get(position).getPrice();
                    String place=mlist.get(position).getPlace();
                    String accountPay=mlist.get(position).getAccountPay();
                    String picURL=mlist.get(position).getPicURL();
                    String shipping=mlist.get(position).getShipping();
                    Intent intent=new Intent(mActivity,ItemDetailsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("item_id",item_id);
                    bundle.putString("title",title);
                    bundle.putString("price",price);
                    bundle.putString("place",place);
                    bundle.putString("accountPay",accountPay);
                    bundle.putString("picURL",picURL);
                    bundle.putString("shipping",shipping);
                    intent.putExtra("item",bundle);
                    mActivity.startActivity(intent);

                }
            });

           /* //收藏或取消收藏
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBUtils dbutils=new DBUtils(mActivity);
                    String s=mlist.get(position).getItem_id();
                    mlist= dbutils.select(s);

                    if(mlist.size()==0) {
                        dbutils.saveToDb(data);
                        holder.mImageViewWishlist.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favorite_black_18dp));
                        Toast.makeText(mActivity, "收藏成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dbutils.deleteItem(mlist.get(0).getId());
                        holder.mImageViewWishlist.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favorite_border_black_18dp));
                        Toast.makeText(mActivity,"取消收藏",Toast.LENGTH_SHORT).show();
                    }

                }
            });*/

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }
}
