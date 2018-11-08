package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titlee)
    TextView titlee;
    @BindView(R.id.oldpassword)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    @BindView(R.id.newpassword)
    EditText newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child(mUser.getUid());
        ll1.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        toolbar.setTitle("");
        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        drawerlayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tickitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String pass = password.getText().toString().trim();
        newpassword.setVisibility(View.INVISIBLE);
        if (pass.isEmpty()) {
            password.setError("Please fill your old password here");
            password.requestFocus();
        }
        if (!pass.isEmpty()) {
            String email = mUser.getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
            progress.setVisibility(View.VISIBLE);
            mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        titlee.setText("Enter your new password");
                        ll1.setVisibility(View.VISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        newpassword.setVisibility(View.VISIBLE);
                        String newpass = newpassword.getText().toString().trim();
                        String conpass = confirmPassword.getText().toString().trim();
                        if (newpass.isEmpty()) {
                            password.setError("Please fill your new password here");
                            password.requestFocus();
                        }
                        if (conpass.isEmpty()) {
                            confirmPassword.setError("Please re-enter to confirm your new password");
                            confirmPassword.requestFocus();
                        }
                        if (!conpass.equals(newpass)) {
                            confirmPassword.setError("Please re-enter again same as your new password");
                            confirmPassword.requestFocus();
                        }
                        if (newpass.length() < 6) {
                            password.setError("Please fill at least 6 character");
                            password.requestFocus();
                        }
                        if (!newpass.isEmpty() && !conpass.isEmpty() && conpass.equals(newpass) && newpass.length() >= 6) {
                            progress.setVisibility(View.VISIBLE);
                            mUser.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(ChangePasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Reauthenticate", e.getMessage());
                }
            });
        }
        return true;
    }
}
