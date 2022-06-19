package com.example.ygm.LogIn;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.ygm.LogIn.LogInActivity.loginNavController;

public class LogInHomeFragment extends Fragment {

    ArrayList<String> resultItem = new ArrayList<>();
    ArrayList<UserItem> userItem = new ArrayList<>();

    ImageView ivLogo;
    TextView tvRegistration, tvFindID, tvFindPW;
    EditText edtID, edtPW;
    Button btnLogin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in_home, container, false);

        ivLogo = v.findViewById(R.id.image_fragment_log_in_home);
        tvRegistration = v.findViewById(R.id.registration_fragment_log_in_home);
        tvFindID = v.findViewById(R.id.find_id_fragment_log_in_home);
        tvFindPW = v.findViewById(R.id.find_pw_fragment_log_in_home);
        edtID = v.findViewById(R.id.id_fragment_log_in_home);
        edtPW = v.findViewById(R.id.pw_fragment_log_in_home);
        btnLogin = v.findViewById(R.id.login_fragment_log_in_home);

        ivLogo.setImageResource(R.drawable.login_logo);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            if (resultItem.get(0).equals("1")) {
                                DB task = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            User user = (User) getActivity().getApplication();
                                            UserItem tmp = userItem.get(0);

                                            user.setUID(tmp.getUID());
                                            user.setID(tmp.getID());
                                            user.setPW(tmp.getPW());
                                            user.setUname(tmp.getUname());
                                            user.setPhone(tmp.getPhone());
                                            user.setEmail(tmp.getEmail());
                                            user.setBirthdate(tmp.getBirthdate());
                                            user.setHeight(tmp.getHeight());
                                            user.setWeight(tmp.getWeight());
                                            user.setExp(tmp.getExp());
                                            user.setPushAlarm(tmp.isPushAlarm());
                                            user.setPoint(tmp.getPoint());
                                            user.setRegdate(tmp.getRegdate());

                                            getActivity().setResult(RESULT_OK);
                                            getActivity().finish();
                                        }
                                    }
                                }, getActivity(), R.id.pw_fragment_log_in_home, userItem);
                                task.execute("http://15.165.152.15//GetUser.php", edtID.getText().toString());
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "아이디 혹은 비밀번호를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                                resultItem.remove(0);
                            }
                        }
                    }
                }, getActivity(), R.id.login_fragment_log_in_home, resultItem);
                task.execute("http://15.165.152.15//Login.php", edtID.getText().toString(), edtPW.getText().toString());
            }
        });

        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
            }
        });
        tvFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginNavController.navigate(R.id.action_logInHomeFragment_to_findIDFragment);
            }
        });
        tvFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginNavController.navigate(R.id.action_logInHomeFragment_to_findPWFragment);
            }
        });

        return v;
    }
}