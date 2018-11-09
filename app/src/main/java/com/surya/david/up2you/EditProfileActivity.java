package com.surya.david.up2you;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private final int PICK_IMAGE_PROFILE = 0;
    String uid;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nmProfile)
    EditText nmProfile;
    @BindView(R.id.bi)
    TextView bi;
    @BindView(R.id.bio)
    EditText bio;
    @BindView(R.id.edit_profile)
    DrawerLayout editProfile;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference mSRef;
    private Uri imgUri;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        toolbar.setTitle("Update Profile");
        uid = getIntent().getStringExtra(ProfileActivity.DATA);
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mRef = mDatabase.getReference("Users");
        mSRef = mStorage.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        configureToolbar();
        configureUser();
    }

    @Override
    public void onBackPressed() {
        final String ui = mUser.getUid();
        if (uid != null){
            mRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    String displayname = ur.getName();
                    String displaybio = ur.getBio();
                    String newname = nmProfile.getText().toString().trim();
                    String newbio = bio.getText().toString().trim();
                    if (!newname.equals(displayname) || !newbio.equals(displaybio) || imgUri != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                        builder.setMessage("Are you sure you want to exit?")
                                .setCancelable(false)
                                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Back Pressed", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }else{
            mRef.child(ui).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    String displayname = ur.getName();
                    String displaybio = ur.getBio();
                    String newname = nmProfile.getText().toString().trim();
                    String newbio = bio.getText().toString().trim();
                    if (!newname.equals(displayname) || !newbio.equals(displaybio) || imgUri != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                        builder.setMessage("Are you sure you want to exit?")
                                .setCancelable(false)
                                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Back Pressed", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }
    }

    private void configureUser() {
        if (uid != null){
            mRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    nmProfile.setText(Objects.requireNonNull(ur).getName());
                    String bo = ur.getBio();
                    if (bo != null && !bo.equals("")) {
                        bio.setText(bo);
                    } else if (bo == null || bo.equals("")) {
                        if (ur.getJen_kel().equals("Laki Laki")) {
                            bio.setText(R.string.bionullhim);
                        }
                        if (ur.getJen_kel().equals("Perempuan")) {
                            bio.setText(R.string.bionullher);
                        }
                    }
                    String ft = ur.getFoto();
                    if (ft != null && !ft.equals("")) {
                        Picasso.get().load(ft).into(imgProfile, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("Profile", e.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }else {
            final String ui = mUser.getUid();
            mRef.child(ui).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user ur = dataSnapshot.getValue(user.class);
                    nmProfile.setText(Objects.requireNonNull(ur).getName());
                    String bo = ur.getBio();
                    if (bo != null && !bo.equals("")) {
                        bio.setText(bo);
                    } else if (bo == null || bo.equals("")) {
                        if (ur.getJen_kel().equals("Laki Laki")) {
                            bio.setText(R.string.bionullhim);
                        }
                        if (ur.getJen_kel().equals("Perempuan")) {

                            bio.setText(R.string.bionullher);
                        }
                    }
                    String ft = ur.getFoto();
                    if (ft != null && !ft.equals("")) {
                        Picasso.get().load(ft).into(imgProfile, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("Profile", e.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                }
            });
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ui = mUser.getUid();
                if (uid != null){
                    mRef.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user ur = dataSnapshot.getValue(user.class);
                            String displayname = ur.getName();
                            String displaybio = ur.getBio();
                            String newname = nmProfile.getText().toString().trim();
                            String newbio = bio.getText().toString().trim();
                            if (!newname.equals(displayname) || !newbio.equals(displaybio) || imgUri != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                                builder.setMessage("Are you sure you want to exit?")
                                        .setCancelable(false)
                                        .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Back Pressed", databaseError.getCode() + "" + databaseError.getMessage());
                        }
                    });
                }else{
                    mRef.child(ui).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user ur = dataSnapshot.getValue(user.class);
                            String displayname = ur.getName();
                            String displaybio = ur.getBio();
                            String newname = nmProfile.getText().toString().trim();
                            String newbio = bio.getText().toString().trim();
                            if (!newname.equals(displayname) || !newbio.equals(displaybio) || imgUri != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                                builder.setMessage("Discard Changes?")
                                        .setCancelable(false)
                                        .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Back Pressed", databaseError.getCode() + "" + databaseError.getMessage());
                        }
                    });
                }
            }
        });
        editProfile.setStatusBarBackgroundColor(getResources().getColor(R.color.statusbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tickitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String username = nmProfile.getText().toString().trim();
        final String desc = bio.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        final String ui = mUser.getUid();
        if (uid != null){
            if (!username.equals("")) {
                if (imgUri != null) {
                    final StorageReference pp = mSRef.child(uid).child(uid + "_1." + getFileExtension(imgUri));
                    pp.putFile(imgUri)
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.show();
                                }
                            })
                            .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw Objects.requireNonNull(task.getException());
                                    }
                                    return pp.getDownloadUrl();
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    final String fotoprofil = task.getResult().toString();
                                    mRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            progressDialog.hide();
                                            dataSnapshot.getRef().child("name").setValue(username);
                                            if (!desc.equals("")){
                                                dataSnapshot.getRef().child("bio").setValue(desc);
                                            }else if (desc.equals("")){
                                                dataSnapshot.getRef().child("bio").setValue("");
                                            }
                                            dataSnapshot.getRef().child("foto").setValue(fotoprofil);
                                            Toast.makeText(EditProfileActivity.this, "Profile Updated !!!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                                        }
                                    });
                                }
                            });
                } else {
                    mRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressDialog.hide();
                            dataSnapshot.getRef().child("name").setValue(username);
                            if (!desc.equals("")){
                                dataSnapshot.getRef().child("bio").setValue(desc);
                            }else if (desc.equals("")){
                                dataSnapshot.getRef().child("bio").setValue("");
                            }
                            Toast.makeText(EditProfileActivity.this, "Profile Updated !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                        }
                    });
                }
            } else {
                if (username.equals("")) {
                    nmProfile.setError("Please fill this field");
                    nmProfile.requestFocus();
                }
            }
        }
        if (uid == null) {
            if (!username.equals("")) {
                if (imgUri != null) {
                    final StorageReference pp = mSRef.child(ui).child(ui + "_1." + getFileExtension(imgUri));
                    pp.putFile(imgUri)
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.show();
                                }
                            })
                            .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw Objects.requireNonNull(task.getException());
                                    }
                                    return pp.getDownloadUrl();
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    final String fotoprofil = task.getResult().toString();
                                    mRef.child(ui).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            progressDialog.hide();
                                            dataSnapshot.getRef().child("name").setValue(username);
                                            if (!desc.equals("")){
                                                dataSnapshot.getRef().child("bio").setValue(desc);
                                            }else if (desc.equals("")){
                                                dataSnapshot.getRef().child("bio").setValue("");
                                            }
                                            dataSnapshot.getRef().child("foto").setValue(fotoprofil);
                                            Toast.makeText(EditProfileActivity.this, "Profile Updated !!!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                                        }
                                    });
                                }
                            });
                } else {
                    mRef.child(ui).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressDialog.hide();
                            dataSnapshot.getRef().child("name").setValue(username);
                            if (!desc.equals("")){
                                dataSnapshot.getRef().child("bio").setValue(desc);
                            }else if (desc.equals("")){
                                dataSnapshot.getRef().child("bio").setValue("");
                            }
                            Toast.makeText(EditProfileActivity.this, "Profile Updated !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Profile", databaseError.getCode() + "" + databaseError.getMessage());
                        }
                    });
                }
            } else {
                if (username.equals("")) {
                    nmProfile.setError("Please fill this field");
                    nmProfile.requestFocus();
                }
            }
        }
        return true;
    }

    @OnClick(R.id.img_profile)
    public void onImgProfileClicked() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_PROFILE);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_PROFILE) {
            if (resultCode == RESULT_OK && data != null) {
                imgUri = data.getData();
                Picasso.get().load(imgUri).into(imgProfile);
            }
        }
    }
}
