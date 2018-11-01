package com.surya.david.up2you;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class ForumActivity extends AppCompatActivity {

    public static final String EXTRADATA = "EXTRADATA";
    String key,key2;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef, mSref, mKref;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.komentar)
    RelativeLayout komentar;
    @BindView(R.id.komen)
    EditText komen;
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
    @BindView(R.id.send)
    ImageView send;
    FirebaseRecyclerAdapter<comment, CommentViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<comment> options;
    Query query;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.temp)
    LinearLayout temp;
    @BindView(R.id.usr)
    TextView usr;
    @BindView(R.id.komen1)
    EditText komen1;
    ImageView update;
    @BindView(R.id.coLayout1)
    CoordinatorLayout coLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
        key = getIntent().getStringExtra(ForumFragment.DATA);
        key2 = getIntent().getStringExtra(ResultSearchForumActivity.DATA);
        listComment.setHasFixedSize(true);
        listComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        update = findViewById(R.id.update);
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = mDatabase.getReference("threads");
        if (key != null){
            mKref = mDatabase.getReference("threads").child(key);
        }else if(key2 !=null){
            mKref = mDatabase.getReference("threads").child(key2);
        }
        mSref = mDatabase.getReference("Users");
        query = mKref.child("comment");
        listComment.setFocusable(false);
        temp.requestFocus();
        toolbar.setTitle("");
        komentar.bringToFront();
        coLayout.bringToFront();
        coLayout1.bringToFront();
        configureForum();
        configureComment();
        configureToolbar();
    }

    private void configureComment() {
        options = new FirebaseRecyclerOptions.Builder<comment>().setQuery(query, comment.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<comment, CommentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position, @NonNull comment model) {
                holder.isi.setText(model.getIsi());
                mSref.orderByKey().equalTo(model.getUserId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot bla : dataSnapshot.getChildren()) {
                            user ur = bla.getValue(user.class);
                            holder.name.setText(ur.getName());
//                            Picasso.get()
//                                    .load(ur.getFoto())
//                                    .placeholder(R.mipmap.ic_launcher)
//                                    .into(holder.profileimg, new Callback() {
//                                        @Override
//                                        public void onSuccess() {
//
//                                        }
//
//                                        @Override
//                                        public void onError(Exception e) {
//                                            Log.d("Profile Image", e.getMessage());
//                                            Toast.makeText(ForumActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                                        }
//                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("Comment", databaseError.getCode() + "" + databaseError.getMessage());
                        Toast.makeText(ForumActivity.this, databaseError.getCode() + "" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uid = firebaseRecyclerAdapter.getItem(position).getUserId();
                        if (mUser.getUid().equals(uid)){
//                            Toast.makeText(ForumActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                            final String[] listItem = getResources().getStringArray(R.array.menu);
                            AlertDialog.Builder mBuild = new AlertDialog.Builder(ForumActivity.this);
                            mBuild.setTitle("What do you want to do?");
                            mBuild.setItems(listItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0){
                                        final String key1 = firebaseRecyclerAdapter.getItem(position).getKey();
                                        query.orderByChild("key").equalTo(key1).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot u : dataSnapshot.getChildren()) {
                                                    comment cmnn = u.getValue(comment.class);
                                                    coLayout1.setVisibility(View.VISIBLE);
                                                    komentar.setVisibility(View.GONE);
                                                    komen1.setText(cmnn.getIsi());
                                                    komen1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                        @Override
                                                        public void onFocusChange(View view, boolean b) {
                                                            komen1.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                    Objects.requireNonNull(imm).showSoftInput(komen1, InputMethodManager.SHOW_IMPLICIT);
                                                                }
                                                            });
                                                        }
                                                    });
                                                    komen1.requestFocus();
                                                }
                                                final String cmn = komen1.getText().toString().trim();
                                                if (cmn.isEmpty()){
                                                    komen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                        @Override
                                                        public void onFocusChange(View view, boolean b) {
                                                            komen1.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(komen1.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                                                                }
                                                            });
                                                        }
                                                    });
                                                    coLayout.setVisibility(View.GONE);
                                                    komentar.setVisibility(View.VISIBLE);
                                                    komen1.setText("");
                                                }else{
                                                    update.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            final String cc = komen1.getText().toString();
//                                                            Toast.makeText(ForumActivity.this, cc, Toast.LENGTH_SHORT).show();
                                                           mKref.child("comment").child(key1).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    dataSnapshot.getRef().child("isi").setValue(cc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()){
                                                                                Toast.makeText(ForumActivity.this, "Comment Updated", Toast.LENGTH_SHORT).show();
                                                                                komen1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                                                    @Override
                                                                                    public void onFocusChange(View view, boolean b) {
                                                                                        komen1.post(new Runnable() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(komen1.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                                coLayout1.setVisibility(View.GONE);
                                                                                komentar.setVisibility(View.VISIBLE);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                    Log.d("Comment", databaseError.getCode() + "" + databaseError.getMessage());
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("Komentar", databaseError.getCode() + "" + databaseError.getMessage());
                                                Toast.makeText(ForumActivity.this, databaseError.getCode() + "" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }if (i == 1){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ForumActivity.this);
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
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }
                            }).show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comment_item, parent, false);
                return new CommentViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        listComment.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
                if (coLayout.getVisibility() == View.VISIBLE || coLayout1.getVisibility() == View.VISIBLE) {
            komen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    komen.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(komen.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    });
                }
            });
            komen1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    komen1.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(komen1.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    });
                }
            });
            coLayout.setVisibility(View.GONE);
            coLayout1.setVisibility(View.GONE);
            komentar.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    private void configureForum() {
        if (key != null){
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
                        tag.setText(thr.getTag());
                        kThread.setText(thr.getKategori());
                        date.setText(thr.getDate());
                        final String uid = thr.getUserId();
                        mSref.orderByKey().equalTo(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    user ur = data.getValue(user.class);
                                    usr.setText(ur.getName());
                                    usr.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForumActivity.this, ProfileActivity.class);
                                            intent.putExtra(EXTRADATA, uid);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("username", databaseError.getCode() + "" + databaseError.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Forum", databaseError.getMessage());
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        if (i1 > i3 && komentar.getVisibility() == View.VISIBLE) {
                            komentar.setVisibility(View.GONE);
                        } else if (i1 < i3) {
                            komentar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }else if(key2 != null){
            mRef.orderByChild("key").equalTo(key2).addValueEventListener(new ValueEventListener() {
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
                        tag.setText(thr.getTag());
                        kThread.setText(thr.getKategori());
                        date.setText(thr.getDate());
                        final String uid = thr.getUserId();
                        mSref.orderByKey().equalTo(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    user ur = data.getValue(user.class);
                                    usr.setText(ur.getName());
                                    usr.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForumActivity.this, ProfileActivity.class);
                                            intent.putExtra(EXTRADATA, uid);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("username", databaseError.getCode() + "" + databaseError.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Forum", databaseError.getMessage());
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        if (i1 > i3 && komentar.getVisibility() == View.VISIBLE) {
                            komentar.setVisibility(View.GONE);
                        } else if (i1 < i3) {
                            komentar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
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
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @OnClick(R.id.komenan)
    public void onViewClick() {
        coLayout.bringToFront();
        komentar.setVisibility(View.GONE);
        coLayout.setVisibility(View.VISIBLE);
        komen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                komen.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).showSoftInput(komen, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        komen.requestFocus();
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        final String cmn = komen.getText().toString().trim();
        final String uid = mUser.getUid();
        if (cmn.isEmpty()) {
            komen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    komen.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(komen.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    });
                }
            });
            coLayout.setVisibility(View.GONE);
            komentar.setVisibility(View.VISIBLE);
            komen.setText("");
        } else {
            String key1 = mKref.push().getKey();
            comment cm = new comment(
                    uid,
                    key,
                    cmn,
                    key1
            );
            mKref.child("comment").child(Objects.requireNonNull(key1)).setValue(cm).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForumActivity.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                        komen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean b) {
                                komen.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        Objects.requireNonNull(imm).hideSoftInputFromWindow(komen.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                                    }
                                });
                            }
                        });
                        coLayout.setVisibility(View.GONE);
                        komentar.setVisibility(View.VISIBLE);
                        komen.setText("");
                    } else {
                        Toast.makeText(ForumActivity.this, "Comment Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
