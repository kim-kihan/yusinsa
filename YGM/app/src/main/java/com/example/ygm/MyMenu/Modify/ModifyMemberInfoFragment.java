package com.example.ygm.MyMenu.Modify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ygm.User;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

public class ModifyMemberInfoFragment extends Fragment {

    TextView tvID, tvUname, tvPW, tvPhone, tvBirthdate, tvBodyInfo;
    Button btnModifyPW, btnModifyPhone, btnModifyBodyInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_member_info, container, false);

        MainActivity.navView.setVisibility(View.GONE);
        MainActivity.title.setText("회원정보 수정");

        tvID = v.findViewById(R.id.textView_id);
        tvUname = v.findViewById(R.id.textView_name);
        tvPW = v.findViewById(R.id.textView_pw);
        tvPhone = v.findViewById(R.id.textView_number);
        tvBirthdate = v.findViewById(R.id.textView_birthdate);
        tvBodyInfo = v.findViewById(R.id.textView_height_weight);

        User user = (User) getActivity().getApplication();
        tvID.setText(user.getID());
        tvUname.setText(user.getUname());
        tvPW.setText(user.getPW());
        tvPhone.setText(user.getPhone());
        tvBirthdate.setText(user.getBirthdate());
        tvBodyInfo.setText(String.format("키 %scm 몸무게 %skg", user.getHeight(), user.getWeight()));

        btnModifyPW = v.findViewById(R.id.button_modify_pw);
        btnModifyPW.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_modifyMemberInfoFragment_to_modifyPWFragment));
        btnModifyPhone = v.findViewById(R.id.button_modify_number);
        btnModifyPhone.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_modifyMemberInfoFragment_to_modifyNumberFragment));
        btnModifyBodyInfo = v.findViewById(R.id.button_modify_height_weight);
        btnModifyBodyInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_modifyMemberInfoFragment_to_modifyHeightWeightFragment));

        return v;
    }
}