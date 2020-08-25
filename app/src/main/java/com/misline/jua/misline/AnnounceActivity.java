package com.misline.jua.misline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AnnounceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int ImageBack = 1;

    private Uri ImageData =null;
    Button announce,choose;
    EditText titlename;
    TextInputEditText information;
    private TextView timeof;
    private Spinner dtlist;
    private CircleImageView profileimag;
    private TextView username;
    DatabaseReference reference;
    private FirebaseUser fuser;
    private String myPhoto;
    private DatabaseReference PostsRef;
    private ProgressDialog progressDialog;
    private long countPosts =0;
    private ImageView announcephoto;

    DatabaseReference databaseReference;
    private StorageReference Folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        dtlist = findViewById(R.id.dtlistname);
        announcephoto = findViewById(R.id.announcephoto);
        titlename  = findViewById(R.id.titlename);
        information = findViewById(R.id.information);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        timeof = findViewById(R.id.timeof);
        timeof.setText(currentDate);
        choose = findViewById(R.id.chooser);
        announce = findViewById(R.id.announce_button);
        profileimag = findViewById(R.id.profileimage);
        username = findViewById(R.id.nameofuser);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lists,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dtlist.setAdapter(adapter);
        dtlist.setOnItemSelectedListener(this);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String as= String.valueOf(dtlist.getSelectedItem().toString());
        PostsRef = FirebaseDatabase.getInstance().getReference().child(as);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countPosts = dataSnapshot.getChildrenCount();

                } else {
                    countPosts = 0;
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    myPhoto = dataSnapshot.child("studentphoto").getValue().toString();
                    Glide.with(getApplicationContext()).load(user.getStudentphoto()).into(profileimag);
                    String myIdentity = dataSnapshot.child("identity").getValue().toString();
                    username.setText(myIdentity);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressDialog = new ProgressDialog(this);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                startActivityForResult(intent,ImageBack);

            }

        });

        announce.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")

            @Override

            public void onClick(View view) {
                String as= String.valueOf(dtlist.getSelectedItem().toString());
                PostsRef = FirebaseDatabase.getInstance().getReference().child(as);
                PostsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            countPosts = dataSnapshot.getChildrenCount();

                        } else {
                            countPosts = 0;
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });




                String s = String.valueOf(dtlist.getSelectedItem());
                Query query = FirebaseDatabase.getInstance().getReference().child(s).orderByChild("title")
                        .equalTo(String.valueOf(titlename.getText()));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            Toast.makeText(AnnounceActivity.this, "Choose a different title name", Toast.LENGTH_SHORT).show();
                        } else if (ImageData == null) {
                            Toast.makeText(AnnounceActivity.this, "You didn't choose an image", Toast.LENGTH_SHORT).show();
                        } else {
                            if (String.valueOf(titlename.getText()).equals("")) {
                                Toast.makeText(AnnounceActivity.this, "Please type a title name", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(titlename.getText()).contains(".")) {
                                Toast.makeText(AnnounceActivity.this, "You should use *,* instead of *.*", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(information.getText()).equals("")) {
                                Toast.makeText(AnnounceActivity.this, "You should fill description area", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(titlename.getText()).contains("#")) {
                                Toast.makeText(AnnounceActivity.this, "You can't use special characters in the title", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(titlename.getText()).contains("$")) {
                                Toast.makeText(AnnounceActivity.this, "You can't use special characters in the title", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(titlename.getText()).contains("[")) {
                                Toast.makeText(AnnounceActivity.this, "You can't use special characters in the title", Toast.LENGTH_SHORT).show();
                            } else if (String.valueOf(titlename.getText()).contains("]")) {
                                Toast.makeText(AnnounceActivity.this, "You can't use special characters in the title", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.setMessage("Announcement is Loading, Please Wait...........");
                                progressDialog.show();


                                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("FileFolder");
                                Uri IndividualFile = ImageData;


                                final StorageReference ImageName = ImageFolder.child("Image" + IndividualFile.getLastPathSegment());
                                ImageName.putFile(IndividualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override

                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {


                                            @Override

                                            public void onSuccess(Uri uri) {
                                                String as= String.valueOf(dtlist.getSelectedItem().toString());
                                                PostsRef = FirebaseDatabase.getInstance().getReference().child(as);
                                                PostsRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            countPosts = dataSnapshot.getChildrenCount();

                                                        } else {
                                                            countPosts = 0;
                                                        }

                                                    }


                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }

                                                });


                                                final String spin = String.valueOf(dtlist.getSelectedItem().toString());


                                                String url = String.valueOf(uri);

                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                HashMap hashMp = new HashMap();

                                                hashMp.put("name", String.valueOf(username.getText()));
                                                hashMp.put("userimage", myPhoto);
                                                hashMp.put("title", String.valueOf(titlename.getText()));
                                                hashMp.put("counter", countPosts);
                                                hashMp.put("description", information.getText().toString());
                                                hashMp.put("date", timeof.getText().toString());
                                                hashMp.put("announcephoto", url);

                                                databaseReference.child(spin).child(String.valueOf(titlename.getText())).setValue(hashMp);


                                            }

                                        });

                                        Toast.makeText(AnnounceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        announce.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                    }

                                });


                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageBack){
            if(resultCode == RESULT_OK){
              ImageData = data.getData();
                announcephoto.setImageURI(ImageData);
            }
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}