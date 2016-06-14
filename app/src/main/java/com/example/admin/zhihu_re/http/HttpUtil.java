package com.example.admin.zhihu_re.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HttpUtil {

    public static final String NEWSLIST_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";

    public static String getResponse(String address) throws IOException{
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                return response.toString();
            }else {
                throw new IOException("Network Error - response code: " + connection.getResponseCode());
            }
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String getLatestNewsList() throws IOException {
        return getResponse(NEWSLIST_LATEST);
    }

    public static String getNewsDetail(int id) throws IOException {
        return getResponse(NEWSDETAIL + id);
    }
}
