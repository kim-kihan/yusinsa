package com.example.ygm.MyMenu.Modify;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.util.ArrayList;


public class ModifyNumberFragment extends Fragment {

    EditText edtPhone;
    Button btnAuthentication;
    Button btnComplete;

    String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_number, container, false);

        MainActivity.title.setText("휴대전화 수정");

        edtPhone = v.findViewById(R.id.editTextPhone_modify_phone);
        btnAuthentication = v.findViewById(R.id.button_authentication);
        btnComplete = v.findViewById(R.id.button_modify_phone_complete);

        btnAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("해당번호로 인증하시겠습니까?");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            ArrayList<String> checkPhone = new ArrayList<>();
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                number = edtPhone.getText().toString();
                                DB task = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            if (checkPhone.get(0).equals("0")) {
                                                btnComplete.setEnabled(true);
                                                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                                dlg.setTitle("인증이 완료되었습니다.");
                                                dlg.setPositiveButton("확인", null);
                                                dlg.show();
                                            } else {
                                                btnComplete.setEnabled(false);
                                                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                                dlg.setTitle("해당 번호로 이미 가입된 계정이 있습니다.");
                                                dlg.setPositiveButton("확인", null);
                                                dlg.show();
                                            }
                                        }
                                    }
                                }, getActivity(), R.id.button_authentication, checkPhone);
                                task.execute("http://15.165.152.15//CheckPhone.php", number);
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            ((User) (getActivity().getApplication())).setPhone(number);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                            dlg.setTitle("휴대전화 번호가 수정되었습니다.");
                            dlg.setPositiveButton("확인", null);
                            dlg.show();
                        }
                    }
                }, getActivity(), R.id.button_modify_phone_complete);
                task.execute("http://15.165.152.15//UpdatePhone.php", ((User) getActivity().getApplication()).getUID(), number);
            }
        });

        return v;
    }
}