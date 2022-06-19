package com.example.ygm.LogIn;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import java.util.ArrayList;

public class FindIDFragment extends Fragment {

    ArrayList<String> item = new ArrayList<>();

    RadioGroup rgChoice;
    LinearLayout linearLayoutFindByPhone, linearLayoutFindByEmail;
    EditText edtName, edtPhone, edtEmail;
    Button btnFindID;

    View dialogView;
    TextView tvID;

    int check;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_id, container, false);

        rgChoice = v.findViewById(R.id.check_ID);
        linearLayoutFindByPhone = v.findViewById(R.id.phone_find_id); linearLayoutFindByEmail = v.findViewById(R.id.email_find_id);
        linearLayoutFindByPhone.setVisibility(View.INVISIBLE);
        edtName = v.findViewById(R.id.name_fragment_find_id);
        edtPhone = v.findViewById(R.id.phone_fragment_find_id);
        edtEmail = v.findViewById(R.id.email_fragment_find_id);

        rgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                check = i;
                switch (i) {
                    case R.id.check_phoneNum_ID:
                        linearLayoutFindByPhone.setVisibility(View.VISIBLE);
                        linearLayoutFindByEmail.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.check_email_ID:
                        linearLayoutFindByPhone.setVisibility(View.INVISIBLE);
                        linearLayoutFindByEmail.setVisibility(View.VISIBLE);
                }
            }
        });

        btnFindID = v.findViewById(R.id.find_id_fragment_find_id);

        btnFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == R.id.check_phoneNum_ID) {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                if (item.size() != 0) {
                                    dialogView = View.inflate(getActivity(), R.layout.dialog_find_id, null);
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                    dlg.setTitle("사용자 ID 정보");
                                    dlg.setView(dialogView);
                                    tvID = dialogView.findViewById(R.id.editTextTextPersonName3);
                                    tvID.setText(item.get(0));
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(view).navigate(R.id.action_findIDFragment_to_logInHomeFragment);
                                        }
                                    });
                                    AlertDialog dialog = dlg.create();
                                    dialog.show();
                                    return;
                                } else {
                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "이름 또는 핸드폰 번호를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                    toast1.show();
                                }
                            }
                        }
                    }, getActivity(), R.id.check_phoneNum_ID, item);
                    task.execute("http://15.165.152.15//FindIDP.php", edtName.getText().toString(), edtPhone.getText().toString());
                } else {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                if (item.size() != 0) {
                                    dialogView = View.inflate(getActivity(), R.layout.dialog_find_id, null);
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                                    dlg.setTitle("사용자 ID 정보");
                                    dlg.setView(dialogView);
                                    tvID = dialogView.findViewById(R.id.editTextTextPersonName3);
                                    tvID.setText(item.get(0));
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(view).navigate(R.id.action_findIDFragment_to_logInHomeFragment);
                                        }
                                    });
                                    AlertDialog dialog = dlg.create();
                                    dialog.show();
                                    return;
                                } else {
                                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "E-mail address를 다시 입력하세요.", Toast.LENGTH_SHORT);
                                    toast1.show();
                                }
                            }
                        }
                    }, getActivity(), R.id.check_email_ID, item);
                    task.execute("http://15.165.152.15//FindIDE.php", edtEmail.getText().toString());
                }

            }
        });

        return v;
    }
}