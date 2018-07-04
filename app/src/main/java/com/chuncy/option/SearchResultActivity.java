package com.chuncy.option;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chuncy.R;
import com.chuncy.adapter.SearchAdapter;
import com.chuncy.bean.ProductBean;
import com.chuncy.bean.SearchItem;
import com.chuncy.product.ItemDetailsActivity;
import com.chuncy.util.AnalyseJson;
import com.chuncy.util.MyApplication;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView mListView;
    private List<SearchItem> mlist=new ArrayList<>();
    private SearchAdapter adapter;
    private String query;
    private TextView text_result;
    public static List<ProductBean>  beanlist=new ArrayList<ProductBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_search_result);
        text_result=(TextView)findViewById(R.id.text_result);
        mListView = (ListView) findViewById(R.id.searc_list);
        handleIntent(getIntent());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.getItem(0);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);
        searchItem.expandActionView();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // 取得欲搜索的字符串
            query = intent.getStringExtra(SearchManager.QUERY);
            text_result.setText(" ");
            query.trim();
            /**
             * 获取json数据
             */
            RequestParams params = new RequestParams(MyApplication.absURL1 + query + MyApplication.getAbsURL2);

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

                /**
                 * analyseJson是自己写的解析json工具类
                 */
                    beanlist= AnalyseJson.analysejson(result);
                    SearchAdapter adapter=new SearchAdapter(SearchResultActivity.this,beanlist);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(SearchResultActivity.this);

                    Log.i("itemid first", beanlist.get(0).getItem_id());
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


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        String item_id=beanlist.get(i).getItem_id();
        String title=beanlist.get(i).getTitle();
        String price =beanlist.get(i).getPrice();
        String place=beanlist.get(i).getPlace();
        String accountPay=beanlist.get(i).getAccountPay();
        String picURL=beanlist.get(i).getPicURL();
        String shipping=beanlist.get(i).getShipping();
        Intent intent=new Intent(SearchResultActivity.this,ItemDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("item_id",item_id);
        bundle.putString("title",title);
        bundle.putString("price",price);
        bundle.putString("place",place);
        bundle.putString("accountPay",accountPay);
        bundle.putString("picURL",picURL);
        bundle.putString("shipping",shipping);
        intent.putExtra("item",bundle);
        startActivity(intent);
    }
//    @Override
//    public void click(View v) {
//        Log.v("test","test");
//        Toast.makeText(
//                 SearchResultActivity.this,
//                 "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
//                         + beanlist.get((Integer) v.getTag()),
//                 Toast.LENGTH_SHORT).show();
//    }


}
