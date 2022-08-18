package com.example.doanthuctap.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.personality.OrderInformationActivity;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductPhotosRecyclerViewAdapter extends RecyclerView.Adapter<ProductPhotosRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Uri> objects = new ArrayList<>();

    public ProductPhotosRecyclerViewAdapter(Context context, List<Uri> objects)
    {
        this.context = context;
        this.objects = objects;
    }



    @NonNull
    @Override
    public ProductPhotosRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.products_recycler_view_adapter_element, parent, false);
        return new ProductPhotosRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = objects.get(position);
        holder.photo.setImageURI(uri);
        holder.layout.setOnClickListener(view1->{
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.elementPhoto);
            layout = itemView.findViewById(R.id.elementLayout);

        }
    }

}
