package com.gino.moment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] users;

    public UserAdapter(@NonNull Context context, int resource, String[] users) {
        super(context, resource, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return users[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(users[position]);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(users[position]);
        return label;
    }
}
