package com.example.admin.zhihu_re.task;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.example.admin.zhihu_re.entity.NewsDetail;
import com.example.admin.zhihu_re.http.HttpUtil;
import com.example.admin.zhihu_re.http.JsonHelper;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/6/6.
 */
public class LoadNewsDetailTask extends AsyncTask<Integer, Void, NewsDetail> {

    private WebView webView;

    public LoadNewsDetailTask(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        NewsDetail newsDetail = null;
        try {
            newsDetail = JsonHelper.parseJsonToDetail(HttpUtil.getNewsDetail(params[0]));
        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return newsDetail;

    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        String headerImage;
        String body;

        if (newsDetail.getImage() == null || newsDetail.getImage().equals("")) {
            headerImage = "file:///android_asset/news_detail_header_image.jpg";
        }else {
            headerImage = newsDetail.getImage();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(headerImage).append("\" alt>")
                .append("<div class=\"img-mask\"></div>");
        body = newsDetail.getBody().replace("<div class=\"img-place-holder\">", builder.toString());
        String newsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                +"<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\">"
                + body;
        webView.loadDataWithBaseURL("file:///android_asset/", newsContent, "text/html", "UTF-8", null);
    }
}
