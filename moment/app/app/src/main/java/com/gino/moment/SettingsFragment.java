package com.gino.moment;

import android.annotation.SuppressLint;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

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

        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setTitle(R.string.action_settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences(
                getString(R.string.shared), Context.MODE_PRIVATE);

        String server = sharedPref.getString(SERVER, null);
        if (server != null) {
            et_server.setText(server);
        }
    }

    @SuppressLint("ShowToast")
    @OnClick(R.id.bt_save)
    public void save() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(et_server.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        sharedPref.edit().putString(SERVER, et_server.getText().toString()).apply();
        Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
    }
}
