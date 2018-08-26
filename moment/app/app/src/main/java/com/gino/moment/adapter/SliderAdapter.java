package com.gino.moment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gino.moment.R;
import com.gino.moment.network.Base64HttpClient;
import com.gino.moment.service.MomentService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<Integer> imageIds;
    private Context context;
    private MomentService momentService;

    public SliderAdapter(List<Integer> imageList, Context context) {
        this.imageIds = imageList;
        this.context = context;
        momentService = new MomentService(context);
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView;
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        (new Picasso.Builder(context).downloader(Base64HttpClient.build().getOkHttp3Downloader())).build()
                .load(momentService.getImageAddress(imageIds.get(position)))
                .placeholder(R.drawable.loader)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
