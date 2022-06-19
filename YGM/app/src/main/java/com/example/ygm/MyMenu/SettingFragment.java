package com.example.ygm.MyMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.LogIn.RegistrationActivity;
import com.example.ygm.User;
import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment {

    TextView tvID, tvLogInOut;
    Switch pushAlarm;
    TextView tvWithdrawal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        User user = (User) getActivity().getApplication();

        MainActivity.navView.setVisibility(View.GONE);
        MainActivity.title.setText("설정");

        tvID = v.findViewById(R.id.textView_user_id_setting);
        tvLogInOut = v.findViewById(R.id.button_login_logout);
        pushAlarm = v.findViewById(R.id.switch_push_alarm_setting);
        tvWithdrawal = v.findViewById(R.id.button_withdrawal_setting);

        if (((User) getActivity().getApplication()).getUID() == null)
        {
            tvID.setText("로그인 해주세요");
            tvLogInOut.setText("로그인");
            tvLogInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), LogInActivity.class), 0);
                }
            });

            pushAlarm.setVisibility(View.GONE);
            tvWithdrawal.setVisibility(View.GONE);
        }
        else
        {
            tvID.setText(user.getID());
            tvLogInOut.setText("로그아웃");
            tvLogInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("로그아웃 하시겠습니까?");
                    dlg.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user.setUID(null);
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();
                                }
                            });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
            });

            pushAlarm.setChecked(user.isPushAlarm());
            pushAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                user.setPushAlarm(pushAlarm.isChecked());
                            }
                        }
                    }, getActivity(), R.id.switch_push_alarm_setting);
                    if (pushAlarm.isChecked()) {
                        task.execute("http://15.165.152.15//PushOn.php", user.getUID());
                    } else {
                        task.execute("http://15.165.152.15//PushOff.php", user.getUID());
                    }
                }
            });
        }

        tvWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_settingFragment_to_withdrawalFragment);
            }
        });

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            MainActivity.navView.setSelectedItemId(R.id.myMenuFragment);
        }
    }
}