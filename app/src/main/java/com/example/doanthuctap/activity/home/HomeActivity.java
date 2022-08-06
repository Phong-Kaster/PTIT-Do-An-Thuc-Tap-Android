package com.example.doanthuctap.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * this is the home activity of the application
 * from home activity, we can access directly to 4 fragments
 * 1. Home Fragment
 * 2. Category Fragment
 * 3. Personality Fragment
 * 4. Cart Fragment
 */
public class HomeActivity extends AppCompatActivity {


    private Dialog dialog;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = null;

    public static WeakReference<HomeActivity> weakActivity;




    private boolean isFragmentChosen = false;
    private int selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        weakActivity = new WeakReference<>(HomeActivity.this);

        setupComponent();
        setupVariable();
        setupEvent();



        /*by default, home fragment is the first screen users interact*/
        fragment = new HomeFragment();
        enableFragment(fragment);
    }




    /**
     * if users click back button by accident or on purpose
     * a confirm dialog will pop up to make sure they want to exit the application
     */
    @Override
    public void onBackPressed() {

        dialog.confirm();
        dialog.show(getString(R.string.attention),
                getString(R.string.are_you_sure_about_that), R.drawable.ic_info);
        dialog.btnOK.setOnClickListener(view->{
            super.onBackPressed();
            finish();
        });
        dialog.btnCancel.setOnClickListener(view-> dialog.close());
    }

    /**
     * @author Phong-Kaster
     * instantiate local variable in MainActivity
     */
    private void setupVariable(){
        dialog = new Dialog(this);
    }

    private void setupComponent(){
        bottomNavigationView = findViewById(R.id.bottomNavigationMenu);
    }

    @SuppressLint("NonConstantResourceId")
    private void setupEvent(){
        /*set up event when users click on item in bottom navigation view*/
        bottomNavigationView.setOnItemSelectedListener(item -> {
            isFragmentChosen = false;

            switch (item.getItemId()){
                case R.id.shortcutHome:
                    selectedFragment = R.id.shortcutHome;
                    fragment = new HomeFragment();
                    break;
                case R.id.shortcutCategory:
                    selectedFragment = R.id.shortcutCategory;
                    fragment = new CategoryFragment();
                    break;
                case R.id.shortcutCart:
                    isFragmentChosen = true;
                    selectedFragment = R.id.shortcutCart;
                    fragment = new CartFragment();
                    break;
                case R.id.shortcutPersonality:
                    isFragmentChosen = true;
                    selectedFragment = R.id.shortcutPersonality;
                    fragment = new PersonalityFragment();
                    break;
            }

            enableFragment(fragment);
            return true;
        });
    }

    /**
     * @author Phong-Kaster
     * activate a fragment right away
     * */
    public void enableFragment(Fragment fragment)
    {
        /*Step 1*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        /*Step 2*/
        Map<String, String > headers = ((GlobalVariable)getApplication()).getHeaders();
        String accessToken = headers.get("Authorization");
        String contentType = headers.get("Content-Type");
//
//        User AuthUser = ((GlobalVariable)getApplication()).getAuthUser();
//        SiteSettings appInfo = ((GlobalVariable)getApplication()).getAppInfo();


        /*Step 3*/
        Bundle bundle = new Bundle();

        bundle.putString("accessToken", accessToken);
        bundle.putString("contentType", contentType);
//        bundle.putParcelable("AuthUser", AuthUser);
//        bundle.putParcelable("appInfo", appInfo);
        fragment.setArguments(bundle);


        /*Step 4*/
        transaction.replace(R.id.frameLayout, fragment, "myFragment");
        transaction.commit();
    }

    /**
     * if users click on cart fragment but now they don't have any product in their cart
     * Continually, users click similar products and choose one product.
     * As soon as they press button back, cart is reloaded automatically
     */
    @Override
    protected void onResume() {
        super.onResume();
        if( isFragmentChosen )
        {
            BottomNavigationView bottomNavigationView;
            bottomNavigationView = findViewById(R.id.bottomNavigationMenu);
            bottomNavigationView.setSelectedItemId(selectedFragment);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}