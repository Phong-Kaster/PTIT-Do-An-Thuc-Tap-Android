package com.example.doanthuctap.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.admin.order.AdminOrdersActivity;
import com.example.doanthuctap.activity.admin.product.AdminProductsActivity;
import com.example.doanthuctap.activity.personality.AccountInformationActivity;
import com.example.doanthuctap.activity.personality.OrdersActivity;
import com.example.doanthuctap.model.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder> {

    private List<Setting> settings = new ArrayList<>();
    private Context context;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public SettingsRecyclerViewAdapter(Context context, List<Setting> settings)
    {
        this.context = context;
        this.settings = settings;
    }

    @Override
    public int getItemViewType(int position) {
        return settings.get(position).getIcon() == 0 ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType == TYPE_ITEM){
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.personality_setting_element, parent, false);
            return new ViewHolder(view, parent);
        }
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.personality_setting_header, parent, false);
        return new ViewHolderSeparator(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if( holder.getItemViewType() == TYPE_ITEM )
        {
            Setting element = settings.get(position);

            holder.leftIcon.setImageResource( element.getIcon() );
            holder.name.setText( element.getTitle() );
            holder.layout.setOnClickListener(view ->{
                switch (element.getId())
                {
                    case "orders":
                        Intent intent = new Intent(context, OrdersActivity.class);
                        intent.putExtra("orderStatus", "");
                        context.startActivity(intent);
                        break;
                    case "darkMode":
                        if( AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES )
                        {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        else
                        {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        break;
                    case "profile":
                        context.startActivity(new Intent(context, AccountInformationActivity.class));
                        break;
                    case "language":
                        break;
                    case "adminOrders":
                        context.startActivity(new Intent(context, AdminOrdersActivity.class));
                        break;
                    case "adminProducts":
                        context.startActivity(new Intent(context, AdminProductsActivity.class));
                        break;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageButton leftIcon;
        private TextView name;
        private LinearLayout layout;
        private ViewGroup parent;

        public ViewHolder(@NonNull View itemView, ViewGroup parent) {
            super(itemView);
            setControl(itemView);
            this.parent = parent;
        }

        private void setControl(View itemView)
        {
            leftIcon = itemView.findViewById(R.id.personalitySettingLeftIcon);
            name = itemView.findViewById(R.id.personalitySettingName);
            layout = itemView.findViewById(R.id.personalitySettingLayout);
        }
    }

    public class ViewHolderSeparator extends ViewHolder
    {
        public ViewHolderSeparator(@NonNull View itemView, ViewGroup parent) {
            super(itemView, parent);
        }
    }
}
