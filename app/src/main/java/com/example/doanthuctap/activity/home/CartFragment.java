package com.example.doanthuctap.activity.home;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.Beautifier;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CartFragment extends Fragment implements OrderContentRecyclerViewAdapter.Callback {

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


    private String orderId = "";
    private int totalAmount = 0;
    private TextView txtNothingInCart;
    private TextView txtTotalAmount;

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
        setupScreen();
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
        txtNothingInCart = view.findViewById(R.id.textNothingInCart);
        txtTotalAmount = view.findViewById(R.id.cartFragmentTotalAmount);
    }

    @SuppressLint("SetTextI18n")
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(CartFragmentViewModel.class);
        viewModel.instantiate();


        /*Step 2 - get necessary products*/
        viewModel.getOrderContents(headers);
        viewModel.getSimilarProducts(parameters);


        /*Step 3 - get latest order to pick up order contents*/
        viewModel.getLastOrder().observe(requireActivity(), getLatestOrderResponse -> {
            int result = getLatestOrderResponse.getResult();
            if( result == 1 && getLatestOrderResponse.getContent().size() > 0)
            {
                /*hide and show text and order content*/
                txtNothingInCart.setVisibility(View.GONE);
                orderContentRecyclerView.setVisibility(View.VISIBLE);


                /*get order id*/
                orderId = getLatestOrderResponse.getData().getId();
                totalAmount = getLatestOrderResponse.getData().getTotal();
                txtTotalAmount.setText(Beautifier.formatNumber(totalAmount)+"đ");

                /*pour order content into recycler view*/
                orderContents.clear();
                orderContents.addAll( getLatestOrderResponse.getContent() );
                setupOrderContentRecyclerView(orderContents);
            }
            else
            {
                txtNothingInCart.setVisibility(View.VISIBLE);
                orderContentRecyclerView.setVisibility(View.GONE);
            }
        });


        /*Step 4 - get similar products*/
        viewModel.getProducts().observe(requireActivity(), productsResponse -> {
            int result = productsResponse.getResult();
            if( result == 1 && productsResponse.getData().size() > 0)
            {
                products.clear();
                products.addAll( productsResponse.getData() );
                setupSimilarProductsRecyclerView( products );
            }
        });


        /*Step 5 - get animation*/
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

        /*Step 6 - modify order content response*/
    }


    private void setupScreen()
    {
        txtTotalAmount.setText(String.valueOf(totalAmount));
    }

    /**
     * @author Phong-Kaster
     * set up order content recycler view
     * @param objects is list of order content from one order
     */
    private void setupOrderContentRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        orderContentAdapter = new OrderContentRecyclerViewAdapter(getActivity(), objects, CartFragment.this);
        orderContentRecyclerView.setAdapter(orderContentAdapter);
        orderContentRecyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        orderContentRecyclerView.setLayoutManager(manager);


        swipeToDelete(orderContentRecyclerView, orderContentAdapter, objects);
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


    /**
     * @author Phong-Kaster
     * Swipe from right to left to eradicate a transaction
     * */
    private void swipeToDelete(RecyclerView recycleView, OrderContentRecyclerViewAdapter adapter, List<GetLatestOrderResponseContent> products)
    {
        /*Step 1*/
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
                {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Remove swiped item from list and notify the RecyclerView

                        /*declare local variables*/
                        int position = viewHolder.getLayoutPosition();
                        String productId = String.valueOf( products.get(position).getProductId() );
                        String quantity = "0"; // quantity = 0 => delete product from order content
                        /*notify to adapter & remove the transaction on SERVER*/
                        products.remove(position);
                        adapter.notifyItemRemoved(position);
                        viewModel.modifyOrderContent(orderId, productId, quantity);
                    }

                    public void onChildDraw (@NonNull Canvas c,
                                             @NonNull RecyclerView recyclerView,
                                             @NonNull RecyclerView.ViewHolder viewHolder,
                                             float dX, float dY, int actionState, boolean isCurrentlyActive){

                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                                .addActionIcon(R.drawable.ic_delete_forever)
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        /*Step 2*/
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        touchHelper.attachToRecyclerView(recycleView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClickButtonQuantity(String action, int price) {

        if( price < 0 || action.length() < 1 || totalAmount == 0)
        {
            return;
        }


        if( "add".equals(action) )
        {
            totalAmount = totalAmount + price;// sum current total amount with intPrice
        }
        else if( "minus".equals(action) )
        {
            totalAmount = totalAmount - price;
        }





        txtTotalAmount.setText( Beautifier.formatNumber(totalAmount) + "đ" );
    }
}