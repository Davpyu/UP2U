package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeEmailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.changeEmail)
    LinearLayout changeEmail;
    @BindView(R.id.titleemail)
    TextView titleemail;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.notverify)
    RelativeLayout notverify;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.send)
    Button send;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    @BindView(R.id.tryagain)
    Button tryagain;
    @BindView(R.id.titlee)
    TextView titlee;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child(mUser.getUid());
        toolbar.setTitle("");
        changeEmail.setVisibility(View.GONE);
        notverify.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        tryagain.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
        titleemail.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        configureLayout();
        configureToolbar();
    }

    private void configureLayout() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user ur = dataSnapshot.getValue(user.class);
                Boolean stts = ur.getStatus();
                if (stts) {
                    changeEmail.setVisibility(View.VISIBLE);
                    notverify.setVisibility(View.GONE);
                } else {
                    changeEmail.setVisibility(View.GONE);
                    notverify.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Edit Email", databaseError.getCode() + "" + databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user ur = dataSnapshot.getValue(user.class);
                Boolean stts = ur.getStatus();
                if (stts) {
                    getMenuInflater().inflate(R.menu.tickitem, menu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Edit Email", databaseError.getCode() + "" + databaseError.getMessage());
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String pass = password.getText().toString().trim();
        if (pass.isEmpty()) {
            password.setError("Please fill this field");
            password.requestFocus();
        }
        if (!pass.isEmpty()) {
            notverify.setVisibility(View.VISIBLE);
            progress.setVisibility(View.VISIBLE);
            titleemail.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            tryagain.setVisibility(View.GONE);
            String emailcurrent = mUser.getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(emailcurrent, pass);
            mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        titlee.setText("Change your Email");
                        email.setVisibility(View.VISIBLE);
                        password.setVisibility(View.GONE);
                        final String em = email.getText().toString().trim();
                        if (em.isEmpty()) {
                            email.setError("Please fill this field");
                            email.requestFocus();
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                            email.setError("Enter a valid email");
                            email.requestFocus();
                        }
                        if (!em.isEmpty()) {
                            progress.setVisibility(View.VISIBLE);
                            mUser.updateEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progress.setVisibility(View.GONE);
                                        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                dataSnapshot.getRef().child("email").setValue(em);
                                                Toast.makeText(ChangeEmailActivity.this, "Email Changed", Toast.LENGTH_SHORT).show();
                                                titleemail.setVisibility(View.VISIBLE);
                                                titleemail.setText("Email Updated!!\nPlease check on your inbox");
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("Update Email", databaseError.getCode() + "" + databaseError.getMessage());
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(ChangeEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Reauthenticate", e.getMessage());
                        }
                    });
        }
        return true;
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        progress.setVisibility(View.VISIBLE);
        send.setVisibility(View.GONE);
        titleemail.setVisibility(View.GONE);
        mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ChangeEmailActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.VISIBLE);
                    titleemail.setVisibility(View.VISIBLE);
                }
            }
        });
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

    @OnClick(R.id.tryagain)
    public void onViewClick() {
        titleemail.setText("Verification Email sent,\nclick Refresh if you have verified your account");
        progress.setVisibility(View.VISIBLE);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mUser.isEmailVerified()) {
                    dataSnapshot.getRef().child("status").setValue(true);
                    mUser.reload();
                    progress.setVisibility(View.GONE);
                    configureLayout();
                } else {
                    dataSnapshot.getRef().child("status").setValue(false);
                    mUser.reload();
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Update Email", databaseError.getCode() + "" + databaseError.getMessage());
            }
        });
    }
}
