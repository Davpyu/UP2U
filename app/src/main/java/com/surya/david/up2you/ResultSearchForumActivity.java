package com.surya.david.up2you;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultSearchForumActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    String search;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerAdapter<Thread, ForumViewHolder> firebaseRecyclerAdapter,firebaseRecyclerAdapter2;
    FirebaseRecyclerOptions<Thread> options,options2;
    Query query;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textsearch)
    TextView textsearch;
    @BindView(R.id.searchtext)
    EditText searchtext;
    @BindView(R.id.searchimage)
    ImageView searchimage;
    @BindView(R.id.list_thread_search)
    RecyclerView listThreadSearch;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search_forum);
        ButterKnife.bind(this);
        search = getIntent().getStringExtra(ForumFragment.SEARCH);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("threads");
        query = mRef.orderByChild("judul").startAt(search).endAt(search + "\uf8ff");
        listThreadSearch.setHasFixedSize(true);
        listThreadSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        toolbar.setTitle("");
        searchtext.setText(search);
        configurethreads();
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
        drawerlayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    private void configurethreads() {
        textsearch.setText("Search results from "+search+" on Lounge");
        options = new FirebaseRecyclerOptions.Builder<Thread>().setQuery(query, Thread.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Thread, ForumViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ForumViewHolder holder, final int position, @NonNull Thread model) {
                holder.jdl.setText(model.getJudul());
                holder.tag.setText(model.getTag());
                holder.kategori.setText(model.getKategori());
                Picasso.get().load(model.getImageUrl()).into(holder.img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Image", e.getMessage());
                    }
                });
                mDatabase.getReference("Users").orderByKey().equalTo(model.getUserId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot username : dataSnapshot.getChildren()) {
                            user ur = username.getValue(user.class);
                            holder.nm.setText(Objects.requireNonNull(ur).getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("Forum", databaseError.getMessage());
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = firebaseRecyclerAdapter.getItem(position).getKey();
                        Intent intent = new Intent(ResultSearchForumActivity.this, ForumActivity.class);
                        intent.putExtra(DATA, key);
                        startActivity(intent);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        String uid = firebaseRecyclerAdapter.getItem(position).getUserId();
                        if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(uid)) {
                            final String[] listItem = getResources().getStringArray(R.array.menu);
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultSearchForumActivity.this);
                            mBuilder.setTitle("What do you want to do?");
                            mBuilder.setItems(listItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        String key = firebaseRecyclerAdapter.getItem(position).getKey();
                                        Intent intent = new Intent(ResultSearchForumActivity.this, UpdateThreadActivity.class);
                                        intent.putExtra(DATA, key);
                                        startActivity(intent);
                                    }
                                    if (i == 1) {
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(ResultSearchForumActivity.this));
                                        builder.setMessage("Are you sure you want to delete?")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        firebaseRecyclerAdapter.getRef(position).removeValue();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        android.support.v7.app.AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }
                            }).show();
                        }
                        return true;
                    }
                });
            }

            @NonNull
            @Override
            public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lounge_item, parent, false);
                return new ForumViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        listThreadSearch.setAdapter(firebaseRecyclerAdapter);
    }

    @OnClick(R.id.searchimage)
    public void onViewClicked() {
        String search1 = searchtext.getText().toString().trim();
        textsearch.setText("Search results from "+search1+" on Lounge");
        if (!search.equals(search1)){
            Query query1 = mRef.orderByChild("judul").startAt(search1).endAt(search1 + "\uf8ff");
            options2 = new FirebaseRecyclerOptions.Builder<Thread>().setQuery(query1, Thread.class).build();
            firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<Thread, ForumViewHolder>(options2) {
                @Override
                protected void onBindViewHolder(@NonNull final ForumViewHolder holder, final int position, @NonNull Thread model) {
                    holder.jdl.setText(model.getJudul());
                    holder.tag.setText(model.getTag());
                    holder.kategori.setText(model.getKategori());
                    Picasso.get().load(model.getImageUrl()).into(holder.img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Image", e.getMessage());
                        }
                    });
                    mDatabase.getReference("Users").orderByKey().equalTo(model.getUserId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot username : dataSnapshot.getChildren()) {
                                user ur = username.getValue(user.class);
                                holder.nm.setText(Objects.requireNonNull(ur).getName());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Forum", databaseError.getMessage());
                        }
                    });
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String key = firebaseRecyclerAdapter.getItem(position).getKey();
                            Intent intent = new Intent(ResultSearchForumActivity.this, ForumActivity.class);
                            intent.putExtra(DATA, key);
                            startActivity(intent);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            String uid = firebaseRecyclerAdapter.getItem(position).getUserId();
                            if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(uid)) {
                                final String[] listItem = getResources().getStringArray(R.array.menu);
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultSearchForumActivity.this);
                                mBuilder.setTitle("What do you want to do?");
                                mBuilder.setItems(listItem, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            String key = firebaseRecyclerAdapter.getItem(position).getKey();
                                            Intent intent = new Intent(ResultSearchForumActivity.this, UpdateThreadActivity.class);
                                            intent.putExtra(DATA, key);
                                            startActivity(intent);
                                        }
                                        if (i == 1) {
                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(ResultSearchForumActivity.this));
                                            builder.setMessage("Are you sure you want to delete?")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            firebaseRecyclerAdapter.getRef(position).removeValue();
                                                        }
                                                    })
                                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                            android.support.v7.app.AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                    }
                                }).show();
                            }
                            return true;
                        }
                    });
                }

                @NonNull
                @Override
                public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lounge_item, parent, false);
                    return new ForumViewHolder(view);
                }
            };
            firebaseRecyclerAdapter2.startListening();
            listThreadSearch.setAdapter(firebaseRecyclerAdapter2);
        }else{
            configurethreads();
        }
    }
}
