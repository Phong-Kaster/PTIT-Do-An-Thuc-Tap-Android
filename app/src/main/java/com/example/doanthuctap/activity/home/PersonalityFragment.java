package com.example.doanthuctap.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.model.Setting;
import com.example.doanthuctap.model.User;
import com.example.doanthuctap.recyclerviewadapter.SettingsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.home.PersonalityFragmentViewModel;
import com.example.doanthuctap.viewModel.personality.OrderInformationViewModel;

import java.util.ArrayList;
import java.util.List;


public class PersonalityFragment extends Fragment {

    private List<Setting> settings = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingsRecyclerViewAdapter adapter;

    private TextView username;
    private TextView buttonChangeInformation;

    /*Auth user*/
    private User authUser;
    private PersonalityFragmentViewModel viewModel;
    String token;

    /* button send request to find orders with status*/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personality, container, false);

        assert this.getArguments() != null;
        token = this.getArguments().getString("accessToken");

        setupComponent(view);
        setupViewModel();
        setupSettings();
        setupRecyclerView();
        //setupScreen();
        setupEvent();
        return view;
    }

    private void setupComponent(View view)
    {
        recyclerView = view.findViewById(R.id.personalitySettings);
        username = view.findViewById(R.id.personalityName);
        buttonChangeInformation = view.findViewById(R.id.personalityButtonChangeInformation);

        GlobalVariable globalVariable = (GlobalVariable) requireActivity().getApplication();
        authUser = globalVariable.getAuthUser();
    }


    @SuppressLint("SetTextI18n")
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(PersonalityFragmentViewModel.class);
        viewModel.getProfile(token);

        viewModel.getResponse().observe(requireActivity(), response -> {
            int result = response.getResult();
            if( result == 1)
            {
                String firstName = response.getData().getFirstName();
                String lastName = response.getData().getLastName();
                username.setText( firstName + " " + lastName );
            }
        });
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

        if( authUser.getRole().equals("admin") )
        {
            Setting setting5 = new Setting("adminOrders", getString(R.string.all_orders_admin), R.drawable.ic_all_orders_admin);
            Setting setting6 = new Setting("adminProducts", getString(R.string.all_products_admin), R.drawable.ic_all_products_admin);

            settings.add(setting5);
            settings.add(setting6);
        }

    }

    private void setupRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);

        adapter = new SettingsRecyclerViewAdapter(requireActivity(), settings);
        recyclerView.setAdapter(adapter);
    }


    @SuppressLint("SetTextI18n")
    private void setupScreen()
    {
        username.setText( authUser.getFirstName() + " " + authUser.getLastName() );
    }


    private void setupEvent()
    {
        buttonChangeInformation.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), ChangeInformationActivity.class);
//            startActivity(intent);
        });

        /* button send request to find orders with status*/
//        buttonStatusProcessing.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), OrdersActivity.class);
//            intent.putExtra("orderStatus", "processing");
//            startActivity(intent);
//        });
//
//        buttonStatusPacked.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), OrdersActivity.class);
//            intent.putExtra("orderStatus", "packed");
//            startActivity(intent);
//        });
//
//        buttonStatusBeingTransported.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), OrdersActivity.class);
//            intent.putExtra("orderStatus", "being transported");
//            startActivity(intent);
//        });
//
//        buttonStatusDelivered.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), OrdersActivity.class);
//            intent.putExtra("orderStatus", "delivered");
//            startActivity(intent);
//        });
//
//        buttonStatusCancel.setOnClickListener(view->{
//            Intent intent = new Intent(requireContext(), OrdersActivity.class);
//            intent.putExtra("orderStatus", "cancel");
//            startActivity(intent);
//        });
    }
}