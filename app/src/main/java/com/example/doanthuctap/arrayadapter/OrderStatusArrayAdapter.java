package com.example.doanthuctap.arrayadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.model.Option;

import java.util.List;
import java.util.prefs.BackingStoreException;

public class OrderStatusArrayAdapter extends BaseAdapter {

    private Context context;
    private List<Option> options;

    public OrderStatusArrayAdapter(Context context, List<Option> options){
        super();
        this.context = context;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.spinner_option_element, viewGroup, false);

        TextView optionName = rootView.findViewById(R.id.optionName);
        ImageView optionAvatar = rootView.findViewById(R.id.optionAvatar);

        String name = options.get(i).getName();
        int avatar = options.get(i).getIcon();


        optionName.setText(name);
        optionAvatar.setImageResource(avatar);


        return rootView;
    }
}
