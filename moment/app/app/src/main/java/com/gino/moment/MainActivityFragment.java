package com.gino.moment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gino.moment.adapter.GridAdapter;
import com.gino.moment.adapter.SectionAdapter;
import com.gino.moment.model.GridData;
import com.gino.moment.service.MomentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.rv_image)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private GridAdapter gridAdapter;
    private SectionAdapter mSectionedAdapter;

    private MomentService momentService;

    private List<SectionAdapter.Section> sections;
    private List<Integer> imageIds;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        momentService = new MomentService(Objects.requireNonNull(getContext()));

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // initial empty data
        imageIds = new ArrayList<>();
        gridAdapter = new GridAdapter(getContext(), imageIds);
        sections = new ArrayList<>();
        //Add your adapter to the sectionAdapter
        SectionAdapter.Section[] dummy = new SectionAdapter.Section[sections.size()];
        mSectionedAdapter = new
                SectionAdapter(getContext(), R.layout.grid_section, R.id.section_text, recyclerView, gridAdapter, getFragmentManager());
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(mSectionedAdapter);
        new AsyncHttpTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncHttpTask extends AsyncTask<Void, Void, GridData> {
        @Override
        protected GridData doInBackground(Void... voids) {
            return momentService.getUserImages();
        }

        @Override
        protected void onPostExecute(GridData gridData) {
            sections = gridData.getSections();
            imageIds = gridData.getImageIds();
            gridAdapter.refreshItem(imageIds);
            SectionAdapter.Section[] sectionArray = new SectionAdapter.Section[sections.size()];
            mSectionedAdapter.setSections(sections.toArray(sectionArray));
            progressBar.setVisibility(View.GONE);
            if (gridData.getMessage() != null) {
                Toast.makeText(getContext(), gridData.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
