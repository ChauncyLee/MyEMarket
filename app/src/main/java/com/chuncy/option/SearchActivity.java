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
import android.widget.TextView;

import com.chuncy.R;


public class SearchActivity extends AppCompatActivity {


    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView=(TextView)findViewById(R.id.text_view);
        Intent intent=getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // 取得欲搜索的字符串
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            // 设置后，点击键盘会弹出搜索框
            setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        }
        String searchContent = intent.getStringExtra(SearchManager.QUERY);
        textView.setText(searchContent);
    }
}
