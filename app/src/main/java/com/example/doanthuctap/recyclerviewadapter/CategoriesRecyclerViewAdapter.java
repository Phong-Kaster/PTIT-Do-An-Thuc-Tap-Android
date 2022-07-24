package com.example.doanthuctap.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.home.CategoryFragment;
import com.example.doanthuctap.activity.home.HomeFragment;
import com.example.doanthuctap.model.Category;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Category> objects = new ArrayList<>();
    private int selectedItem = 0;
    private callbacks callback;

    public CategoriesRecyclerViewAdapter(Context context, List<Category> objects, callbacks callback)
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
                .inflate(R.layout.categories_recycler_view_adapter_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = objects.get(position);
        String name = category.getName();

        /*Step 1 - set default color for non-selected item*/
        /*default background and text for element*/
        int colorDefaultBackground = ContextCompat.getColor(context,R.color.colorBackground);
        int colorDefaultName = ContextCompat.getColor(context,R.color.colorTextBlack);

        holder.parentLayout.setBackgroundColor(colorDefaultBackground);
        holder.name.setTextColor(colorDefaultName);


        /*Step 2 - set default color for selected item*/
        /*default background and text for selected element*/
        int colorSelectedBackground = ContextCompat.getColor(context,R.color.colorBlue);
        int colorSelectedName =  ContextCompat.getColor(context,R.color.white);
        if( selectedItem == position)
        {
            holder.parentLayout.setBackgroundColor(colorSelectedBackground);
            holder.name.setTextColor(colorSelectedName);
        }


        /*Step 3 - set up value to components*/
        holder.name.setText(name);
        holder.parentLayout.setOnClickListener(view -> {

            /*reset color for new selected item & previous item*/
            int previousSelectItem = selectedItem;
            selectedItem = position;

            notifyItemChanged(previousSelectItem);
            notifyItemChanged(selectedItem);

            callback.onItemClicked(category.getId());
        });


    }

    @Override
    public int getItemCount() {
        return objects.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout parentLayout;
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryName);
            parentLayout = itemView.findViewById(R.id.categoryParentLayout);
        }
    }

    public interface callbacks{
        void onItemClicked(int categoryId);
    }
}
