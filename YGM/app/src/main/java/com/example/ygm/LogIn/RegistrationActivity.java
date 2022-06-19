package com.example.ygm.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    String idPattern = "^[a-zA-Z0-9]*$";
    String namePattern = "^[가-힣]*$";
    String emailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$";
    String phonePattern = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
    String birthdatePattern = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    EditText edtID, edtPW, edtPWConfirm, edtName, edtEmail, edtPhone, edtBirthdate;
    ArrayList<String> checkID, checkEmail, checkPhone;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtID = findViewById(R.id.id_fragment_registration);
        edtPW = findViewById(R.id.pw_fragment_registration);
        edtPWConfirm = findViewById(R.id.pw_check_fragment_registration);
        edtName = findViewById(R.id.name_fragment_registration);
        edtEmail = findViewById(R.id.email_fragment_registration);
        edtPhone = findViewById(R.id.phone_fragment_registration);
        edtBirthdate = findViewById(R.id.birth_fragment_registration);
        btnRegister = findViewById(R.id.done_fragment_registration);

        Activity activity = this;

        checkID = new ArrayList<>();
        checkEmail = new ArrayList<>();
        checkPhone = new ArrayList<>();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtID.getText().toString().matches(idPattern)) {
                    Toast.makeText(getApplicationContext(), "[ID]잘못된 입력입니다. ID는 숫자와 영어의 조합만 사용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtPW.getText().toString().equals(edtPWConfirm.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "[PW]비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtName.getText().toString().matches(namePattern)) {
                    Toast.makeText(getApplicationContext(), "[이름]잘못된 입력입니다. 이름은 한글만 입력이 가능합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtEmail.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "[Email]잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtPhone.getText().toString().matches(phonePattern)) {
                    Toast.makeText(getApplicationContext(), "[전화번호]잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtBirthdate.getText().toString().matches(birthdatePattern)) {
                    Toast.makeText(getApplicationContext(), "[생년월일]잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DB task1 = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            if (checkID.get(0).equals("0")) {
                                checkID.remove(0);
                                DB task2 = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            if (checkEmail.get(0).equals("0")) {
                                                checkEmail.remove(0);
                                                DB task3 = new DB(new DB.OnResultListener() {
                                                    @Override
                                                    public void onComplete(boolean result) {
                                                        if (!result) {
                                                            if (checkPhone.get(0).equals("0")) {
                                                                checkPhone.remove(0);
                                                                DB task = new DB(new DB.OnResultListener() {
                                                                    @Override
                                                                    public void onComplete(boolean result) {
                                                                        if (!result) {
                                                                            Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_logInHomeFragment);
                                                                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }, activity, R.id.fragment_registration);
                                                                task.execute("http://15.165.152.15//Registration.php", edtID.getText().toString(), edtPW.getText().toString(), edtName.getText().toString(), edtPhone.getText().toString(), edtEmail.getText().toString(), edtBirthdate.getText().toString());
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "이미 존재하는 휴대전화번호입니다.", Toast.LENGTH_SHORT).show();
                                                                checkPhone.remove(0);
                                                            }
                                                        }
                                                    }
                                                }, activity, R.id.phone_fragment_registration, checkPhone);
                                                task3.execute("http://15.165.152.15//CheckPhone.php", edtPhone.getText().toString());
                                            } else {
                                                Toast.makeText(getApplicationContext(), "이미 존재하는 Email입니다.", Toast.LENGTH_SHORT).show();
                                                checkEmail.remove(0);
                                            }
                                        }
                                    }
                                }, activity, R.id.email_fragment_registration, checkEmail);
                                task2.execute("http://15.165.152.15//CheckMail.php", edtEmail.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                checkID.remove(0);
                            }
                        }
                    }
                }, activity, R.id.id_fragment_registration, checkID);
                task1.execute("http://15.165.152.15//CheckID.php", edtID.getText().toString());
            }
        });
    }
}