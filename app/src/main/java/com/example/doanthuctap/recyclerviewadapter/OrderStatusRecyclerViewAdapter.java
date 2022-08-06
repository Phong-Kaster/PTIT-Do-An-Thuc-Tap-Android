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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.model.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusRecyclerViewAdapter extends RecyclerView.Adapter<OrderStatusRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<OrderStatus> objects = new ArrayList<>();
    private int selectedItem = 0;
    private callbacks callback;


    public OrderStatusRecyclerViewAdapter(Context context, List<OrderStatus> objects, callbacks callback )
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
                .inflate(R.layout.orders_status_recycler_view_element, parent, false);
        return new OrderStatusRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*Step 1 - retrieve data from order status clicked*/
        OrderStatus element = objects.get(position);
        String key = element.getKey();
        String value = element.getValue();

        int colorDefault = ContextCompat.getColor(context,R.color.colorTextSupport);
        int colorChosen = ContextCompat.getColor(context,R.color.colorBlue);
        holder.name.setTextColor(colorDefault);

        if( selectedItem == position)
        {
            holder.name.setTextColor(colorChosen);
        }


        /*Step 2 - set data to view*/
        holder.name.setText(key);
        holder.layout.setOnClickListener(view->{
            /*reset color for new selected item & previous item*/
            int previousItem = selectedItem;
            selectedItem = position;

            notifyItemChanged(previousItem);
            notifyItemChanged(selectedItem);

            callback.onItemClicked(element.getValue());

            //Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setSelectedItem(int position)
    {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout;
        private TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.orderStatusLayout);
            name = itemView.findViewById(R.id.orderStatusName);

        }
    }

    public interface callbacks{
        void onItemClicked(String status);
    }
}
