package com.chuncy.option;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuncy.bean.CartItem;
import com.chuncy.bean.ProductBean;
import com.chuncy.product.ItemDetailsActivity;
import com.chuncy.buy.MainActivity;
//import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.chuncy.R;
import com.chuncy.util.DBUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

//import static com.allandroidprojects.ecomsample.fragments.ImageListFragment.STRING_IMAGE_POSITION;
//import static com.allandroidprojects.ecomsample.fragments.ImageListFragment.STRING_IMAGE_URI;

public class CartListActivity extends AppCompatActivity {
    private static Context mContext;
    private RecyclerView recyclerview;
    MyCartRecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Fresco.initialize(getApplicationContext());

        List<CartItem> list=new ArrayList<CartItem>();
        DBUtils dbutil=new DBUtils(CartListActivity.this);
        list= dbutil.selectAllCartItem();
        if(list.size()==0){
            setContentView(R.layout.activity_cart_list);
        }else{
            setContentView(R.layout.layout_recylerview_list);
        }
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        mContext = CartListActivity.this;
        //设置RecyclerView管理器
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new MyCartRecyclerViewAdapter(recyclerview,list);
        //设置添加或删除item时的动画，这里使用默认动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recyclerview.setAdapter(mAdapter);

        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);


    }

    public class MyCartRecyclerViewAdapter extends RecyclerView.Adapter<MyCartRecyclerViewAdapter.ViewHolder> {
        private List<CartItem> list;
        private RecyclerView mRecyclerView;


        class ViewHolder extends RecyclerView.ViewHolder {
            public final SimpleDraweeView image_wish;
            public final TextView wish_item_name;
            public final TextView wish_item_price;
            public final TextView wish_item_freight;
            public final TextView wish_item_accountPay;
            public final TextView wish_item_place;
            public final ImageView mImageViewWishlist;
            public final LinearLayout mLayoutItem;
            public ViewHolder(View itemView) {
                super(itemView);
                image_wish=(SimpleDraweeView)itemView.findViewById(R.id.image_list);
                wish_item_name=(TextView)itemView.findViewById(R.id.wish_item_name);
                wish_item_price=(TextView)itemView.findViewById(R.id.wish_item_price);
                wish_item_freight=(TextView)itemView.findViewById(R.id.wish_item_freight);
                wish_item_accountPay=(TextView)itemView.findViewById(R.id.wish_item_accountPay);
                wish_item_place=(TextView)itemView.findViewById(R.id.wish_item_place);
                mImageViewWishlist = (ImageView) itemView.findViewById(R.id.ic_wishlist);
                mLayoutItem = (LinearLayout) itemView.findViewById(R.id.linear_wishitem);

            }
        }

        public MyCartRecyclerViewAdapter(RecyclerView recyclerView,List<CartItem> list) {
            this.mRecyclerView=recyclerView;
            this.list = list;
        }

        @Override
        public CartListActivity.MyCartRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
            return new CartListActivity.MyCartRecyclerViewAdapter.ViewHolder(view);

        }



        @Override
        public void onBindViewHolder(MyCartRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.image_wish.setImageURI(list.get(position).getPicURL());
            holder.wish_item_name.setText(list.get(position).getTitle());
            holder.wish_item_price.setText(list.get(position).getPrice());
            holder.wish_item_accountPay.setText(list.get(position).getAccountPay());
            holder.wish_item_freight.setText(list.get(position).getShipping());
            holder.wish_item_place.setText(list.get(position).getPlace());

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("点击", "listview的item被点击了！，点击的位置是-->" + list.get(position).getItem_id());
                    String item_id=list.get(position).getItem_id();
                    String title=list.get(position).getTitle();
                    String price =list.get(position).getPrice();
                    String place=list.get(position).getPlace();
                    String accountPay=list.get(position).getAccountPay();
                    String picURL=list.get(position).getPicURL();
                    String shipping=list.get(position).getShipping();
                    Intent intent=new Intent(mContext,ItemDetailsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("item_id",item_id);
                    bundle.putString("title",title);
                    bundle.putString("price",price);
                    bundle.putString("place",place);
                    bundle.putString("accountPay",accountPay);
                    bundle.putString("picURL",picURL);
                    bundle.putString("shipping",shipping);
                    intent.putExtra("item",bundle);
                    mContext.startActivity(intent);
                }
            });
            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<CartItem>mlist=new ArrayList<CartItem>();
                    DBUtils dbutils=new DBUtils(mContext);
                    mlist= dbutils.selectCartItem(list.get(position).getItem_id());
                    dbutils.deleteItem(mlist.get(0).getId());
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
