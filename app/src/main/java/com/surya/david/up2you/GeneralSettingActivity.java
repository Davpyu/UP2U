package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GeneralSettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.edit_profile)
    TextView editProfile;
    @BindView(R.id.change_email)
    TextView changeEmail;
    @BindView(R.id.change_pw)
    TextView changePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_setting);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.general));
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
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @OnClick(R.id.edit_profile)
    public void onEditProfileClicked() {
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.change_email)
    public void onChangeEmailClicked() {
    }

    @OnClick(R.id.change_pw)
    public void onChangePwClicked() {
    }
}
