package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumActivity extends AppCompatActivity {
    String key;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_thread)
    TextView titleThread;
    @BindView(R.id.kota)
    TextView kota;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.img_thread)
    ImageView imgThread;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.k_thread)
    TextView kThread;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.list_comment)
    RecyclerView listComment;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
        key = getIntent().getStringExtra(ForumFragment.DATA);
        toolbar.setTitle("");
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("threads");
        configureForum();
        configureToolbar();
    }

    private void configureForum() {
        mRef.orderByChild("key").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot forumSnapshot : dataSnapshot.getChildren()) {
                    Thread thr = forumSnapshot.getValue(Thread.class);
                    Picasso.get().load(thr.getImageUrl()).into(imgThread, new Callback() {
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
}
