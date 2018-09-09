package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.forgot_pass)
    Button forgotpass;
    @BindView(R.id.btn_loginui)
    Button btnLoginui;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.pass)
    TextInputEditText pass;
    @BindView(R.id.progressbar)
    RelativeLayout progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressbar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.forgot_pass)
    public void onViewClick() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_loginui)
    public void onViewClicked() {
        String email = username.getText().toString().trim();
        String pw = pass.getText().toString().trim();
        if (email.isEmpty()){
            username.setError("Please enter your email");
            username.requestFocus();
            return;
        }
        if (pw.isEmpty()){
            pass.setError("Please enter your password");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    @OnClick(R.id.register)
    public void onViewClicke() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
