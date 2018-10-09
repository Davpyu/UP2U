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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddThreadActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference mSRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @BindView(R.id.send_btn)
    FloatingActionButton sendBtn;
    @BindView(R.id.tag)
    Spinner tag;
    @BindView(R.id.kategori)
    Spinner kategori;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.add_layout)
    DrawerLayout addLayout;
    private Uri imgUri;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_judul)
    EditText addJudul;
    @BindView(R.id.isithread)
    EditText isithread;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.gambar_video)
    TextView gambarVideo;
    ProgressBar dialog;
    Calendar calendar;
    SimpleDateFormat dtf;
    SimpleDateFormat dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        ButterKnife.bind(this);
        String[] tg = {"#fyi", "#ask"};
        String[] ktgr = {"Hiburan", "Olahraga", "Teknologi", "Fashion"};
        ArrayAdapter<String> t4g = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tg);
        ArrayAdapter<String> ktgri = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ktgr);
        tag.setAdapter(t4g);
        kategori.setAdapter(ktgri);
        dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dt = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        calendar = Calendar.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("threads");
        mStorage = FirebaseStorage.getInstance();
        mSRef = mStorage.getReference("threads");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        toolbar.setTitle("Post Thread");
        dialog = findViewById(R.id.progress);
        dialog.setVisibility(View.GONE);
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
        addLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @OnClick(R.id.gambar_video)
    public void onViewClicked() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(img);
        }
    }

    @OnClick(R.id.send_btn)
    public void onViewClick() {
        final String judul = addJudul.getText().toString().trim();
        final String isi = isithread.getText().toString().trim();
        final String tg = tag.getSelectedItem().toString().trim();
        final String ktgr = kategori.getSelectedItem().toString().trim();
        final String uid = mUser.getUid();
        final String key = mRef.push().getKey();
        final String currentDate = dtf.format(calendar.getTime());
        final String current = dt.format(calendar.getTime());
        final StorageReference th = mSRef.child(current).child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
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
        if (isi.length() < 500){
            isithread.setError("Please fill content more than 500");
            isithread.requestFocus();
            return;
        }
        if (imgUri != null) {
            th.putFile(imgUri)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.setVisibility(View.VISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.setVisibility(View.GONE);
                            Log.e("Post Thread", e.getMessage());
                        }
                    })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }
                            return th.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            dialog.setVisibility(View.GONE);
                            Uri uri = task.getResult();
                            String downloadUrl = uri.toString();
                            Thread tr = new Thread(
                                    judul,
                                    isi,
                                    downloadUrl,
                                    uid,
                                    tg,
                                    ktgr,
                                    currentDate,
                                    key
                            );
                            mRef.child(key).setValue(tr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Postingan berhasil", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Postingan gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
        }if(imgUri == null){
            dialog.setVisibility(View.VISIBLE);
            Thread tr = new Thread(
                    judul,
                    isi,
                    null,
                    uid,
                    tg,
                    ktgr,
                    currentDate,
                    key
            );
            mRef.child(key).setValue(tr).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Postingan berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Postingan gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
