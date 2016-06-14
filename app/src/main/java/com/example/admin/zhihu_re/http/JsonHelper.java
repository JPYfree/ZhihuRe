package com.example.admin.zhihu_re.http;

import com.example.admin.zhihu_re.entity.News;
import com.example.admin.zhihu_re.entity.NewsDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class JsonHelper {

    public static List<News> parseJsonToList(String json) throws JSONException {
        JSONObject newsObject = new JSONObject(json);
        JSONArray newsArray = newsObject.getJSONArray("stories");
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < newsArray.length(); i ++) {
            JSONObject newsContent = newsArray.getJSONObject(i);
            int id = newsContent.optInt("id");
            String title = newsContent.optString("title");
            String image = "";
            if (newsContent.has("images")) {
                image = (String) newsContent.getJSONArray("images").get(0);
            }
            News news = new News(id, title, image);
            newsList.add(news);
        }
        return newsList;
    }

    public static NewsDetail parseJsonToDetail(String json) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(json, NewsDetail.class);
    }
}
