package com.surya.david.up2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetSuccessActivity extends AppCompatActivity {
    @BindView(R.id.btn_back_login)
    Button backlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_back_login)
    public void onViewClick(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
