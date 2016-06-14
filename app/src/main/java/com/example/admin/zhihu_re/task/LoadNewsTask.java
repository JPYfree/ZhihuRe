package com.example.admin.zhihu_re.task;

import android.os.AsyncTask;

import com.example.admin.zhihu_re.adapter.NewsAdapter;
import com.example.admin.zhihu_re.entity.News;
import com.example.admin.zhihu_re.http.HttpUtil;
import com.example.admin.zhihu_re.http.JsonHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class LoadNewsTask extends AsyncTask<Void, Void, List<News>> {

    private NewsAdapter adapter;
    private onFinishListener listener;

    public LoadNewsTask(NewsAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    public LoadNewsTask(NewsAdapter adapter, onFinishListener listener) {
        this(adapter);
        this.listener = listener;

    }

    @Override
    protected List<News> doInBackground(Void... params) {
        //获取NewsList信息
        List<News> newsList = null;
        try {
            newsList = JsonHelper.parseJsonToList(HttpUtil.getLatestNewsList());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter.refreshNewsList(newsList);
        if (listener != null) {
            listener.afterTaskFinish();
        }
    }


    public interface onFinishListener {
        void afterTaskFinish();
    }
}
