package com.example.doanthuctap.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Photo;
import com.example.doanthuctap.recyclerviewadapter.AdminProductsRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.ProductPhotosRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.AdminPhotoViewModel;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPhotoActivity extends AppCompatActivity implements ProductPhotosRecyclerViewAdapter.Callbacks{

    private AdminPhotoViewModel viewModel;
    private AppCompatButton buttonOpenGallery;
    private RecyclerView recyclerView;

    private GlobalVariable globalVariable;
    private Map<String, String> headers = new HashMap<>();

    private LoadingScreen loadingScreen;
    private Dialog dialog;

    private String productId;

    private ProductPhotosRecyclerViewAdapter adapter;
    private List<Photo> photos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_photo);

        productId = getIntent().getStringExtra("productId");

        setupComponent();
        setupViewModel();
        setupRecyclerView();
    }

    private void setupComponent()
    {
        buttonOpenGallery = findViewById(R.id.adminPhotoButtonOpenGallery);
        recyclerView = findViewById(R.id.adminPhotoRecyclerView);

        globalVariable = (GlobalVariable) this.getApplication();
        headers = globalVariable.getHeaders();

        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(AdminPhotoViewModel.class);
        viewModel.instantiate();

        /*Step 2 - instantiate*/
        viewModel.getPhotos(headers, productId);

        /*Step 3 - animation*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });

        /*Step 4 - get response*/
        viewModel.getPhotosResponse().observe(this, response -> {
            int result = response.getResult();
            if( result == 1 && response.getData().size() > 0)
            {
                photos.clear();
                photos.addAll(response.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerView()
    {
        adapter = new ProductPhotosRecyclerViewAdapter(this, photos, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickButtonRemove(int photoId) {
        viewModel.removePhoto(headers, productId, photoId);

        dialog.announce();
        viewModel.getPhotoResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {

                dialog.show(R.string.success, getString(R.string.action_successfully), R.drawable.ic_check);
                adapter.notifyDataSetChanged();
            }
            else
            {
                dialog.show(R.string.fail, response.getMsg(), R.drawable.ic_close);
            }
        });
        dialog.btnOK.setOnClickListener(view->{
            dialog.close();
            viewModel.getPhotos(headers, productId);
            viewModel.getPhotosResponse().observe(this, response->{
                int result = response.getResult();
                if( result == 1 && response.getData().size() > 0)
                {
                    photos.clear();
                    photos.addAll(response.getData());
                    setupRecyclerView();
                }
            });
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickButtonSetAvatar(int photoId) {
        viewModel.setAvatar(headers, productId, photoId);

        dialog.announce();
        viewModel.getPhotoResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {

                dialog.show(R.string.success, getString(R.string.action_successfully), R.drawable.ic_check);
            }
            else
            {
                dialog.show(R.string.fail, response.getMsg(), R.drawable.ic_close);
            }
        });
        dialog.btnOK.setOnClickListener(view->{
            dialog.close();
            viewModel.getPhotos(headers, productId);
            viewModel.getPhotosResponse().observe(this, response->{
                int result = response.getResult();
                if( result == 1 && response.getData().size() > 0)
                {
                    photos.clear();
                    photos.addAll(response.getData());
                    setupRecyclerView();
                }
            });
        });
    }
}