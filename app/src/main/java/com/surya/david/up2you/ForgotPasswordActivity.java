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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(R.id.submit_email)
    Button submitemail;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.progressbar)
    RelativeLayout progressbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        progressbar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @OnClick(R.id.submit_email)
    public void onViewClicked() {
        String id = email.getText().toString().trim();
        if (id.isEmpty()) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
//        if (user.isEmailVerified()){
            mAuth.sendPasswordResetEmail(id)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPasswordActivity.this, "Reset Password has been send on your email", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
//        }else{
//            progressbar.setVisibility(View.GONE);
//            Toast.makeText(ForgotPasswordActivity.this, "Email not verified. Please verify your email first", Toast.LENGTH_SHORT).show();
//        }

    }
}
