package com.example.doanthuctap.activity.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanthuctap.R;
import com.example.doanthuctap.model.Setting;
import com.example.doanthuctap.recyclerviewadapter.SettingsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class PersonalityFragment extends Fragment {

    private List<Setting> settings = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingsRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personality, container, false);

        setupComponent(view);
        setupSettings();
        setupRecyclerView();

        return view;
    }

    private void setupComponent(View view)
    {
        recyclerView = view.findViewById(R.id.personalitySettings);
    }

    private void setupSettings()
    {
        Setting setting1 = new Setting("orders", getString(R.string.all_orders),R.drawable.ic_all_orders );
        Setting setting2 = new Setting("darkMode", getString(R.string.dark_mode),R.drawable.ic_dark_mode );
        Setting setting3 = new Setting("profile", getString(R.string.personal_information),R.drawable.ic_profile );
        Setting setting4 = new Setting("language", getString(R.string.language),R.drawable.ic_language );

        settings.add(setting1);
        settings.add(setting2);
        settings.add(setting3);
        settings.add(setting4);
    }

    private void setupRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);

        SettingsRecyclerViewAdapter adapter =
                new SettingsRecyclerViewAdapter(requireActivity(), settings);
        recyclerView.setAdapter(adapter);

    }
}