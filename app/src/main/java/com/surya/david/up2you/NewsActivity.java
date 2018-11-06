package com.surya.david.up2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    @BindView(R.id.title_news)
    TextView titleNews;
    @BindView(R.id.kota)
    TextView kota;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.k_news)
    TextView kNews;
    @BindView(R.id.author)
    TextView author;
    int id,id2;
    @BindView(R.id.img_news)
    ImageView imgNews;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("news");
        id = getIntent().getIntExtra(NewsFragment.EXTRA_DATA, 0);
        id2 = getIntent().getIntExtra(ResultSearchNewsActivity.EXTRA_DATA, 0);
        configureNews();
        configureToolbar();
    }

    private void configureNews() {
        if (!String.valueOf(id).equals("0")){
            mReference.orderByChild("id_berita").equalTo(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                        news nws = newsSnapshot.getValue(news.class);
                        titleNews.setText(Objects.requireNonNull(nws).getTitle());
                        kota.setText(nws.getKota());
                        date.setText(nws.getDate());
                        desc.setText(nws.getIsi());
                        author.setText(nws.getAuthor());
                        kNews.setText(nws.getKategori());
                        Picasso.get().load(nws.getImage()).into(imgNews, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("news", e.getMessage());
                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("News", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }else if(!String.valueOf(id2).equals("0")){
            mReference.orderByChild("id_berita").equalTo(id2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                        news nws = newsSnapshot.getValue(news.class);
                        titleNews.setText(Objects.requireNonNull(nws).getTitle());
                        kota.setText(nws.getKota());
                        date.setText(nws.getDate());
                        desc.setText(nws.getIsi());
                        author.setText(nws.getAuthor());
                        kNews.setText(nws.getKategori());
                        Picasso.get().load(nws.getImage()).into(imgNews, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("news", e.getMessage());
                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("News", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
