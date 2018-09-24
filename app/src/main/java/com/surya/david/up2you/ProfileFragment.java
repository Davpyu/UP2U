package com.surya.david.up2you;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.usr_nm)
    TextView usrNm;
    Unbinder unbinder;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.gender)
    TextView gender;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef.child(Objects.requireNonNull(mUser).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user ur = dataSnapshot.getValue(user.class);
                usrNm.setText(Objects.requireNonNull(ur).getName());
                email.setText(ur.getEmail());
                birth.setText(ur.getTl());
                gender.setText(ur.getJen_kel());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
