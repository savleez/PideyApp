package com.pideyapp.pideyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btnLogin = (Button) findViewById(R.id.btnLogin);
    }


    public void btn_login(View view) {
        Intent intentLogin = new Intent(this, DashboardActivity.class);
        startActivity(intentLogin);
    }

    public void btn_register(View view) {
        Intent intentRegister = new Intent(this, RegisterMenuActivity.class);
        startActivity(intentRegister);
    }
}