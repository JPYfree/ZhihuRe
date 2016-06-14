package com.example.admin.zhihu_re.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.zhihu_re.R;
import com.example.admin.zhihu_re.adapter.NewsAdapter;
import com.example.admin.zhihu_re.db.DailyNewsDB;
import com.example.admin.zhihu_re.entity.News;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FavouriteActivity extends Activity {

    private NewsAdapter adapter;
    private ListView listViewFav;
    private List<News> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);

        initToolbar();

        listViewFav = (ListView) findViewById(R.id.list_view_fav);
        favList = DailyNewsDB.getInstance(this).loadFavourite();
        adapter = new NewsAdapter(this, R.layout.listview_item, favList);
        listViewFav.setAdapter(adapter);
        listViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsDetailActivity.startActivity(FavouriteActivity.this, adapter.getItem(position));
            }
        });

    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(R.string.favourite);
//        toolbar.setTitleTextColor(Color.parseColor("#ecf0f1"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
