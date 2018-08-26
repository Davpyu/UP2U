package com.surya.david.up2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.submit_new_password)
    Button newpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.submit_new_password)
    public void onViewClick(){
        Intent intent = new Intent(this, ResetSuccessActivity.class);
        startActivity(intent);
    }
}
