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
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CheckoutOrderContentRecyclerViewAdapter extends RecyclerView.Adapter<CheckoutOrderContentRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<GetLatestOrderResponseContent> objects = new ArrayList<>();


    public CheckoutOrderContentRecyclerViewAdapter(Context context, List<GetLatestOrderResponseContent> objects)
    {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.checkout_order_content_element, parent, false);
        return new CheckoutOrderContentRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetLatestOrderResponseContent content = objects.get(position);

        String name = content.getProductName();
         String avatar = content.getProductAvatar().length() > 0 ?
                content.getProductAvatar() : context.getString(R.drawable.product_default_avatar);
        String price =      Beautifier.formatNumber( content.getPrice() ) + "đ";
        int quantity = content.getQuantity();


        // holder.productAvatar.setBackgroundResource(R.drawable.product_default_avatar);
        /*sau nay co server public thi se ko dung cai nay nua*/
        String temporaryAvatar = Beautifier.generateRandomAvatar();


        Picasso.get().load(temporaryAvatar).into(holder.avatar);
        holder.name.setText(name);
        holder.price.setText(price);
        holder.quantity.setText("x" + quantity);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView name;
        private TextView price;
        private TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.checkoutAvatar);
            name = itemView.findViewById(R.id.checkoutName);
            price = itemView.findViewById(R.id.checkoutPrice);
            quantity = itemView.findViewById(R.id.checkoutQuantity);
        }
    }
}
