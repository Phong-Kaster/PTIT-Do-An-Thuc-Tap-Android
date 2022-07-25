package com.example.doanthuctap.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.search.SearchActivity;
import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.container.GetProductsWithCategoryIDResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Category;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.CategoriesRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.home.CategoryFragmentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CategoryFragment extends Fragment implements CategoriesRecyclerViewAdapter.callbacks
{

    private CategoryFragmentViewModel viewModel;
    private Map<String, String> parameters = new HashMap<>();


    private RecyclerView categoriesRecyclerView;
    private CategoriesRecyclerViewAdapter categoriesAdapter;
    private List<Category> categories = new ArrayList<>();

    private RecyclerView productsRecyclerView;
    private ProductsRecyclerViewAdapter productsAdapter;
    private List<ProductClient> products = new ArrayList<>();

    private LoadingScreen loadingScreen;
    private Dialog dialog;

    private androidx.appcompat.widget.SearchView searchView;
    private ImageButton buttonCart;
    private ImageButton buttonFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent();
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
//        viewModel.getCategoriesAndProducts(parameters);
    }

    /**
     * @author Phong-Kaster
     * mapping widgets with XML layout
     * @param view is the current content of application
     */
    private void setupComponent(View view)
    {
        categoriesRecyclerView = view.findViewById(R.id.categoryFragmentCategoriesRecyclerView);
        productsRecyclerView  = view.findViewById(R.id.categoryFragmentProductsRecyclerView);

        loadingScreen = new LoadingScreen(requireActivity());
        dialog = new Dialog(requireActivity());

        searchView = view.findViewById(R.id.categoryFragmentSearchView);
        buttonCart = view.findViewById(R.id.categoryFragmentButtonCart);
//        buttonFilter = view.findViewById(R.id.categoryFragmentButtonFilter);
    }

    /**
     * @author Phong-Kaster
     * set up view model
     */
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(CategoryFragmentViewModel.class);
        viewModel.instantiate();


        /*Step 2 - get categories and products*/
        viewModel.getCategoriesAndProducts(parameters);


        /*Step 3 - listen API returns data about categories and products*/
        viewModel.getCategoriesData().observe(requireActivity(), categoriesResponse -> {
            int result = categoriesResponse.getResult();
//            System.out.println("get categories data - result: " + result);
            if( result == 1)
            {
                categories.clear();
                categories.addAll(categoriesResponse.getData());
                setupCategoriesRecyclerView(categories);
            }
        });

        viewModel.getProductsData().observe(requireActivity(), productsResponse -> {
            int result = productsResponse.getResult();
            if( result == 1)
            {
                products.clear();
                products.addAll(productsResponse.getData());
                setupProductsRecyclerView(products);
            }
        });


        /*Step 4 - get animation*/
//        viewModel.getAnimation().observe(requireActivity(), aBoolean -> {
//            if(aBoolean)
//            {
//                loadingScreen.start();
//            }
//            else
//            {
//                loadingScreen.stop();
//            }
//        });

        /*Step 5 - get products with categoryId*/
        viewModel.getProductsWithCategoryID().observe(requireActivity(), getProductsWithCategoryIDResponse -> {
            int result = getProductsWithCategoryIDResponse.getResult();
            System.out.println("getProductsWithCategoryIDResponse Result: " + result);
        });
    }

    /**
     * @author Phong-Kaster
     * set up products recycler view
     */
    private void setupProductsRecyclerView(List<ProductClient> objects)
    {
        productsAdapter = new ProductsRecyclerViewAdapter(getActivity(), objects);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2,
                LinearLayoutManager.VERTICAL,
                false);

        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(manager);
    }


    /**
     * @author Phong-Kaster
     * set up categories recycler view
     */
    private void setupCategoriesRecyclerView(List<Category> objects)
    {
        categoriesAdapter = new CategoriesRecyclerViewAdapter(getActivity(), objects, this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1,
                LinearLayoutManager.VERTICAL,
                false);

        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(manager);
    }



    /**
     * @author Phong-Kaster
     * get products bases on category id is being passed!
     * Situation 1: if categoryId = 1 => get all products
     * Situation 2: if categoryId != 1 => get products match with this id. If there isn't any products found, show
     * Toast message "Can not found any products" with this category id.
     * @param categoryId is the id of the category we wanna find products related
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClicked(int categoryId) {
        productsAdapter.notifyDataSetChanged();
        if( categoryId == 1)
        {
            viewModel.getCategoriesAndProducts(parameters);
        }
        else
        {
            viewModel.getProductsWithCategoryId(categoryId);
            viewModel.getProductsWithCategoryID().observe(requireActivity(), getProductsWithCategoryIDResponse -> {
                int result = getProductsWithCategoryIDResponse.getResult();
                if (result == 1 && getProductsWithCategoryIDResponse.getProducts().size() > 1)
                {
                    products = getProductsWithCategoryIDResponse.getProducts();
                    setupProductsRecyclerView(products);
                }
//                else
//                {
//                    Toast.makeText(getContext(), getString(R.string.can_not_find_match_products), Toast.LENGTH_SHORT).show();
//                }
            });
        }
    }

    /**
     * @author Phong-Kaster
     * set up what happens when users click on specific buttons
     */
    private void setupEvent()
    {
        /* SEARCH VIEW */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*get text users write down to query*/
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("demand","");
                intent.putExtra("search",query);
                startActivity(intent);

                return false;
            }

            /*if users click on close button - newText = "" then query again */
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        buttonCart.setOnClickListener(view -> {
//            CartFragment fragment = new CartFragment();
//            requireActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frameLayout, fragment, "myFragment")
//                    .addToBackStack(null)
//                    .commit();
//            HomeActivity.getmInstanceActivity().enableFragment(new CartFragment());
            BottomNavigationView bottomNavigationView;
            bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.bottomNavigationMenu);
            bottomNavigationView.setSelectedItemId(R.id.shortcutCart);
        });
    }
}