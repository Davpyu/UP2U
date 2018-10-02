package com.surya.david.up2you;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateThreadActivity extends AppCompatActivity {
    String id;
    private final int PICK_IMAGE_REQUEST = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_judul)
    EditText addJudul;
    @BindView(R.id.isithread)
    EditText isithread;
    @BindView(R.id.tag)
    Spinner tag;
    @BindView(R.id.kategori)
    Spinner kategori;
//    @BindView(R.id.gambar_video)
//    TextView gambarVideo;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.add_layout)
    DrawerLayout addLayout;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    ArrayAdapter<String> t4g;
    ArrayAdapter<String> ktgri;
    @BindView(R.id.send_btn)
    FloatingActionButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_thread);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra(ForumFragment.DATA);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("threads");
        String[] tg = {"#fyi", "#ask"};
        String[] ktgr = {"Hiburan", "Olahraga", "Teknologi", "Fashion"};
        t4g = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tg);
        ktgri = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ktgr);
        tag.setAdapter(t4g);
        kategori.setAdapter(ktgri);
        progress.setVisibility(View.GONE);
        toolbar.setTitle("Update Thread");
        configurethread();
        configureToolbar();
    }

    private void configurethread() {
        mRef.orderByChild("key").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot th : dataSnapshot.getChildren()) {
                    Thread thr = th.getValue(Thread.class);
                    addJudul.setText(thr.getJudul());
                    isithread.setText(thr.getIsi());
                    Picasso.get().load(thr.getImageUrl()).into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Update Thread", e.getMessage());
                        }
                    });
                    tag.setSelection(t4g.getPosition(thr.getTag()));
                    kategori.setSelection(ktgri.getPosition(thr.getKategori()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Update Forum", databaseError.getCode() + "" + databaseError.getMessage());
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
        addLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @OnClick(R.id.send_btn)
    public void onViewClick() {
        final String judul = addJudul.getText().toString().trim();
        final String isi = isithread.getText().toString().trim();
        final String tg = tag.getSelectedItem().toString().trim();
        final String ktgr = kategori.getSelectedItem().toString().trim();
        if (judul.isEmpty()) {
            addJudul.setError("Please add title of this thread");
            addJudul.requestFocus();
            return;
        }
        if (isi.isEmpty()) {
            isithread.setError("Please add content of this thread");
            isithread.requestFocus();
            return;
        }
        mRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("judul").setValue(judul);
                dataSnapshot.getRef().child("isi").setValue(isi);
                dataSnapshot.getRef().child("tag").setValue(tg);
                dataSnapshot.getRef().child("kategori").setValue(ktgr);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Update Forum", databaseError.getCode() + "" + databaseError.getMessage());
                Toast.makeText(getApplicationContext(), MessageFormat.format("{0}{1}", String.valueOf(databaseError.getCode()), databaseError.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }
}
