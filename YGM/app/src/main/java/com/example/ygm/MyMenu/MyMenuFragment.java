package com.example.ygm.MyMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ygm.Cart.CartActivity;
import com.example.ygm.Home.PushAlarmActivity;
import com.example.ygm.User;
import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.MainActivity;
import com.example.ygm.MyMenu.ServiceCenter.FAQActivity;
import com.example.ygm.MyMenu.ServiceCenter.MembershipActivity;
import com.example.ygm.MyMenu.ServiceCenter.NoticeActivity;
import com.example.ygm.R;

import static android.app.Activity.RESULT_OK;

public class MyMenuFragment extends Fragment {

    TextView tvNotice, tvFAQ, tvMembership;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_menu, container, false);

        MainActivity.navView.setVisibility(View.VISIBLE);
        MainActivity.title.setText(null);
        setHasOptionsMenu(true);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (((User) getActivity().getApplication()).getUID() == null) {
            transaction.replace(R.id.frameLayout_member, new MyMenuNonmemberFragment()).commit();
        } else {
            transaction.replace(R.id.frameLayout_member, new MyMenuMemberFragment()).commit();
        }

        tvNotice = v.findViewById(R.id.button_notice);
        tvFAQ = v.findViewById(R.id.button_faq);
        tvMembership = v.findViewById(R.id.button_membership);

        tvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NoticeActivity.class));
            }
        });
        tvFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FAQActivity.class));
            }
        });
        tvMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MembershipActivity.class));
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_mymenu_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settingFragment :
                MainActivity.navController.navigate(R.id.action_myMenuFragment_to_settingFragment);
                break;
            case R.id.cartFragment :
                if (((User) getActivity().getApplication()).getUID() == null) {
                    startActivityForResult(new Intent(getActivity(), LogInActivity.class), 2);
                } else {
                    startActivityForResult(new Intent(getActivity(), CartActivity.class), 22222);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:                     // Login For Cart
                if (resultCode == RESULT_OK) {
                    startActivityForResult(new Intent(getActivity(), CartActivity.class), 22222);
                }
                break;
            case 22222:
                if (resultCode == RESULT_OK) {
                    MainActivity.navView.setSelectedItemId(R.id.favoriteFragment);
                }
        }
    }
}