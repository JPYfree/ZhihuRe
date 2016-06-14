package com.example.admin.zhihu_re.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.zhihu_re.R;
import com.example.admin.zhihu_re.adapter.NewsAdapter;
import com.example.admin.zhihu_re.task.LoadNewsTask;
import com.example.admin.zhihu_re.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016/6/5.
 */
public class MainActivity extends Activity {

    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private NewsAdapter adapter;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnected = Utility.isNetworkConnected(this);
//        setTitle(getTime());
        initToolbar();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected) {
                    //刷新list
                    LoadNewsTask.onFinishListener listener = new LoadNewsTask.onFinishListener() {
                        @Override
                        public void afterTaskFinish() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    };
                    new LoadNewsTask(adapter, listener).execute();
                }else {
                    Utility.noNetworkAlert(MainActivity.this);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new NewsAdapter(this, R.layout.listview_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到News信息页面
                NewsDetailActivity.startActivity(MainActivity.this, adapter.getItem(position));
            }
        });

        if (isConnected) {
            //载入News列表
            new LoadNewsTask(adapter).execute();
        }else {
            Utility.noNetworkAlert(this);
        }
    }


    public void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "U clicked navigation button",
                        Toast.LENGTH_SHORT).show();
            }
        });

        textView = (TextView) findViewById(R.id.clock);
        textView.setText(getTime());

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, R.string.action_settings,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favourite:
                        //进入收藏页面
                        Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }

        });
    }


    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format), Locale.CHINA);
        return format.format(calendar.getTime());
    }
}
