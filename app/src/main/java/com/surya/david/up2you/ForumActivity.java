package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumActivity extends AppCompatActivity {
    String key;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef, mKRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.komentar)
    RelativeLayout komentar;
    @BindView(R.id.komen)
    EditText komen;
    @BindView(R.id.send)
    ImageView send;
    @BindView(R.id.coLayout)
    CoordinatorLayout coLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_thread)
    TextView titleThread;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.k_thread)
    TextView kThread;
    @BindView(R.id.img_thread)
    ImageView imgThread;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.list_comment)
    RecyclerView listComment;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.komenan)
    CardView komenan;
    @BindView(R.id.scroll)
    ScrollView scroll;
    FirebaseRecyclerOptions<comment> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
        key = getIntent().getStringExtra(ForumFragment.DATA);
        toolbar.setTitle("");
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("threads");
        mKRef = mDatabase.getReference("comment");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        configureForum();
        configureToolbar();
    }

    @Override
    public void onBackPressed() {
        if (coLayout.getVisibility() == View.VISIBLE){
            coLayout.setVisibility(View.GONE);
            komentar.setVisibility(View.VISIBLE);
        }else {
            finish();
        }
    }

    private void configureForum() {
        mRef.orderByChild("key").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot forumSnapshot : dataSnapshot.getChildren()) {
                    Thread thr = forumSnapshot.getValue(Thread.class);
                    Picasso.get().load(Objects.requireNonNull(thr).getImageUrl()).into(imgThread, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Forum", e.getMessage());
                        }
                    });
                    titleThread.setText(thr.getJudul());
                    desc.setText(thr.getIsi());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Forum", databaseError.getMessage());
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
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @OnClick(R.id.komenan)
    public void onViewClick() {
//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        coLayout.bringToFront();
        komentar.setVisibility(View.GONE);
        coLayout.setVisibility(View.VISIBLE);
        final String cmn = komen.getText().toString().trim();
        final String uid = mUser.getUid();
        if (cmn != null){
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comment cm = new comment(
                            uid,
                            key,
                            cmn
                    );
                    mKRef.child(Objects.requireNonNull(mKRef.push().getKey())).setValue(cm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForumActivity.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ForumActivity.this, "Comment Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
