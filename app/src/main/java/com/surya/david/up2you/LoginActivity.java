package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.forgot_pass)
    Button forgotpass;
    @BindView(R.id.btn_loginui)
    Button btnLoginui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.forgot_pass)
    public void onViewClick() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_loginui)
    public void onViewClicked() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
