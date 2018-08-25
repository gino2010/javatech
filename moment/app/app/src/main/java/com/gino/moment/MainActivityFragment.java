package com.gino.moment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gino.moment.adapter.GridAdapter;
import com.gino.moment.adapter.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.rv_image)
    RecyclerView recyclerView;

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

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        gridAdapter = new GridAdapter(getContext());

        List<SectionAdapter.Section> sections = new ArrayList<>();

        //Sections
        sections.add(new SectionAdapter.Section(0, "Section 1"));
        sections.add(new SectionAdapter.Section(5, "Section 2"));
        sections.add(new SectionAdapter.Section(12, "Section 3"));
        sections.add(new SectionAdapter.Section(14, "Section 4"));
        sections.add(new SectionAdapter.Section(20, "Section 5"));

        //Add your adapter to the sectionAdapter
        SectionAdapter.Section[] dummy = new SectionAdapter.Section[sections.size()];
        SectionAdapter mSectionedAdapter = new
                SectionAdapter(getContext(), R.layout.grid_section, R.id.section_text, recyclerView, gridAdapter, getFragmentManager());
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(mSectionedAdapter);
        new AsyncHttpTask().execute();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            // TODO: 2018/8/24 请求图片序号
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
        }
    }
}
