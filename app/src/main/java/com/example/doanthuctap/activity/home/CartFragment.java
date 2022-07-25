package com.example.doanthuctap.activity.home;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.OrderContentRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.home.CartFragmentViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    private AppCompatButton buttonConfirmCart;
    private RecyclerView orderContentRecyclerView;
    private RecyclerView similarProductsRecyclerView;

    private ProductsRecyclerViewAdapter productAdapter;
    private OrderContentRecyclerViewAdapter orderContentAdapter;


    private CartFragmentViewModel viewModel;
    private List<ProductClient> products = new ArrayList<>();
    private List<GetLatestOrderResponseContent> orderContents = new ArrayList<>();


    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private LoadingScreen loadingScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Retrieve bundle's arguments
        assert this.getArguments() != null;
        String accessToken = this.getArguments().getString("accessToken");
        String contentType = this.getArguments().getString("contentType");


        /*initialize headers to attach HTTP Request*/
        headers.put("Authorization", accessToken);
        headers.put("Content-Type", contentType);


        setupComponent(view);
        setupViewModel();
        return view;
    }

    private void setupComponent(View view)
    {
        buttonConfirmCart = view.findViewById(R.id.cartFragmentButtonConfirmCart);
        orderContentRecyclerView = view.findViewById(R.id.cartFragmentOrderContent);
        //orderContentRecyclerView.setNestedScrollingEnabled(false);
        similarProductsRecyclerView = view.findViewById(R.id.cartFragmentSimilarProducts);
        //similarProductsRecyclerView.setNestedScrollingEnabled(false);

        loadingScreen = new LoadingScreen(requireActivity());
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(CartFragmentViewModel.class);
        viewModel.instantiate();


        /*Step 2 - get necessary products*/
        viewModel.getOrderContents(headers);
        viewModel.getSimilarProducts(parameters);


        /*Step 2 - get latest order to pick up order contents*/
        viewModel.getLastOrder().observe(requireActivity(), getLatestOrderResponse -> {
            int result = getLatestOrderResponse.getResult();
            String msg = getLatestOrderResponse.getMsg();
            System.out.println("cart fragment - result: " + result);
            System.out.println("cart fragment - msg: " + msg);
            if( result == 1 && getLatestOrderResponse.getContent().size() > 0)
            {
                orderContents.clear();
                orderContents.addAll( getLatestOrderResponse.getContent() );
                setupOrderContentRecyclerView(orderContents);
            }
        });


        /*Step 3 - get similar products*/
        viewModel.getProducts().observe(requireActivity(), productsResponse -> {
            int result = productsResponse.getResult();
            if( result == 1 && productsResponse.getData().size() > 0)
            {
                products.clear();
                products.addAll( productsResponse.getData() );
                setupSimilarProductsRecyclerView( products );
            }
        });


        /*Step 4 - get animation*/
        viewModel.getAnimation().observe(requireActivity(), aBoolean -> {
            if( aBoolean)
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
     * @author Phong-Kaster
     * set up order content recycler view
     * @param objects is list of order content from one order
     */
    private void setupOrderContentRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        orderContentAdapter = new OrderContentRecyclerViewAdapter(getActivity(), objects);
        orderContentRecyclerView.setAdapter(orderContentAdapter);
        orderContentRecyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        orderContentRecyclerView.setLayoutManager(manager);

    }


    /**
     * @author Phong-Kaster
     * set up similar products recycler view
     * @param objects is list of similar products
     */
    private void setupSimilarProductsRecyclerView(List<ProductClient> objects)
    {
        productAdapter = new ProductsRecyclerViewAdapter(getActivity(), objects);
        similarProductsRecyclerView.setAdapter(productAdapter);
        similarProductsRecyclerView.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(getActivity(),2,
                LinearLayoutManager.VERTICAL,
                false);
        similarProductsRecyclerView.setLayoutManager(manager);

    }
}