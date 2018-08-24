package com.gino.moment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gino.moment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> images;

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, ((GridView) parent).getColumnWidth() / 3 * 4));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position).toString();
        Picasso.get().setLoggingEnabled(true);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.loader)
                .resize(((GridView) parent).getColumnWidth(), ((GridView) parent).getColumnWidth() / 3 * 4)
                .into(imageView);

//        imageView.setImageBitmap();
        return imageView;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }
}
