package com.example.doanthuctap.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.personality.OrderInformationActivity;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.model.Order;
import com.example.doanthuctap.model.OrderStatus;

import java.util.ArrayList;
import java.util.List;


public class OrderInformationRecyclerViewAdapter extends RecyclerView.Adapter<OrderInformationRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Order> objects = new ArrayList<>();

    public OrderInformationRecyclerViewAdapter(Context context, List<Order> objects)
    {
        this.context = context;
        this.objects = objects;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.order_information_recycler_view_element, parent, false);
        return new OrderInformationRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order element = objects.get(position);
        String orderId = element.getId();
        String status = Beautifier.convertStatusToReadableStatus(context, element.getStatus());
        String date = Beautifier.convertStringToReadableDate( element.getUpdateAt() );
        String total = Beautifier.formatNumber( element.getTotal() ) + "đ";

        holder.orderId.setText( orderId );
        holder.status.setText(status);
        holder.date.setText( date);
        holder.total.setText( total );
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, OrderInformationActivity.class);
            intent.putExtra("orderId", orderId);
            context.startActivity(intent);
        });
        holder.buttonWatch.setOnClickListener(view->{
            Intent intent = new Intent(context, OrderInformationActivity.class);
            intent.putExtra("orderId", orderId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final TextView orderId;
        private final TextView status;
        private final TextView date;
        private final TextView total;
        private final AppCompatButton buttonWatch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderInformationID);
            layout = itemView.findViewById(R.id.orderInformationLayout);
            status = itemView.findViewById(R.id.orderInformationStatus);
            date = itemView.findViewById(R.id.orderInformationDate);
            total = itemView.findViewById(R.id.orderInformationTotal);
            buttonWatch = itemView.findViewById(R.id.orderInformationButtonWatchOrder);
        }
    }
}
