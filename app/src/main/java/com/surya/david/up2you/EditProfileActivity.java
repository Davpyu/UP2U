package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity {
    String uid;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nmProfile)
    EditText nmProfile;
    @BindView(R.id.bi)
    TextView bi;
    @BindView(R.id.bio)
    EditText bio;
    @BindView(R.id.ttl)
    TextView ttl;
    @BindView(R.id.jk_l)
    RadioButton jkL;
    @BindView(R.id.jk_p)
    RadioButton jkP;
    @BindView(R.id.edit)
    FloatingActionButton edit;
    @BindView(R.id.edit_profile)
    DrawerLayout editProfile;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        toolbar.setTitle("Update Profile");
        uid = getIntent().getStringExtra(ProfileActivity.DATA);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");
        configureToolbar();
        configureUser();
    }
    private void configureUser() {
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user ur = dataSnapshot.getValue(user.class);
                nmProfile.setText(Objects.requireNonNull(ur).getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
            }
        });
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editProfile.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @OnClick(R.id.edit)
    public void onViewClicked() {
    }
}
