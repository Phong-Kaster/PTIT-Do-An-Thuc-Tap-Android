package com.example.doanthuctap.activity.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.FileUtils;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Photo;
import com.example.doanthuctap.recyclerviewadapter.ProductPhotosRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.AdminPhotoViewModel;

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
        setupEvent();
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

    /**
     * @author Phong-Kaster
     * open Gallery To Pick Photo
     */
    private final ActivityResultLauncher<Intent> openGalleryToPickPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();
                        uploadPhotoToServer(uri);
                    }
                    else
                    {
                        dialog.announce();
                        dialog.show(R.string.fail, getString(R.string.oops_there_is_an_issue), R.drawable.ic_close);
                        dialog.btnOK.setOnClickListener(view->{
                            dialog.close();
                        });
                    }
                }
            });

    private void setupEvent()
    {
        buttonOpenGallery.setOnClickListener(view->{

            verifyStoragePermissions(AdminPhotoActivity.this);

//            Intent intent = new Intent();
//            intent.setType("image/*");//allows any image file type. Change * to specific extension to limit it
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            intent.setAction(Intent.ACTION_GET_CONTENT);
            Intent intent = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            openGalleryToPickPhoto.launch(intent);
        });
    }


    private void uploadPhotoToServer(Uri uri)
    {
        /*Step 1 - set up file path*/
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,
                projection, null, null, null);

        int columnIndex = cursor.getColumnIndex(projection[0]);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();


        /*Step 2 - configure new request*/
        String accessToken = globalVariable.getAccessToken();
        viewModel.updatePhoto(accessToken,productId, filePath);


        dialog.announce();
        dialog.btnOK.setOnClickListener(view ->{
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
    }


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}