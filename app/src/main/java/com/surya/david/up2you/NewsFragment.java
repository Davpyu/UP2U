package com.surya.david.up2you;


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
import android.widget.LinearLayout;
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
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    @BindView(R.id.bnv)
    BottomNavigationView bnv;
    @BindView(R.id.bs_sort)
    LinearLayout bsSort;
    @BindView(R.id.bs_filter)
    LinearLayout bsFilter;
    @BindView(R.id.bs_search)
    LinearLayout bsSearch;
    BottomSheetBehavior sheetBehavior;
    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior behavior;
    @BindView(R.id.co_layout)
    CoordinatorLayout coLayout;
    Unbinder unbinder;
    @BindView(R.id.list_news)
    RecyclerView listNews;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, rootview);
        listNews.setHasFixedSize(true);
        listNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("news");
        Query query = mRef;
        configureBnv();
        FirebaseRecyclerOptions<news> options =
                new FirebaseRecyclerOptions.Builder<news>().setQuery(query, news.class).build();
        FirebaseRecyclerAdapter<news, NewsAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<news, NewsAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NewsAdapter holder, int position, @NonNull news model) {
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
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bnv.isShown()){
                    bnv.setVisibility(View.GONE);
                    coLayout.setVisibility(View.GONE);
                }else if(dy < 0){
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
                sheetBehavior = BottomSheetBehavior.from(bsSort);
                bottomSheetBehavior = BottomSheetBehavior.from(bsFilter);
                behavior = BottomSheetBehavior.from(bsSearch);
                if (itemId == R.id.sort) {

                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if ((bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) && sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    return true;
                } else if (itemId == R.id.filter) {

                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if ((sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    return true;
                } else if (itemId == R.id.search) {

                    if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        } else if ((sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) && behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
}
