package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.ProductClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * this recycler view is used to list products in a modal bottom sheet
 * when admin clicks on a product then the product is added into the current order
 */
public class AdminProductsModifyOrderContentRecyclerViewAdapter extends RecyclerView.Adapter<AdminProductsModifyOrderContentRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ProductClient> objects = new ArrayList<>();
    private AdminProductsModifyOrderContentRecyclerViewAdapter.callbacks callbacks;

    public AdminProductsModifyOrderContentRecyclerViewAdapter(Context context,
                                                              List<ProductClient> objects,
                                                              AdminProductsModifyOrderContentRecyclerViewAdapter.callbacks callbacks){
        this.context = context;
        this.objects = objects;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public AdminProductsModifyOrderContentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.products_recycler_view_adapter_element, parent, false);
        return new AdminProductsModifyOrderContentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductClient product = objects.get(position);

        @SuppressLint("ResourceType") String avatar = product.getAvatar().length() > 0 ?
                product.getAvatar() : context.getString(R.drawable.product_default_avatar);
        int id = product.getId();
        String name =       Beautifier.shortenName(product.getName());
        String price =      Beautifier.formatNumber(product.getPrice()) + "đ";
        String remaining = product.getRemaining() > 0 ?
                "Còn lại: " + product.getRemaining() :
                context.getString(R.string.out_of_stock);

//        holder.productAvatar.setBackgroundResource(R.drawable.product_default_avatar);
        /*sau nay co server public thi se ko dung cai nay nua*/
        String temporaryAvatar = Beautifier.generateRandomAvatar();

        Picasso.get().load(temporaryAvatar).into(holder.productAvatar);
        holder.productName.setText(name);
        holder.productPrice.setText(price);
        holder.productRemaining.setText(remaining);
        holder.productLayout.setOnClickListener(view->{
            callbacks.addProductIntoOrder(id, "1");
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

    public interface callbacks
    {
        void addProductIntoOrder(int productId, String quantity);
    }

}
