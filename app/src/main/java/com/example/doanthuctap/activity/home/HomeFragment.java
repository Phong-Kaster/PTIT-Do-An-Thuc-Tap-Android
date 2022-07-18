package com.example.doanthuctap.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.search.SearchActivity;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.home.HomeFragmentViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private EditText searchView;
    private ImageSlider imageSlider;
    private HomeFragmentViewModel viewModel;
    private ProductsRecyclerViewAdapter adapter = null;
    private RecyclerView recyclerView;
    private LoadingScreen loadingScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);



        setupComponent(view);
        setupViewModel();
        setupScreen();
        setupEvent();
        return view;
    }

    /**
     * @author Phong-Kaster
     * mapping components with XML layout
     */
    private void setupComponent(View view){
        //searchView = view.findViewById(R.id.homeFragmentSearchView);
        searchView = view.findViewById(R.id.homeFragmentSearchView);
        imageSlider = view.findViewById(R.id.homeFragmentImageSlider);
        recyclerView = view.findViewById(R.id.homeFragmentRecyclerView);

        loadingScreen = new LoadingScreen(getActivity());
    }

    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        viewModel.instantiate();

        Map<String,String> parameters = new HashMap<>();
        viewModel.getProducts(parameters);

        /*listen data change to update recycler view*/
        viewModel.getObjects().observe((LifecycleOwner) requireContext(), productsResponse -> {
            int result = productsResponse.getResult();

            if( result == 1){
                List<ProductClient> list = productsResponse.getData();
                setupRecyclerView(list);
            }
            else{
                Toast.makeText(requireContext(), getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
            }
        });

        /*loading screen when data is being gotten*/
        viewModel.getAnimation().observe((LifecycleOwner) requireContext(), aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
    }

    /**
     * create default content to print on HomeFragment
     */
    private void setupScreen() {
        /*Step 1 - image slider*/
        ArrayList<SlideModel> photos = new ArrayList<>();
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/c8/e2/b1/77f4e5b61a4670f16d60d3fa376e7161.png",  null));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/76/23/23/6157c5789f7759c175d6e46f37349fe9.png",  null));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/20/e9/72/ec51bea3ce60b9237c2ed27f449c9cf2.png",  null));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/fa/88/0f/55629abdb23fef135d48a08700fec6c9.png",  null));

        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/fa/88/0f/55629abdb23fef135d48a08700fec6c9.png",  null));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/2d/14/fd/d76a8c08e736cea87e8c0bc1ea7abe4a.png",  null));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1080/ts/banner/04/3e/6c/4ee8340f89b79671dee62a3d54255477.png",  null));
        imageSlider.setImageList(photos, ScaleTypes.FIT);
    }


    /**
     * @author Phong-Kaster
     * handle event when users click on button
     */
    private void setupEvent(){
        /*******************SEARCH VIEW*******************/
        searchView.setOnClickListener(view->{
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            startActivity(intent);
            //requireActivity().finish();
        });

        /*******************IMAGE SLIDER*******************/
        imageSlider.setItemClickListener(i -> Toast.makeText(requireContext(), "Item " + i + " is selected", Toast.LENGTH_SHORT).show());

    }


    private void setupRecyclerView(List<ProductClient> objects)
    {
        adapter = new ProductsRecyclerViewAdapter(getActivity(), objects);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}