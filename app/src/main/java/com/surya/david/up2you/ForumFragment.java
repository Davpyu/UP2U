package com.surya.david.up2you;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    public static final String DATA = "DATA";
    @BindView(R.id.add_thread)
    FloatingActionButton addThread;
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
    @BindView(R.id.list_thread)
    RecyclerView listThread;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerAdapter<Thread, ForumViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Thread> options;

    public ForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        unbinder = ButterKnife.bind(this, view);
        configureBnv();
        listThread.setHasFixedSize(true);
        listThread.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("threads");
        Query query = mRef;
        options = new FirebaseRecyclerOptions.Builder<Thread>().setQuery(query, Thread.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Thread, ForumViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ForumViewHolder holder, final int position, @NonNull Thread model) {
                holder.jdl.setText(model.getJudul());
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
                        for (DataSnapshot username: dataSnapshot.getChildren()) {
                            user ur = username.getValue(user.class);
                            holder.nm.setText(ur.getName());
//                            Log.d("Forum", ur.getName());
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
                        Intent intent = new Intent(getContext(), ForumActivity.class);
                        intent.putExtra(DATA, key);
                        startActivity(intent);
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
        listThread.setAdapter(firebaseRecyclerAdapter);
        listThread.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bnv.isShown()) {
                    bnv.setVisibility(View.GONE);
                    coLayout.setVisibility(View.GONE);
                } else if (dy < 0) {
                    bnv.setVisibility(View.VISIBLE);
                    coLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_thread)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), AddThreadActivity.class);
        startActivity(intent);
    }
}
