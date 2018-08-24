package com.gino.moment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.gino.moment.adapter.UserAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends Fragment {

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.bt_save)
    Button bt_save;

    Toolbar toolbar;
    UserAdapter userAdapter;

    private SharedPreferences sharedPref;

    public static final String USER = "user";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] users = {"all", "aaa", "bbb"};
        userAdapter = new UserAdapter(getContext(), android.R.layout.simple_spinner_item, users);
        spinner.setAdapter(userAdapter);
        sharedPref = getContext().getSharedPreferences(
                getString(R.string.shared), Context.MODE_PRIVATE);
        spinner.setSelection(sharedPref.getInt(USER, 0));

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                getFragmentManager().popBackStack();
            }
        });
    }

    @OnClick(R.id.bt_save)
    public void save() {
        sharedPref.edit().putInt(USER, spinner.getSelectedItemPosition()).apply();
    }
}
