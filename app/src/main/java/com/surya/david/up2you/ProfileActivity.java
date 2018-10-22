package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.usr_nm)
    TextView usrNm;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.gender)
    TextView gender;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @BindView(R.id.profile)
    DrawerLayout profile;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.edit_profile)
    FloatingActionButton editProfile;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = getIntent().getStringExtra(ForumActivity.EXTRADATA);
        if (uid != null){
            if (uid.equals(mUser.getUid())){
                editProfile.setVisibility(View.VISIBLE);
            }else{
                editProfile.setVisibility(View.GONE);
            }
        }
        configureUser();
        configureToolbar();
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
        profile.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    private void configureUser() {
        if (uid != null){
            mRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    usrNm.setText(Objects.requireNonNull(ur).getName());
                    String bo = ur.getBio();
                    if (bo != null && !bo.equals("")) {
                        bio.setText(bo);
                    }else if (bo == null || bo.equals("")){
                        if (ur.getJen_kel().equals("Laki Laki")){
                            bio.setText(R.string.bionullhim);
                        }if (ur.getJen_kel().equals("Perempuan")){
                            bio.setText(R.string.bionullher);
                        }
                    }
                    email.setText(ur.getEmail());
                    birth.setText(ur.getTl());
                    gender.setText(ur.getJen_kel());
                    toolbar.setTitle(ur.getName());
                    status.setText(ur.getStatus().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }else{
            mRef.child(Objects.requireNonNull(mUser).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    usrNm.setText(Objects.requireNonNull(ur).getName());
                    String bo = ur.getBio();
                    if (bo != null && !bo.equals("")) {
                        bio.setText(bo);
                    }else if (bo == null || bo.equals("")){
                        bio.setText(R.string.bionullme);
                    }
                    email.setText(ur.getEmail());
                    birth.setText(ur.getTl());
                    gender.setText(ur.getJen_kel());
                    toolbar.setTitle(ur.getName());
                    status.setText(ur.getStatus().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.edit_profile)
    public void onViewClicked() {
        String uid = mUser.getUid();
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        intent.putExtra(DATA, uid);
        startActivity(intent);
    }
}
