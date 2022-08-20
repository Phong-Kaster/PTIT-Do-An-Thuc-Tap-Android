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


import com.bumptech.glide.Glide;
import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.admin.order.AdminOrderChangeInformationActivity;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.Photo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductPhotosRecyclerViewAdapter extends RecyclerView.Adapter<ProductPhotosRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Photo> objects = new ArrayList<>();
    private final String ROOT_URL = Beautifier.getRootURL();
    private ProductPhotosRecyclerViewAdapter.Callbacks callbacks;

    public ProductPhotosRecyclerViewAdapter(Context context, List<Photo> objects, ProductPhotosRecyclerViewAdapter.Callbacks callbacks )
    {
        this.context = context;
        this.objects = objects;
        this.callbacks = callbacks;
    }



    @NonNull
    @Override
    public ProductPhotosRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.photo_recycler_view_element, parent, false);
        return new ProductPhotosRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int id = objects.get(position).getId();
        Photo photo = objects.get(position);
        String avatar = ROOT_URL + photo.getPath();
        int isDefaultAvatar = objects.get(position).getIsAvatar();


        Picasso.get().load(avatar).into(holder.photo);
        holder.layout.setOnClickListener(view1->{
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
        });


        /**
         * if the photo is the default avatar then disable button set avatar and enable txtDefaultAvatar
         */
        if( isDefaultAvatar == 1 )
        {
            holder.buttonSetAvatar.setEnabled(false);
            int colorGrey = ContextCompat.getColor(context,R.color.colorGrey);
            holder.buttonSetAvatar.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.background_button_edit_order_information_disable ));
            holder.buttonSetAvatar.setTextColor(colorGrey);
            holder.txtDefaultAvatar.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.buttonSetAvatar.setOnClickListener(view->{
                callbacks.onClickButtonSetAvatar(id);
            });
        }

        holder.buttonRemove.setOnClickListener(view1->{
            callbacks.onClickButtonRemove(id);
        });

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final ImageView photo;
        private final AppCompatButton buttonSetAvatar;
        private final AppCompatButton buttonRemove;
        private final TextView txtDefaultAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.elementPhoto);
            layout = itemView.findViewById(R.id.elementLayout);
            buttonRemove = itemView.findViewById(R.id.elementButtonRemove);
            buttonSetAvatar = itemView.findViewById(R.id.elementButtonSetAvatar);
            txtDefaultAvatar = itemView.findViewById(R.id.elementDefaultAvatar);
        }
    }


    public interface Callbacks{
        void onClickButtonRemove(int photoId);
        void onClickButtonSetAvatar( int photoId);
    }
}
