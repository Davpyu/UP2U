package com.surya.david.up2you;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(R.id.submit_email)
    Button submitemail;
    @BindView(R.id.email)
    TextInputEditText email;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @OnClick(R.id.submit_email)
    public void onViewClicked() {
        final String id = email.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
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
        progressDialog.show();
        mDatabase.orderByChild("email").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.hide();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    user ur = userSnapshot.getValue(user.class);
                    Boolean stts = ur.getStatus();
                    if (stts){
                        mAuth.sendPasswordResetEmail(id)
                                .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ForgotPasswordActivity.this, "Reset Password has been send on your email", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            progressDialog.hide();
                                            Toast.makeText(ForgotPasswordActivity.this, "Cannot reset password, please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Email not verified. Please verify your email first", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ForgotPasswordActivity.this, "Database error, please report to developer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
