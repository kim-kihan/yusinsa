package com.example.ygm.MyMenu.Modify;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

public class ModifyPWFragment extends Fragment {

    EditText edtCurrentPW, edtNewPW, edtNewPW_reconfirm;
    TextView[] tvWarning = new TextView[3];
    int[] tvWarning_id = {R.id.textView_warning_currentPW, R.id.textView_warning_newPW, R.id.textView_warning_newPW_reconfirm};
    Button btnComplete;

    boolean[] check = new boolean[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_pw, container, false);

        MainActivity.title.setText("비밀번호 수정");

        edtCurrentPW = v.findViewById(R.id.editTextTextPassword_current);
        edtNewPW = v.findViewById(R.id.editTextTextPassword_new);
        edtNewPW_reconfirm = v.findViewById(R.id.editTextTextPassword_new_reconfirm);
        for (int i = 0; i < 3; i++) {
            tvWarning[i] = v.findViewById(tvWarning_id[i]);
            tvWarning[i].setVisibility(View.GONE);
        }

        for (int i = 0; i < 3; i++) {
            check[i] = false;
        }

        edtCurrentPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = edtCurrentPW.getText().toString();

                if (pw.equals(((User) getActivity().getApplication()).getPW()))
                {
                    tvWarning[0].setText(null);
                    tvWarning[0].setVisibility(View.GONE);
                    check[0] = true;
                }
                else
                {
                    if (pw.equals("")) {
                        tvWarning[0].setText("비밀번호는 필수정보입니다.");
                    } else {
                        tvWarning[0].setText("비밀번호가 일치하지 않습니다.");
                    }
                    tvWarning[0].setVisibility(View.VISIBLE);
                    check[0] = false;
                }

                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNewPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = edtNewPW.getText().toString();

                if (pw.equals("")) {
                    tvWarning[1].setText("비밀번호는 필수정보입니다.");
                    tvWarning[1].setVisibility(View.VISIBLE);
                    check[1] = false;
                } else {
                    tvWarning[1].setText(null);
                    tvWarning[1].setVisibility(View.GONE);
                    check[1] = true;
                }

                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNewPW_reconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = edtNewPW_reconfirm.getText().toString();

                if (pw.equals(edtNewPW.getText().toString()))
                {
                    tvWarning[2].setText(null);
                    tvWarning[2].setVisibility(View.GONE);
                    check[2] = true;
                }
                else
                {
                    if (pw.equals("")) {
                        tvWarning[2].setText("비밀번호 재확인은 필수정보입니다.");
                    } else {
                        tvWarning[2].setText("비밀번호가 일치하지 않습니다.");
                    }
                    tvWarning[2].setVisibility(View.VISIBLE);
                    check[2] = false;
                }

                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnComplete = v.findViewById(R.id.button_modify_pw_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCurrentPW.getText() != edtNewPW.getText())
                {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("비밀번호를 변경하시겠습니까?");
                    dlg.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DB task = new DB(new DB.OnResultListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            if (!result) {
                                                ((User) getActivity().getApplication()).setPW(edtNewPW.getText().toString());
                                                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                                dlg.setTitle("비밀번호 변경이 완료되었습니다.");
                                                dlg.setPositiveButton("확인", null);
                                                dlg.show();
                                            }
                                        }
                                    }, getActivity(), R.id.button_modify_pw_complete);
                                    task.execute("http://15.165.152.15//ResetPW.php", ((User) getActivity().getApplication()).getID(), edtNewPW.getText().toString());
                                }
                            });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
                else
                {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("현재 비밀번호와 신규 비밀번호가 동일합니다.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
            }
        });

        return v;
    }

    public void checkValidation() {
        btnComplete.setEnabled(isValidate());
    }

    public boolean isValidate() {
        return (check[0] && check[1] && check[2]) ? true : false;
    }
}