package com.surya.david.up2you;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_reg_user)
    Button reguser;
    @BindView(R.id.tl)
    TextView tl;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                tl.setText(date);
            }
        };
    }

    @OnClick(R.id.btn_reg_user)
    public void onViewClicked() {
        Intent intent = new Intent(this, EmailConfirmActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tl)
    public void onDateClick() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDialog = new DatePickerDialog(RegisterActivity.this,R.style.Theme_AppCompat_Light_Dialog,mDateSetListener,year,month,date);
        mDialog.show();
    }
}
