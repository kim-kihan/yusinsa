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

public class ModifyHeightWeightFragment extends Fragment {

    EditText edtHeight, edtWeight;
    Button btnReset, btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_height_weight, container, false);

        User user = (User) getActivity().getApplication();

        MainActivity.navView.setVisibility(View.GONE);
        MainActivity.title.setText("마이사이즈");

        edtHeight = v.findViewById(R.id.editTextHeight_modify_height_weight);
        edtWeight = v.findViewById(R.id.editTextWeight_modify_height_weight);
        btnReset = v.findViewById(R.id.button_reset_modify_height_weight);
        btnSave = v.findViewById(R.id.button_save_modify_height_weight);

        edtHeight.setText((user.getHeight() == 0) ? null : Integer.toString(user.getHeight()));
        edtWeight.setText((user.getWeight() == 0) ? null : Integer.toString(user.getWeight()));

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("등록하신 사이즈\n정보를 삭제하시겠습니까?");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DB task = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            user.setHeight(0);
                                            user.setWeight(0);
                                            edtHeight.setText(null);
                                            edtWeight.setText(null);
                                        }
                                    }
                                }, getActivity(), R.id.fragment_modify_height_weight);
                                task.execute("http://15.165.152.15//UpdateBody.php", user.getUID(), "0", "0");
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                return;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h, w;

                try {
                    h = Integer.parseInt(edtHeight.getText().toString());
                } catch(NumberFormatException e) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("키를 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                    return;
                }

                try {
                    w = Integer.parseInt(edtWeight.getText().toString());
                } catch(NumberFormatException e) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("몸무게를 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                    return;
                }

                if (h < 100 || 220 < h) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("키는 최소 100부터 220까지\n입력할 수 있습니다.\n다시 입력해 주세요");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else if(w < 30 || 150 < w) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("몸무게는 최소 30부터 150까지\n입력할 수 있습니다.\n다시 입력해 주세요");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                user.setHeight(h);
                                user.setWeight(w);
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                dlg.setTitle("사이즈가 정상적으로 저장되었습니다.");
                                dlg.setPositiveButton("확인", null);
                                dlg.show();
                            }
                        }
                    }, getActivity(), R.id.fragment_modify_height_weight);
                    task.execute("http://15.165.152.15//UpdateBody.php", user.getUID(), Integer.toString(h), Integer.toString(w));
                }
            }
        });

        return v;
    }
}