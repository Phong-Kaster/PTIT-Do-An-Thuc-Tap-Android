package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.admin.order.AdminOrderChangeInformationActivity;
import com.example.doanthuctap.activity.personality.OrderInformationActivity;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.model.Order;

import java.util.Arrays;
import java.util.List;

public class AdminOrderInformationRecyclerViewAdapter extends RecyclerView.Adapter<AdminOrderInformationRecyclerViewAdapter.ViewHolder> {

    private List<Order> orders;
    private Context context;
    private AdminOrderInformationRecyclerViewAdapter.callbacks callbacks;
    private Dialog dialog;

    public AdminOrderInformationRecyclerViewAdapter(Context context, List<Order> orders, AdminOrderInformationRecyclerViewAdapter.callbacks callbacks)
    {
        this.context = context;
        this.orders = orders;
        this.callbacks = callbacks;
        dialog = new Dialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.admin_order_information_recycler_view_element, parent, false);
        return new AdminOrderInformationRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order element = orders.get(position);
        String orderId = element.getId();
        String status = Beautifier.convertStatusToReadableStatus(context, element.getStatus());
        String date = Beautifier.convertStringToReadableDate( element.getUpdateAt() );
        String total = Beautifier.formatNumber( element.getTotal() ) + "đ";

        holder.orderId.setText( orderId );
        holder.status.setText(status);
        holder.date.setText( date);
        holder.total.setText( total );

        /*LAYOUT*/
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, OrderInformationActivity.class);
            intent.putExtra("orderId", orderId);
            context.startActivity(intent);
        });


        /*BUTTON WATCH*/
        holder.buttonWatch.setOnClickListener(view->{
            Intent intent = new Intent(context, OrderInformationActivity.class);
            intent.putExtra("orderId", orderId);
            context.startActivity(intent);
        });


        /*BUTTON EDIT*/
        List<String> invalidStatus = Arrays.asList("delivered", "cancel");
        boolean flag = invalidStatus.contains(element.getStatus());
        if( flag )
        {
            holder.buttonEdit.setEnabled(false);
            int colorGrey = ContextCompat.getColor(context,R.color.colorGrey);
            holder.buttonEdit.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.background_button_edit_order_information_disable ));
            holder.buttonEdit.setTextColor(colorGrey);
        }
        else
        {
            holder.buttonEdit.setOnClickListener(view->{
                Intent intent = new Intent(context, AdminOrderChangeInformationActivity.class);
                intent.putExtra("orderId", orderId);
                context.startActivity(intent);
            });
        }


        /*BUTTON REMOVE*/
        holder.buttonRemove.setOnClickListener(view->{
            dialog.confirm();
            dialog.show(R.string.attention, context.getString(R.string.are_you_sure_about_that), R.drawable.ic_info);
            dialog.btnOK.setOnClickListener(view1 -> {
                dialog.close();
                callbacks.onRemoveButtonClicked(orderId);
            });
            dialog.btnCancel.setOnClickListener(view1->{
                dialog.close();
            });
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final TextView orderId;
        private final TextView status;
        private final TextView date;
        private final TextView total;
        private final AppCompatButton buttonWatch;
        private final AppCompatButton buttonEdit;
        private final AppCompatButton buttonRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId     = itemView.findViewById(R.id.adminOrderInformationID);
            layout      = itemView.findViewById(R.id.adminOrderInformationLayout);
            status      = itemView.findViewById(R.id.adminOrderInformationStatus);
            date        = itemView.findViewById(R.id.adminOrderInformationDate);
            total       = itemView.findViewById(R.id.adminOrderInformationTotal);
            buttonWatch = itemView.findViewById(R.id.adminOrderInformationButtonWatch);
            buttonEdit  = itemView.findViewById(R.id.adminOrderInformationButtonEdit);
            buttonRemove = itemView.findViewById(R.id.adminOrderInformationButtonRemove);
        }
    }

    public interface callbacks{
        void onRemoveButtonClicked(String orderId);
    }
}
