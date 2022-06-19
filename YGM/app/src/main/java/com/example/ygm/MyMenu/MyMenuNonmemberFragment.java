package com.example.ygm.MyMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.LogIn.RegistrationActivity;
import com.example.ygm.R;

import static android.app.Activity.RESULT_OK;

public class MyMenuNonmemberFragment extends Fragment {

    Button btnLogin, btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_menu_nonmember, container, false);

        btnLogin = v.findViewById(R.id.button_login);
        btnRegister = v.findViewById(R.id.button_registration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), LogInActivity.class), 0);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
            }
        });

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout_member, new MyMenuMemberFragment()).commit();
        }
    }
}