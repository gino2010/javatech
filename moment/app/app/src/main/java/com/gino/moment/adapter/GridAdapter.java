package com.gino.moment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.gino.moment.R;
import com.gino.moment.service.MomentService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SimpleViewHolder> {
//    private static final int COUNT = 100;

    private final Context mContext;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;
    private MomentService momentService;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;

        public SimpleViewHolder(View view) {
            super(view);
            imageView = (ImageView) view;
        }
    }

    public GridAdapter(Context context, List<Integer> items) {
        mContext = context;
        mItems = items;
        momentService = new MomentService(mContext);
//        mItems = new ArrayList<>(COUNT);
//        for (int i = 0; i < COUNT; i++) {
//            addItem(i);
//        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, parent.getWidth() / 9 * 4));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setPadding(2, 2, 2, 2);
        return new SimpleViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        Picasso.get()
                .load(momentService.getImageAddress(mItems.get(position)))
                .placeholder(R.drawable.loader)
                .into(holder.imageView);
//        holder.imageView.setImageBitmap(momentService.getImageById(mItems.get(position)));
    }

    public void addItem(int position, int imageId) {
        mItems.add(position, imageId);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshItem(List<Integer> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}

