package com.surya.david.up2you;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    @BindView(R.id.logout)
    TextView logout;
    Unbinder unbinder;
    private FirebaseAuth mAuth;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, rootview);
        mAuth = FirebaseAuth.getInstance();
        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        mAuth.signOut();
        Toast.makeText(getContext(), "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
