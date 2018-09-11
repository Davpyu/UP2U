package com.surya.david.up2you;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_reg_user)
    Button reguser;
    @BindView(R.id.tl)
    TextView tl;
    @BindView(R.id.profile_name)
    TextInputEditText profileName;
    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.pass)
    TextInputEditText pass;
    @BindView(R.id.confirm_pass)
    EditText confirmPass;
    @BindView(R.id.jk_l)
    RadioButton jkL;
    @BindView(R.id.jk_p)
    RadioButton jkP;
    @BindView(R.id.progressbar)
    RelativeLayout progressbar;
    @BindView(R.id.gen)
    TextView gen;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        progressbar.setVisibility(View.GONE);
        dateDialog();
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        final String name = profileName.getText().toString().trim();
        final String email = username.getText().toString().trim();
        String pw = pass.getText().toString().trim();
        String con_pass = confirmPass.getText().toString().trim();
        final String t_l = tl.getText().toString().trim();
        final String userId = mAuth.getCurrentUser().getUid();
        String jen_kel = "";
        if (jkL.isChecked()) {
            jen_kel = jkL.getText().toString().trim();
        }
        if (jkP.isChecked()) {
            jen_kel = jkP.getText().toString().trim();
        }
        if (name.isEmpty()) {
            profileName.setError("Profile Name required");
            profileName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            username.setError("Email required");
            username.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Enter a valid email");
            username.requestFocus();
            return;
        }
        if (pw.isEmpty()) {
            pass.setError("Please enter your password");
            pass.requestFocus();
            return;
        }
        if (con_pass.isEmpty()) {
            confirmPass.setError("Please confirm your password");
            confirmPass.requestFocus();
            return;
        }
        if (!pw.equals(con_pass)) {
            confirmPass.setError("Password not valid, Please re-enter your vonfirm password");
            confirmPass.requestFocus();
            return;
        }
        if (t_l.isEmpty()) {
            tl.setError("Please enter your birthday");
            tl.requestFocus();
            return;
        }
        if (jen_kel.isEmpty()) {
            gen.setError("Please select your gender");
            gen.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        final String finalJen_kel = jen_kel;
        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            user usr = new user(
                                    name,
                                    email,
                                    finalJen_kel,
                                    t_l,
                                    "",
                                    "",
                                    false
                            );
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId)
                                    .setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(RegisterActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Intent intent = new Intent(RegisterActivity.this, EmailConfirmActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void dateDialog() {
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
        registerUser();
    }

    @OnClick(R.id.tl)
    public void onDateClick() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDialog = new DatePickerDialog(RegisterActivity.this, R.style.Theme_AppCompat_DayNight_Dialog, mDateSetListener, year, month, date);
        mDialog.show();
    }
}
