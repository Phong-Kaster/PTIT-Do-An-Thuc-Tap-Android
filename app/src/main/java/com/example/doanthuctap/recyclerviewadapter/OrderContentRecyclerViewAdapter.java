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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderContentRecyclerViewAdapter extends RecyclerView.Adapter<OrderContentRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<GetLatestOrderResponseContent> objects = new ArrayList<>();
    private OrderContentRecyclerViewAdapter.Callback callback;
    private int valueQuantity = 1;

    public OrderContentRecyclerViewAdapter(Context context,
                                           List<GetLatestOrderResponseContent> objects,
                                           OrderContentRecyclerViewAdapter.Callback callback)
    {
        this.context = context;
        this.objects = objects;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.order_content_recycler_view_adapter_element, parent, false);



        return new ViewHolder(view);
    }

    @Override
    @SuppressLint("ResourceType")
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
//        holder.callback = this.callback;
        holder.name.setText(name);
        holder.price.setText(price);
        holder.quantity.setText(String.valueOf(quantity));
//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.buttonAdd.setOnClickListener(view -> {
            /*send price to update total amount in cart fragment*/
            callback.onClickButtonQuantity("add", content.getPrice());

            /*update current element's view*/
            valueQuantity++;
            holder.quantity.setText(String.valueOf(valueQuantity));
        });
        holder.buttonMinus.setOnClickListener(view->{
            callback.onClickButtonQuantity("minus", content.getPrice());
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout parentLayout;
        private ImageView avatar;
        private TextView name;
        private TextView price;
        private AppCompatImageButton buttonAdd;
        private AppCompatImageButton buttonMinus;
        private TextView quantity;
        private int valueQuantity = 1;
        //private OrderContentRecyclerViewAdapter.Callback callback;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setupComponent(itemView);
//            setupEvent();
        }

        private void setupComponent(View itemView)
        {
            parentLayout = itemView.findViewById(R.id.orderContentParentLayout);
            avatar = itemView.findViewById(R.id.orderContentAvatar);
            name = itemView.findViewById(R.id.orderContentName);
            price = itemView.findViewById(R.id.orderContentPrice);
            buttonAdd = itemView.findViewById(R.id.orderContentButtonAdd);
            buttonMinus = itemView.findViewById(R.id.orderContentButtonMinus);
            quantity = itemView.findViewById(R.id.orderContentQuantity);
        }

//        private void setupEvent()
//        {
//            buttonAdd.setOnClickListener(view -> {
//                valueQuantity++;
//                quantity.setText(String.valueOf(valueQuantity));
//            });
//
//            buttonMinus.setOnClickListener(view -> {
//                if( valueQuantity > 1)
//                {
//                    valueQuantity--;
//                    quantity.setText(String.valueOf(valueQuantity));
//                }
//            });
//        }
    }

    public interface Callback
    {
        /*
        * when users change quantity of a product, this function
        * update total amount in cart fragment
        * */
        void onClickButtonQuantity(String action, int price);
    }
}
