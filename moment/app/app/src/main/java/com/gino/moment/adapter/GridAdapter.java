package com.gino.moment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.gino.moment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private final Context mContext;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;

        public SimpleViewHolder(View view) {
            super(view);
            imageView = (ImageView) view;
        }
    }

    public GridAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            addItem(i);
        }
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
                .load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.drawable.loader)
                .into(holder.imageView);
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

