package com.surya.david.up2you;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultSearchNewsActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRADATA";
    FirebaseRecyclerAdapter<news, NewsAdapter> firebaseRecyclerAdapter,firebaseRecyclerAdapter2;
    FirebaseRecyclerOptions<news> options,options2;
    String search;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    Query query,query1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textsearch)
    TextView textsearch;
    @BindView(R.id.searchtext)
    EditText searchtext;
    @BindView(R.id.searchimage)
    ImageView searchimage;
    @BindView(R.id.list_news_search)
    RecyclerView listNewsSearch;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search_news);
        ButterKnife.bind(this);
        search = getIntent().getStringExtra(NewsFragment.SEARCH);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("news");
        query = mRef.orderByChild("title").startAt(search).endAt(search + "\uf8ff");
        listNewsSearch.setHasFixedSize(true);
        listNewsSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        toolbar.setTitle("");
        searchtext.setText(search);
        configureNews();
        configureToolbar();
    }

    private void configureNews() {
        textsearch.setText("Search results from "+search+" on News");
        options = new FirebaseRecyclerOptions.Builder<news>().setQuery(query, news.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<news, NewsAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NewsAdapter holder, final int position, @NonNull news model) {
                Picasso.get().load(model.getImage()).into(holder.mImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(ResultSearchNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.mTitle.setText(model.getTitle());
                holder.mDate.setText(model.getDate());
                holder.mCategory.setText(model.getKategori());
                holder.mKota.setText(model.getKota());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = firebaseRecyclerAdapter.getItem(position).getId_berita();
                        Intent intent = new Intent(ResultSearchNewsActivity.this, NewsActivity.class);
                        intent.putExtra(EXTRA_DATA, id);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public NewsAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
                return new NewsAdapter(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        listNewsSearch.setAdapter(firebaseRecyclerAdapter);
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

    @OnClick(R.id.searchimage)
    public void onViewClicked() {
        String search1 = searchtext.getText().toString().trim();
        textsearch.setText("Search results from "+search1+" on News");
        if (!search.equals(search1)){
            query1 = mRef.orderByChild("title").startAt(search1).endAt(search1 + "\uf8ff");
            options2 = new FirebaseRecyclerOptions.Builder<news>().setQuery(query1, news.class).build();
            firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<news, NewsAdapter>(options2) {
                @Override
                protected void onBindViewHolder(@NonNull final NewsAdapter holder, final int position, @NonNull news model) {
                    Picasso.get().load(model.getImage()).into(holder.mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(ResultSearchNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.mTitle.setText(model.getTitle());
                    holder.mDate.setText(model.getDate());
                    holder.mCategory.setText(model.getKategori());
                    holder.mKota.setText(model.getKota());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = firebaseRecyclerAdapter2.getItem(position).getId_berita();
                            Intent intent = new Intent(ResultSearchNewsActivity.this, NewsActivity.class);
                            intent.putExtra(EXTRA_DATA, id);
                            startActivity(intent);
                        }
                    });
                }

                @NonNull
                @Override
                public NewsAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
                    return new NewsAdapter(view);
                }
            };
            firebaseRecyclerAdapter2.startListening();
            listNewsSearch.setAdapter(firebaseRecyclerAdapter2);
        }else{
            configureNews();
        }
    }
}
