package com.example.ygm.MyMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ygm.MainActivity;
import com.example.ygm.MyMenu.OrderList.OrderListActivity;
import com.example.ygm.User;
import com.example.ygm.MyMenu.Inquiry.OneToOneInquiryActivity;
import com.example.ygm.MyMenu.Inquiry.ProductInquiryActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;

public class MyMenuMemberFragment extends Fragment {

    TextView tvUname, tvLV, tvRegdate, tvGradeInfo, tvMysize, tvPoint;
    Button btnOrderList;
    TextView tvProductInquiry, tvOneToOneInquiry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_menu_member, container, false);

        tvUname = v.findViewById(R.id.textView_user_name);
        tvLV = v.findViewById(R.id.textView_level);
        tvRegdate = v.findViewById(R.id.textView_regdate);
        tvGradeInfo = v.findViewById(R.id.textView_grade_info);
        tvMysize = v.findViewById(R.id.textView_my_size_my_menu_member);
        tvPoint = v.findViewById(R.id.textView_point_mymenu_member);

        User user = (User) getActivity().getApplication();

        tvPoint.setText(new DecimalFormat("###,###,###,###").format(user.getPoint()));

        tvMysize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_myMenuFragment_to_modifyHeightWeightFragment);
            }
        });

        tvUname.setText(user.getUname());
        String level = "LV." + user.getLV() + " " + user.getGrade();
        tvLV.setText(level);
        tvRegdate.setText(String.format("가입일 : %s", user.getRegdate()));

        String[] grade = {"뉴비", "루키", "멤버", "브론즈", "실버", "골드", "플래티넘", "다이아몬드"};
        int[] requiredEXP = {2000, 10000, 100000, 200000, 500000, 1000000, 2000000 };
        if (user.getLV() < 8) {
            tvGradeInfo.setText(user.getID() + "님 회원등급\n\n다음 등급인 " + grade[user.getLV()] + "까지 " + new DecimalFormat("###,###,###,###").format(requiredEXP[user.getLV() - 1] - user.getExp()) + "점 남았습니다.");
        } else {
            tvGradeInfo.setText("최고 등급입니다.");
        }

        tvUname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_myMenuFragment_to_modifyMemberInfoFragment);
            }
        });

        btnOrderList = v.findViewById(R.id.button_all_orderlist_mymenu);
        btnOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderListActivity.class));
            }
        });

        tvProductInquiry = v.findViewById(R.id.button_product_inquiry);
        tvOneToOneInquiry = v.findViewById(R.id.button_onetoone_inquiry);

        tvProductInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductInquiryActivity.class));
            }
        });
        tvOneToOneInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OneToOneInquiryActivity.class));
            }
        });

        return v;
    }
}