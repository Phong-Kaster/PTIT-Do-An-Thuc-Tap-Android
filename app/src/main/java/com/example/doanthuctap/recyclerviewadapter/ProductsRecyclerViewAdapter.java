package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.ProductClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ProductClient> objects = new ArrayList<>();

    public ProductsRecyclerViewAdapter(Context context, List<ProductClient> objects){
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.products_recycler_view_adapter_element, parent, false);
        return new ProductsRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductClient product = objects.get(position);

        String avatar = product.getAvatar().length() > 0 ?
                product.getAvatar() : context.getString(R.drawable.product_default_avatar);

        String name =       Beautifier.shortenName(product.getName());
        String price =      Beautifier.formatNumber(product.getPrice()) + "đ";
        String remaining = product.getRemaining() > 0 ?
                "Còn lại: " + product.getRemaining() :
                context.getString(R.string.out_of_stock);

//        holder.productAvatar.setBackgroundResource(R.drawable.product_default_avatar);

        String temporaryAvatar = Beautifier.generateRandomAvatar();

        Picasso.get().load(temporaryAvatar).into(holder.productAvatar);
        holder.productName.setText(name);
        holder.productPrice.setText(price);
        holder.productRemaining.setText(remaining);
        holder.productLayout.setOnClickListener(view->{
            Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout productLayout;
        private ImageView productAvatar;
        private TextView productName;
        private TextView productPrice;
        private TextView productRemaining;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productLayout = itemView.findViewById(R.id.productLayout);
            productAvatar = itemView.findViewById(R.id.productAvatar);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productRemaining = itemView.findViewById(R.id.productRemaining);
        }
    }
}
