package com.surya.david.up2you;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_reg_user)
    Button reguser;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }
    @OnClick(R.id.btn_reg_user)
    public void onViewClicked(){
        Intent intent = new Intent(this, EmailConfirmActivity.class);
        startActivity(intent);
    }
}
