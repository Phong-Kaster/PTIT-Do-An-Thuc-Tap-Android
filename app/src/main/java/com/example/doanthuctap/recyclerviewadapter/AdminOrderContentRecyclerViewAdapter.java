package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminOrderContentRecyclerViewAdapter extends RecyclerView.Adapter<AdminOrderContentRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<GetLatestOrderResponseContent> objects = new ArrayList<>();
    private OrderContentRecyclerViewAdapter.Callback callback;
    private String orderStatus;// is used to determine enable / disable button quantity
    private int valueQuantity = 1;


    public AdminOrderContentRecyclerViewAdapter(Context context,
                                           List<GetLatestOrderResponseContent> objects,
                                           OrderContentRecyclerViewAdapter.Callback callback, String orderStatus)
    {
        this.context = context;
        this.objects = objects;
        this.callback = callback;
        this.orderStatus = orderStatus;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.order_content_recycler_view_adapter_element, parent, false);

        return new AdminOrderContentRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceType")
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
        holder.quantity.setText(String.valueOf(quantity));

        /*if order status in array <invalid-status> then we do not allow modifying order content*/
        List<String> invalidStatus = Arrays.asList("being transported", "delivered", "cancel");
        boolean flag = invalidStatus.contains(orderStatus);


        if( flag )
        {
//            Toast.makeText(context, "Your order status is "+orderStatus+" & you can't modify order content !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            holder.buttonAdd.setOnClickListener(view -> {
                /*send price to update total amount in cart fragment*/
                callback.onClickButtonQuantity("add", content.getProductId(), valueQuantity, content.getPrice());

                /*update current element's view*/
                valueQuantity++;
                holder.quantity.setText(String.valueOf(valueQuantity));
            });
            holder.buttonMinus.setOnClickListener(view->{
                if( valueQuantity > 1)
                {
                    callback.onClickButtonQuantity("minus", content.getProductId(), valueQuantity, content.getPrice());
                    Toast.makeText(context, "Minus", Toast.LENGTH_SHORT).show();
                    /*update current element's view*/
                    valueQuantity--;
                    holder.quantity.setText(String.valueOf(valueQuantity));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView name;
        private TextView price;
        private AppCompatImageButton buttonAdd;
        private AppCompatImageButton buttonMinus;
        private TextView quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setupComponent(itemView);
        }

        private void setupComponent(View itemView)
        {
            avatar = itemView.findViewById(R.id.orderContentAvatar);
            name = itemView.findViewById(R.id.orderContentName);
            price = itemView.findViewById(R.id.orderContentPrice);
            buttonAdd = itemView.findViewById(R.id.orderContentButtonAdd);
            buttonMinus = itemView.findViewById(R.id.orderContentButtonMinus);
            quantity = itemView.findViewById(R.id.orderContentQuantity);
        }

    }

    public interface Callback
    {
        /*
         * when users change quantity of a product, this function
         * update total amount in cart fragment
         * */
        void onClickButtonQuantity(String action, int productId, int quantity, int price);
    }

}
