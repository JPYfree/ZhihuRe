package com.example.admin.zhihu_re.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.zhihu_re.R;
import com.example.admin.zhihu_re.entity.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class NewsAdapter extends ArrayAdapter<News>{

    private int resourceId;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.no_image)
            .showImageOnFail(R.drawable.no_image)
            .showImageForEmptyUri(R.drawable.no_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();


    public NewsAdapter(Context context, int resourceId) {
        super(context, resourceId);
        this.resourceId = resourceId;
    }

    public NewsAdapter(Context context, int resourceId, List<News> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.newsImage = (ImageView) convertView.findViewById(R.id.news_image);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newsTitle.setText(news.getTitle());

        imageLoader.displayImage(news.getImage(), viewHolder.newsImage, options);
        return convertView;
    }


    class ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
    }

    public void refreshNewsList(List<News> newsList) {
        clear();
        addAll(newsList);
        notifyDataSetChanged();
    }
}
