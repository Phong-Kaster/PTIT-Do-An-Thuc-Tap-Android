package com.example.doanthuctap.activity.product;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.doanthuctap.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author Phong-Kaster
 * this is a class extends BottomSheetDialogFragment. Elementary, it acts as Fragment
 * when users click "add to cart" button in Product Information Activity
 * it will show up from the bottom to let users select product's quantity that they want
 */
public class AddToCartModalBottomSheet extends BottomSheetDialogFragment {

    private ModalBottomSheetListener listener;

    private AppCompatImageButton buttonAdd;
    private AppCompatImageButton buttonMinus;
    private TextView txtQuantity;
    private int quantity = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_bottom_sheet_add_to_cart, container, false);

        setControl(view);
        setEvent();
        return view;
    }

    public interface ModalBottomSheetListener
    {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ModalBottomSheetListener) context;
    }

    private void setControl(View view) {
        buttonAdd = view.findViewById(R.id.addToCartButtonAdd);
        buttonMinus = view.findViewById(R.id.addToCartButtonMinus);
        txtQuantity = view.findViewById(R.id.addToCartQuantity);

        txtQuantity.setText("0");
    }

    private void setEvent() {
        buttonAdd.setOnClickListener(view->{
            Toast.makeText(getActivity(), "Hallo", Toast.LENGTH_SHORT).show();
            quantity++;
            txtQuantity.setText(String.valueOf(quantity));
        });

        buttonAdd.setOnClickListener(view->{
            if(quantity > 1)
            {
                quantity--;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });
    }
}
