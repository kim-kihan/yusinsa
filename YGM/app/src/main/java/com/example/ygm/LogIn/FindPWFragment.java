package com.example.ygm.LogIn;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import java.util.ArrayList;

public class FindPWFragment extends Fragment {

    ArrayList<String> item = new ArrayList<>();

    LinearLayout linearLayoutFindByPhone, linearLayoutFindByEmail;

    RadioGroup rgChoice;
    EditText edtID1, edtID2, edtName, edtPhone, edtEmail, edtPW, edtPWConfirm;
    Button btnFindPW;

    View dialogView;
    int check, chk = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_pw, container, false);

        rgChoice = v.findViewById(R.id.check_PW);
        linearLayoutFindByPhone = v.findViewById(R.id.phone_find_pw);  linearLayoutFindByEmail = v.findViewById(R.id.email_find_pw);
        linearLayoutFindByEmail.setVisibility(View.INVISIBLE);
        edtID1 = v.findViewById(R.id.id_fragment_find_pw);
        edtID2 = v.findViewById(R.id.id_fragment_find_pw_email);
        edtName = v.findViewById(R.id.name_fragment_find_pw);
        edtPhone = v.findViewById(R.id.phone_fragment_find_pw);
        edtEmail = v.findViewById(R.id.email_fragment_find_pw);

        rgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                check = i;
                switch (i) {
                    case R.id.check_phoneNum_PW:
                        linearLayoutFindByPhone.setVisibility(View.VISIBLE);
                        linearLayoutFindByEmail.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.check_email_PW:
                        linearLayoutFindByEmail.setVisibility(View.VISIBLE);
                        linearLayoutFindByPhone.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnFindPW = v.findViewById(R.id.find_pw_fragment_find_pw);

        btnFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == R.id.check_phoneNum_PW) {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                if (item.get(chk++).toString().equals("1")) {
                                    dialogView = View.inflate(getActivity(), R.layout.dialog_find_pw, null);
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                    dlg.setTitle("사용자 PW 정보");
                                    dlg.setView(dialogView);
                                    edtPW = dialogView.findViewById(R.id.editTextTextPersonName3);
                                    edtPWConfirm = dialogView.findViewById(R.id.editTextTextPersonName4);
                                    dlg.setPositiveButton("확인", null);
                                    AlertDialog dialog = dlg.create();
                                    dialog.show();
                                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (edtPW.getText().toString().equals(edtPWConfirm.getText().toString())) {
                                                if(!edtPW.getText().toString().equals("")){
                                                    DB task = new DB(new DB.OnResultListener() {
                                                        @Override
                                                        public void onComplete(boolean result) {
                                                            if (!result) {
                                                                LogInActivity.loginNavController.navigate(R.id.action_findPWFragment_to_logInHomeFragment);
                                                                Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT);
                                                                toast1.show();
                                                                dialog.dismiss();
                                                            }
                                                        }
                                                    }, getActivity(), R.id.find_pw_fragment_find_pw);
                                                    task.execute("http://15.165.152.15//ResetPW.php", edtID1.getText().toString(), edtPW.getText().toString());
                                                }else{
                                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호를 다시 설정해주십시오.", Toast.LENGTH_SHORT);
                                                    toast1.show();
                                                }
                                            } else {
                                                Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                                toast1.show();
                                            }
                                        }
                                    });
                                    return;
                                } else {
                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "아이디, 이름 또는 핸드폰 번호를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                    toast1.show();
                                }
                            }
                        }
                    }, getActivity(), R.id.check_phoneNum_PW, item);
                    task.execute("http://15.165.152.15//CheckPWP.php", edtName.getText().toString(), edtID1.getText().toString(), edtPhone.getText().toString());
                } else {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                if (item.get(chk++).toString().equals("1")) {
                                    dialogView = View.inflate(getActivity(), R.layout.dialog_find_pw, null);
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                    dlg.setTitle("사용자 PW 정보");
                                    dlg.setView(dialogView);
                                    edtPW = dialogView.findViewById(R.id.editTextTextPersonName3);
                                    edtPWConfirm = dialogView.findViewById(R.id.editTextTextPersonName4);
                                    dlg.setPositiveButton("확인", null);
                                    AlertDialog dialog = dlg.create();
                                    dialog.show();
                                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (edtPW.getText().toString().equals(edtPWConfirm.getText().toString())) {
                                                if(!edtPW.getText().toString().equals("")){
                                                    DB task = new DB(new DB.OnResultListener() {
                                                        @Override
                                                        public void onComplete(boolean result) {
                                                            if (!result) {
                                                                Navigation.findNavController(view).navigate(R.id.action_findPWFragment_to_logInHomeFragment);
                                                                Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT);
                                                                toast1.show();
                                                                dialog.dismiss();
                                                            }
                                                        }
                                                    }, getActivity(), R.id.find_pw_fragment_find_pw);
                                                    task.execute("http://15.165.152.15//ResetPW.php", edtID2.getText().toString(), edtPW.getText().toString());
                                                }else{
                                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호를 다시 설정해주십시오.", Toast.LENGTH_SHORT);
                                                    toast1.show();
                                                }
                                            } else {
                                                Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "비밀번호를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                                toast1.show();
                                            }
                                        }
                                    });
                                    return;
                                } else {
                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "아이디 또는 E-mail address를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                    toast1.show();
                                }
                            }
                        }
                    }, getActivity(), R.id.check_email_PW, item);
                    task.execute("http://15.165.152.15//CheckPWE.php", edtEmail.getText().toString(), edtID2.getText().toString());
                }
            }
        });

        return v;
    }
}