package com.example.ygm.MyMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class WithdrawalFragment extends Fragment {

    EditText edtPW, edtPWConfirm;
    Button btnWithdrawal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_withdrawal, container, false);

        edtPW = v.findViewById(R.id.editTextPW_withdrawal);
        edtPWConfirm = v.findViewById(R.id.editTextPW_confirm_withdrawal);
        btnWithdrawal = v.findViewById(R.id.button_withdrawal);

        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw, pwConfirm;
                pw = edtPW.getText().toString();
                pwConfirm = edtPWConfirm.getText().toString();

                if (pw == null) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("패스워드를 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else if(pwConfirm == null) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("패스워드를 한번더 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else {
                    if (pw.equals(pwConfirm)) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                        dlg.setTitle("정말로 탈퇴 하시겠습니까?");
                        dlg.setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DB task = new DB(new DB.OnResultListener() {
                                            @Override
                                            public void onComplete(boolean result) {
                                                if (!result) {
                                                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                                    dlg.setTitle("회원탈퇴가 완료되었습니다.");
                                                    dlg.setPositiveButton("확인", null);
                                                    dlg.show();
                                                    ((User) getActivity().getApplication()).setUID(null);
                                                    getActivity().finish();
                                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                                    // 홈 화면으로
                                                }
                                            }
                                        }, getActivity(), R.id.button_withdrawal);
                                        task.execute("http://15.165.152.15//RemoveUser.php", ((User) getActivity().getApplication()).getUID());
                                    }
                                });
                        dlg.setNegativeButton("취소", null);
                        dlg.show();
                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                        dlg.setTitle("패스워드가 일치하지 않습니다.");
                        dlg.setPositiveButton("확인", null);
                        dlg.show();
                    }
                }
            }
        });

        return v;
    }
}