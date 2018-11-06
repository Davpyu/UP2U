package com.surya.david.up2you;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    public static final String EXTRA_DATA = "EXTRADATA";
    public static final String SEARCH = "SEARCH";
    @BindView(R.id.bnv)
    BottomNavigationView bnv;
    @BindView(R.id.bs_filter)
    LinearLayout bsFilter;
    @BindView(R.id.bs_search)
    LinearLayout bsSearch;
    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior behavior;
    @BindView(R.id.co_layout)
    CoordinatorLayout coLayout;
    Unbinder unbinder;
    @BindView(R.id.list_news)
    RecyclerView listNews;
    FirebaseDatabase mDatabase;
    int id;
    DatabaseReference mRef;
    FirebaseRecyclerAdapter<news, NewsAdapter> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<news> options;
    @BindView(R.id.closefilter)
    ImageView closefilter;
    @BindView(R.id.searchtext)
    EditText searchtext;
    @BindView(R.id.searchimage)
    ImageView searchimage;
    @BindView(R.id.close)
    ImageView close;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, rootview);
        bottomSheetBehavior = BottomSheetBehavior.from(bsFilter);
        behavior = BottomSheetBehavior.from(bsSearch);
        listNews.setHasFixedSize(true);
        listNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("news");
        Query query = mRef;
        configureBnv();

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
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getContext(), NewsActivity.class);
                        intent.putExtra(EXTRA_DATA, id);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
        listNews.setAdapter(firebaseRecyclerAdapter);
        listNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bnv.isShown()) {
                    bnv.setVisibility(View.GONE);
                    coLayout.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (dy < 0) {
                    bnv.setVisibility(View.VISIBLE);
                    coLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void configureBnv() {
        coLayout.bringToFront();
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.filter) {

                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    return true;
                } else if (itemId == R.id.search) {

                    if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    return true;
                }
                return false;
            }
        });
    }


    @OnClick(R.id.closefilter)
    public void onClosefilterClicked() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @OnClick(R.id.searchimage)
    public void onSearchimageClicked() {
        String searchquery= searchtext.getText().toString().trim();
        Intent intent = new Intent(getActivity(), ResultSearchNewsActivity.class);
        intent.putExtra(SEARCH, searchquery);
        searchtext.setText("");
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        startActivity(intent);
    }

    @OnClick(R.id.close)
    public void onViewClicked() {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
