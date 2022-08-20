package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.doanthuctap.activity.product.ProductInformationActivity;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.model.ProductClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ProductClient> objects = new ArrayList<>();
    private final String ROOT_URL = Beautifier.getRootURL();

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
                ROOT_URL + product.getAvatar() : context.getString(R.drawable.product_default_avatar);
        int id = product.getId();
        String name =       Beautifier.shortenName(product.getName());
        String price =      Beautifier.formatNumber(product.getPrice()) + "đ";
        String remaining = product.getRemaining() > 0 ?
                "Còn lại: " + product.getRemaining() :
                context.getString(R.string.out_of_stock);

//        holder.productAvatar.setBackgroundResource(R.drawable.product_default_avatar);
        /*sau nay co server public thi se ko dung cai nay nua*/
        String temporaryAvatar = Beautifier.generateRandomAvatar();

        Picasso.get().load(avatar).into(holder.productAvatar);
        holder.productName.setText(name);
        holder.productPrice.setText(price);
        holder.productRemaining.setText(remaining);
        holder.productLayout.setOnClickListener(view->{
            Intent intent = new Intent(context, ProductInformationActivity.class);
            intent.putExtra("productId", String.valueOf(id) );
            /*gui kem cai anh dang load bang internet, sau nay co server public thi ko can nua*/
            intent.putExtra("temporaryAvatar", avatar);
            context.startActivity(intent);
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
