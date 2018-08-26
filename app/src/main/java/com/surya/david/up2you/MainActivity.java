package com.surya.david.up2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
@BindView(R.id.btn_login)
TextView btnlogin;
@BindView(R.id.btn_register)
TextView btnreg;
@BindView(R.id.btn_loginasguest)
TextView btnloginguest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_login)
    public void onViewClicked(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_register)
    public void onClick(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_loginasguest)
    public void onClicked(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
