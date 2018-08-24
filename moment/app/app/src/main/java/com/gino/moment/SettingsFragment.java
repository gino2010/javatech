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
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {

    @BindView(R.id.et_server)
    EditText et_server;

    @BindView(R.id.bt_save)
    Button bt_save;

    Toolbar toolbar;

    private SharedPreferences sharedPref;

    public static final String SERVER = "server";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                getFragmentManager().popBackStack();
            }
        });

        sharedPref = getContext().getSharedPreferences(
                getString(R.string.shared), Context.MODE_PRIVATE);

        String server = sharedPref.getString(SERVER, null);
        if (server != null) {
            et_server.setText(server);
        }
    }

    @OnClick(R.id.bt_save)
    public void save() {
        sharedPref.edit().putString(SERVER, et_server.getText().toString()).apply();
    }
}
