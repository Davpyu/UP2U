package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        configureToolbar();
        configureNavigationDrawer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            mDatabase.child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (user.isEmailVerified()){
                        dataSnapshot.getRef().child("status").setValue(true);
                        user.reload();
                    }else{
                        dataSnapshot.getRef().child("status").setValue(false);
                        user.reload();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("User", databaseError.getMessage());

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void configureNavigationDrawer() {
        final Fragment ff = new NewsFragment();
        toolbar.setTitle(getString(R.string.news));
        View headerView = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout)headerView.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                if (mAuth.getCurrentUser() != null){
                    Fragment ft = ff;
                    ft = new ProfileFragment();
                    toolbar.setTitle(getString(R.string.profile));
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, ft);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, ff);
        transaction.commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment f = ff;
                int itemId = item.getItemId();

                if (itemId == R.id.nws) {
                    toolbar.setTitle(getString(R.string.news));
                    f = new NewsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, f);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (itemId == R.id.frm) {
                    if (mAuth.getCurrentUser() != null){
                        toolbar.setTitle(getString(R.string.lounge));
                        f = new ForumFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, f);
                        transaction.commit();
                        drawerLayout.closeDrawers();
                        return true;
                    }else{
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }

                } else if (itemId == R.id.stt) {
                    if (mAuth.getCurrentUser() != null){
                        toolbar.setTitle(getString(R.string.setting));
                        f = new SettingFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, f);
                        transaction.commit();
                        drawerLayout.closeDrawers();
                        return true;
                    }else{
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }

                }

                return false;
            }
        });
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }
}
