package com.gino.moment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gino.moment.adapter.SliderAdapter;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class SliderFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_slider, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        ArrayList<String> strings = new ArrayList<>();
        strings.add("http://i.imgur.com/DvpvklR.png");
        strings.add("http://i.imgur.com/DvpvklR.png");
        strings.add("http://i.imgur.com/DvpvklR.png");
        strings.add("http://i.imgur.com/DvpvklR.png");
        viewPager.setAdapter(new SliderAdapter(strings, getContext()));
        circleIndicator.setViewPager(viewPager);
    }

}
