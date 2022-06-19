package com.example.ygm.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.ygm.R;

public class LogInActivity extends AppCompatActivity {

    public static NavController loginNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setResult(RESULT_CANCELED);

        loginNavController = Navigation.findNavController(this, R.id.nav_host_fragment_login);
    }
}