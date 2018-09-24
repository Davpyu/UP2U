package com.surya.david.up2you;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
//    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("threads");
        mStorage = FirebaseStorage.getInstance();
        mSRef = mStorage.getReference("threads");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        toolbar.setTitle("Post Thread");
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
//        dialog = new ProgressDialog(getApplicationContext());
//        dialog.setCancelable(false);
//        dialog.setMessage("Uploading your post");
        final String judul = addJudul.getText().toString().trim();
        final String isi = isithread.getText().toString().trim();
        final String uid = mUser.getUid();
        final StorageReference th = mSRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
        if (judul.isEmpty()){
            addJudul.setError("Please add title of this thread");
            addJudul.requestFocus();
            return;
        }
        if (isi.isEmpty()){
            isithread.setError("Please add content of this thread");
            isithread.requestFocus();
            return;
        }
        if (imgUri != null){
            th.putFile(imgUri)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            dialog.setProgress(0);
//                            dialog.hide();
                            Log.e("Post Thread", e.getMessage());
                        }
                    })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw Objects.requireNonNull(task.getException());
                            }
                            return th.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = task.getResult();
                            String downloadUrl = uri.toString();
                            Thread tr = new Thread(
                                    judul,
                                    isi,
                                    downloadUrl,
                                    uid
                            );
                            mRef.child(Objects.requireNonNull(mRef.push().getKey())).setValue(tr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Postingan berhasil", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Postingan gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
