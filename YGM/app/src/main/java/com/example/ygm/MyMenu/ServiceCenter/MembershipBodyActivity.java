package com.example.ygm.MyMenu.ServiceCenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

public class MembershipBodyActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_body);

        tvTitle = findViewById(R.id.textView_title_activity_membershipBody);
        ivBody = findViewById(R.id.imageView_membership_body);

        tvTitle.setText(getIntent().getStringExtra("membershipTitle"));
        Glide.with(this).load(getIntent().getStringExtra("membershipBody")).override(Target.SIZE_ORIGINAL).into(ivBody);
    }
}