package com.example.admin.zhihu_re.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.admin.zhihu_re.R;
import com.example.admin.zhihu_re.db.DailyNewsDB;
import com.example.admin.zhihu_re.entity.News;
import com.example.admin.zhihu_re.task.LoadNewsDetailTask;
import com.example.admin.zhihu_re.utility.Utility;

/**
 * Created by Administrator on 2016/6/8.
 */
public class NewsDetailActivity extends Activity {
    private WebView webView;
    private boolean isFavourite;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detial);

        webView = (WebView) findViewById(R.id.web_view);
        setWebView(webView);

        news = (News) getIntent().getSerializableExtra("news");
        initToolbar();
        new LoadNewsDetailTask(webView).execute(news.getId());

    }

    private void setWebView(WebView webView) {
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }

    public static void startActivity(Context context, News news) {
        if (Utility.isNetworkConnected(context)) {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("news", news);
            context.startActivity(intent);
        }else {
            Utility.noNetworkAlert(context);
        }
    }

    public void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(R.string.app_name);
//        toolbar.setTitleTextColor(Color.parseColor("#ecf0f1"));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isFavourite = DailyNewsDB.getInstance(this).isFavourite(news);
        if (isFavourite) {
//            MenuItem item = (MenuItem) findViewById(R.id.action_favourite);
            Menu menu = toolbar.getMenu();
            menu.findItem(R.id.action_favourite).setIcon(R.drawable.fav_active);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_settings:
                        Toast.makeText(NewsDetailActivity.this, R.string.action_settings,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favourite:
                        if (!isFavourite) {
                            //收藏列表添加
                            DailyNewsDB.getInstance(NewsDetailActivity.this).saveFavourite(news);
                            item.setIcon(R.drawable.fav_active);
                            isFavourite = true;
                        }else {
                            //收藏列表删减
                            DailyNewsDB.getInstance(NewsDetailActivity.this).deleteFavourite(news);
                            item.setIcon(R.drawable.fav_normal);
                            isFavourite = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

}
