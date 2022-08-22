package com.example.doanthuctap.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doanthuctap.MainActivity;
import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.cart.CartCheckoutActivity;
import com.example.doanthuctap.activity.home.CartFragment;
import com.example.doanthuctap.activity.home.HomeActivity;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ProductByIdResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Photo;
import com.example.doanthuctap.model.Product;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.product.ProductInformationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInformationActivity extends AppCompatActivity implements AddToCartModalBottomSheet.ModalBottomSheetListener {
    private ImageSlider imageSlider;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtRemaining;
    private TextView txtManufacturer;
    private TextView txtScreenSize;
    private TextView txtCPU;
    private TextView txtRam;
    private TextView txtGraphicCard;
    private TextView txtRom;
    private TextView txtDemand;
    private TextView txtContent;
    private RecyclerView recyclerView;


    private String orderId;
    private String productId;
    private String temporaryAvatar;/*sau nay co server public thi se ko dung cai nay nua*/
    private ProductInformationViewModel viewModel;
    private List<Photo> productPhotos = new ArrayList<>();/* sau nay co server public - product photos luu tru duong dan anh cua san pham*/


    private List<ProductClient> similarProducts = new ArrayList<>();// mang luu cac san pham lien quan toi san pham duoc chon
    private ProductsRecyclerViewAdapter adapter;
    private Dialog dialog;
    private LoadingScreen loadingScreen;
    private Map<String, String> parameters = new HashMap<>();// parameters to filter products


    private ImageButton buttonBack;
    private AppCompatButton buttonAddToCart;
    private AppCompatButton buttonBuyNow;
    private AppCompatButton buttonReview;
    private ImageButton buttonCart;

    private int productQuantity = 1;// for add to cart modal bottom sheet
    private int totalAmount = 0;// for add to cart modal bottom sheet
    private Product product;// the product is gotten from viewModel

    private GlobalVariable globalVariable;
    private Map<String, String> headers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        /*get data passed from ProductsRecyclerViewAdapter*/
        productId = getIntent().getStringExtra("productId");
        temporaryAvatar = getIntent().getStringExtra("temporaryAvatar");

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @author Phong-Kaster
     * mapping components with XML layout
     */
    private void setupComponent()
    {
        imageSlider = findViewById(R.id.productInforImageSlider);
        txtName = findViewById(R.id.productInforName);
        txtPrice = findViewById(R.id.productInforPrice);
        txtRemaining = findViewById(R.id.productInforRemaining);
        txtManufacturer = findViewById(R.id.productInforManufacturer);
        txtScreenSize = findViewById(R.id.productInforScreenSize);
        txtCPU = findViewById(R.id.productInforCPU);
        txtRam = findViewById(R.id.productInforRam);
        txtGraphicCard = findViewById(R.id.productInforGraphicCard);
        txtRom = findViewById(R.id.productInforROM);
        txtDemand = findViewById(R.id.productInforDemand);
        txtContent = findViewById(R.id.productInforContent);
        recyclerView = findViewById(R.id.productInforSimilarProducts);
        recyclerView.setNestedScrollingEnabled(false);

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        buttonAddToCart = findViewById(R.id.productInforButtonAddToCart);
//        buttonReview = findViewById(R.id.productInforButtonReview);
        buttonBuyNow = findViewById(R.id.productInforButtonBuyNow);

        globalVariable = (GlobalVariable) this.getApplication();
        headers = globalVariable.getHeaders();
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(ProductInformationViewModel.class);
        viewModel.instantiate();


        /*Step 2 - get product by id*/
        viewModel.getProductById(productId);
        viewModel.getObjects().observe(this, productByIdResponse -> {
            int result = productByIdResponse.getResult();
            if( result == 1)
            {
                product = productByIdResponse.getData();
                setupScreen(product);
                productPhotos.addAll( productByIdResponse.getPhotos() );
            }
            else
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.oops_there_is_an_issue), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });


        /*Step 3 - get similar products*/
        Map<String, String> parameters = new HashMap<>();
        viewModel.getProducts(parameters);
        viewModel.getSimilarProducts().observe(this, productsResponse -> {
            int result = productsResponse.getResult();
            if( result == 1)
            {
                similarProducts.clear();
                similarProducts.addAll( productsResponse.getData() );
                setupRecyclerView(similarProducts);
            }
            else
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.oops_there_is_an_issue), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });


        /*Step 4 - get latest order of authUser in the first time to create a new one
        * if there is no order. It create a new order with long order-id: f61ae2e1-e93e-4842-b1de-15780f219681
        * the second time helps us to get the order have been just created, and now
        * we could get short order-id: f61ae2e1-e93e-48*/
        Map<String, String> headers = globalVariable.getHeaders();
        viewModel.getLatestOrder(headers);
        viewModel.getLatestOrderWithAuthUser().observe(this, getLatestOrderResponse -> {
            int result = getLatestOrderResponse.getResult();
            if( result == 1)
            {
                orderId = getLatestOrderResponse.getData().getId();
            }
        });

        /*Final Step - get animation*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean){
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
    }

    private void setupScreen(Product product)
    {
        /*Step 1 - retrieve product's information as variable*/
        String name = product.getName();
        String price = Beautifier.formatNumber(product.getPrice()) + "đ";
        String remaining = product.getRemaining() > 0 ?
                "Còn lại: " + product.getRemaining() :
                getString(R.string.out_of_stock);

        String manufacturer = product.getManufacturer();
        double screenSize = product.getScreenSize();
        String cpu = product.getCpu();
        String ram = product.getRam();
        String graphicCard = product.getGraphicCard();
        String rom = product.getRom();
        String demand = product.getDemand();
        String content = product.getContent();


        /*Step 2 - set these variables to view*/
        txtName.setText(name);
        txtPrice.setText(price);
        txtRemaining.setText(remaining);
        txtManufacturer.setText(manufacturer);
        txtScreenSize.setText(String.valueOf(screenSize));
        txtCPU.setText(cpu);
        txtRam.setText(ram);
        txtGraphicCard.setText(graphicCard);
        txtRom.setText(rom);
        txtDemand.setText(demand);
        txtContent.setText(content);


        /*Step 3 - image Slider - ham duoi day chi hoat dong tam thoi, khi co public server thi can phai nap anh tu viemodel vao*/
        ArrayList<SlideModel> photos = new ArrayList<>();
        photos.add(new SlideModel(temporaryAvatar,  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/400x400/ts/product/df/ba/51/f9145a886e3048b3432cf35708c0208f.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/400x400/ts/product/0b/e5/e2/ca5ff1693d1b7dd3cea1fcd2505650d3.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/400x400/ts/product/35/a6/24/f2d2ee99cf2b0dcbda4a9f71bd30183d.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/400x400/ts/product/43/89/b0/e620c52919d867f9680d4967dec4a762.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1200/ts/product/43/89/b0/e620c52919d867f9680d4967dec4a762.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1200/ts/product/66/c1/9f/8eb56aac317533f3938e82a3248a5690.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1200/ts/product/44/73/c5/288835e30ad87192a3b0042fa59ec267.jpg",  ScaleTypes.FIT));
        photos.add(new SlideModel("https://salt.tikicdn.com/cache/w1200/ts/product/14/3e/f6/83878dc747c1c547922cbabfcbc77fbc.jpg",  ScaleTypes.FIT));
        imageSlider.setImageList(photos, ScaleTypes.FIT);
    }

    private void setupEvent()
    {
//        buttonBack.setOnClickListener(view->finish());
        buttonAddToCart.setOnClickListener(view -> openAddToCartSheet());
//        buttonCart.setOnClickListener(view->{
//            FragmentManager manager = getFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.add(R.id.frameLayout, CartFragment ,"myFragment");
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });

        /*Step 5 - button buy now*/
        buttonBuyNow.setOnClickListener(view->{
            /*put a product into the order*/
            viewModel.modifyOrderContent(headers, orderId, productId, "1");
            viewModel.getModifyOrderContentResponse().observe(this, response->{

                int result = response.getResult();
                if( result == 1)
                {
                    Intent intent = new Intent(this, CartCheckoutActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    dialog.announce();
                    dialog.show(R.string.fail, response.getMsg(), R.drawable.ic_close);
                    dialog.btnOK.setOnClickListener(view1 -> dialog.close());
                }
            });

        });
    }

    private void setupRecyclerView(List<ProductClient> objects)
    {
        adapter = new ProductsRecyclerViewAdapter(ProductInformationActivity.this, objects);
        GridLayoutManager manager = new GridLayoutManager(ProductInformationActivity.this,2,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * @author Phong-Kaster
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    /**
     * @author Phong-Kaster
     * open modal bottom sheet to users select product's quantity
     */
    private void openAddToCartSheet()
    {
        /*Step 1 - create a Bottom Sheet Dialog*/
        @SuppressLint("InflateParams") View addToCart = getLayoutInflater().inflate(R.layout.modal_bottom_sheet_add_to_cart, null);
        BottomSheetDialog sheet = new BottomSheetDialog(ProductInformationActivity.this);
        sheet.setContentView(addToCart);
        sheet.show();


        /*Step 2 - mapping xml layout*/
        AppCompatImageButton buttonAdd = addToCart.findViewById(R.id.addToCartButtonAdd);
        AppCompatImageButton buttonMinus = addToCart.findViewById(R.id.addToCartButtonMinus);
        TextView txtQuantity = addToCart.findViewById(R.id.addToCartQuantity);
        TextView txtTotalAmount = addToCart.findViewById(R.id.addToCartTotalAmount);
        ImageView avatar = addToCart.findViewById(R.id.addToCartProductAvatar);
        AppCompatButton buttonConfirm = addToCart.findViewById(R.id.addToCartButtonConfirm);


        /*Step 3 - set up modal bottom sheet*/
        String defaultPrice = Beautifier.formatNumber(product.getPrice()) + "đ";
        txtTotalAmount.setText(defaultPrice);


        /*temporaryAvatar - sau nay co public server thi phai doi thanh product.getAvatar()*/
        Picasso.get().load(temporaryAvatar).into(avatar);
        txtQuantity.setText(String.valueOf(productQuantity));


        /*Step 4 - handle users click button increase|decrease quantity */
        /*add To Cart Fragment - add product's quantity*/
        buttonAdd.setOnClickListener(view -> {
            productQuantity++;
            totalAmount = productQuantity*product.getPrice();

            String quantity = String.valueOf(productQuantity);
            String totalAmountValue = Beautifier.formatNumber(totalAmount) + "đ";

            txtQuantity.setText(quantity);
            txtTotalAmount.setText(totalAmountValue);
        });

        /*add To Cart Fragment - minus product's quantity*/
        buttonMinus.setOnClickListener(view -> {
            if( productQuantity > 1)
            {
                productQuantity--;
                totalAmount = productQuantity*product.getPrice();

                String quantity = String.valueOf(productQuantity);
                String totalAmountValue = Beautifier.formatNumber(totalAmount) + "đ";

                txtQuantity.setText(quantity);
                txtTotalAmount.setText(totalAmountValue);
            }
        });

        /*add To Cart Fragment - button confirm*/
        System.out.println("button confirm clicked !");
        buttonConfirm.setOnClickListener(view->{
            //System.out.println("add To Cart Fragment - button confirm - orderId: " + orderId);
            /*send request to API - /orders/{id}*/
            //orderId = Beautifier.shortenOrderId(orderId);
            headers = globalVariable.getHeaders();
            viewModel.modifyOrderContent(headers, orderId, productId, txtQuantity.getText().toString());
            /*listen for response data*/
            dialog.announce();
            viewModel.getModifyOrderContentResponse().observe(this, response -> {
                int result = response.getResult();
                sheet.cancel();

                String title = getString(R.string.success);
                String msg = getString(R.string.add_to_cart_successfully);
                int icon = R.drawable.ic_check;

                if(result == 0)
                {
                    title = getString(R.string.fail);
                    msg = response.getMsg();
                    icon = R.drawable.ic_close;
                }


                dialog.show(title, msg, icon);

            });

            /*put dialog.btnOK outside viewModel to guarantee the button works as expected*/
            dialog.btnOK.setOnClickListener(view1 ->{
                dialog.close();
            });
            /*end listen for response data*/
        });



    }
}