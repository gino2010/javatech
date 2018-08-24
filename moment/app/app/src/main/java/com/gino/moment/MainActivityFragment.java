package com.gino.moment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gino.moment.adapter.GridAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.image_grid)
    GridView gridView;

    private GridAdapter gridAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        gridAdapter = new GridAdapter(getContext());
        gridView.setAdapter(gridAdapter);
        new AsyncHttpTask().execute();
    }

    @OnItemClick(R.id.image_grid)
    public void onItemClick(int position) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new SliderFragment())
                .addToBackStack(null)
                .commit();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            // TODO: 2018/8/24 请求图片序号
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("http://i.imgur.com/DvpvklR.png");
            strings.add("http://i.imgur.com/DvpvklR.png");
            strings.add("http://i.imgur.com/DvpvklR.png");
            strings.add("http://i.imgur.com/DvpvklR.png");
            gridAdapter.setImages(strings);
        }
    }
}
