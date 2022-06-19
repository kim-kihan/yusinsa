package com.example.ygm.MyMenu.Inquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.LogIn.UserItem;
import com.example.ygm.R;
import com.example.ygm.User;

import java.util.ArrayList;

public class OneToOneInquireActivity extends AppCompatActivity {

    Spinner type;

    TextView tvName, tvPhone, tvEmail;
    EditText edtTitle, edtBody;
    Button btnCancel, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_inquire);

        Activity activity = this;

        setResult(RESULT_CANCELED);

        type = findViewById(R.id.spinner_onetoone_inquire_type);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_onetoone_inquire_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(spinnerAdapter);

        tvName = findViewById(R.id.textView_name_onetoone_inquire);
        tvPhone = findViewById(R.id.textView_phone_onetoone_inquire);
        tvEmail = findViewById(R.id.textView_email_onetoone_inquire);

        User user = (User) getApplication();
        tvName.setText(user.getUname());
        String number = user.getPhone();
        tvPhone.setText(number.substring(0, 3) + "-" + number.substring(3, 7) + "-" + number.substring(7, 11));
        tvEmail.setText(user.getEmail());

        edtTitle = findViewById(R.id.editText_title_onetotone_inquire);
        edtBody = findViewById(R.id.editText_body_onetotone_inquire);

        btnCancel = findViewById(R.id.button_cancel_onetoone_inquire);
        btnRegister = findViewById(R.id.button_register_onetoone_inquire);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.getSelectedItem().toString().equals("선택")) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
                    dlg.setTitle("문의유형을 선택해 주십시오.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else {
                    DB task = new DB(new DB.OnResultListener() {
                        ArrayList<UserItem> item = new ArrayList<>();

                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
                                dlg.setTitle("1:1문의가 등록되었습니다.");
                                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });
                                dlg.show();
                            }
                        }
                    }, activity, R.layout.activity_one_to_one_inquire);
                    task.execute("http://15.165.152.15//InsertQnA.php", user.getUID(), edtTitle.getText().toString(), edtBody.getText().toString(), type.getSelectedItem().toString());
                }
            }
        });
    }
}