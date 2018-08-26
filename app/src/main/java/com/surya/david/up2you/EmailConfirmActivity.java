package com.surya.david.up2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailConfirmActivity extends AppCompatActivity {
    @BindView(R.id.submit_code)
    Button submitcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirm);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.submit_code)
    public void onViewClicked(){
        Intent intent = new Intent(this, ConfirmSuccessActivity.class);
        startActivity(intent);
    }
}
