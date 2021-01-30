package com.vivekojha.weatherapp.nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vivekojha.weatherapp.R;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    TextView username;
    EditText DefaultCity_Et;
    TextView defaultCITY_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        username = view.findViewById(R.id.username);
        //DefaultCity_Et= view.findViewById(R.id.et_defaultCity);
        defaultCITY_txt = view.findViewById(R.id.textView_Default_city);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent data = getActivity().getIntent();
        final String userNameData = data.getStringExtra("userName");
        username.setText(userNameData);

    }
}