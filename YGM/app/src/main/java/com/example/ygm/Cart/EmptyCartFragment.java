package com.example.ygm.Cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.ygm.MainActivity;
import com.example.ygm.R;

import static android.app.Activity.RESULT_OK;

public class EmptyCartFragment extends Fragment {

    Button btnFavorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_empty_cart, container, false);

        CartActivity.tvSelectAll.setText("선택 불가");
        CartActivity.togSelectAll.setChecked(false);
        CartActivity.togSelectAll.setClickable(false);
        CartActivity.tvDeleteSelect.setVisibility(View.GONE);
        CartActivity.tvDeleteSoldOut.setClickable(false);

        btnFavorite = v.findViewById(R.id.button_look_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            }
        });

        return v;
    }
}
