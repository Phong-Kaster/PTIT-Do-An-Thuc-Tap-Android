package com.example.doanthuctap.recyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
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
                Toast.makeText(context,"settings", Toast.LENGTH_SHORT).show();
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
