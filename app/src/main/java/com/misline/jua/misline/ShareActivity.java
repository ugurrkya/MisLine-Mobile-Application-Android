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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ShareActivity extends AppCompatActivity {
    private long countPosts =0;
    private DatabaseReference PostsRef;
    private static final int PICK_IMAGE = 1;
    Button upload,choose;
    EditText sharetitle;
    TextView alert;
    private CircleImageView profileimage;
    private TextView name;
    private FirebaseUser fuser;
    ArrayList<Uri> FileList = new ArrayList<Uri>();
    private Uri FileUri;
    private ProgressDialog progressDialog;
    private int upload_count = 0;
    DatabaseReference reference;
    private String myPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        sharetitle = findViewById(R.id.share_title);
        alert = findViewById(R.id.alert);
        upload = findViewById(R.id.upload_image);
        choose = findViewById(R.id.chooser);
        profileimage = findViewById(R.id.profileimage);
        name = findViewById(R.id.name);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
       PostsRef = FirebaseDatabase.getInstance().getReference().child("Titles");
        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    countPosts = dataSnapshot.getChildrenCount();
                }
                else{
                    countPosts=0;
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
                    Glide.with(getApplicationContext()).load(user.getStudentphoto()).into(profileimage);
                    String myIdentity = dataSnapshot.child("identity").getValue().toString();
                    name.setText(myIdentity);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressDialog = new ProgressDialog(ShareActivity.this);
        progressDialog.setMessage("File Uploading Please Wait...........");
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,PICK_IMAGE);

            }

        });


        upload.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")

            @Override

            public void onClick(View view) {


                Query query = FirebaseDatabase.getInstance().getReference().child("Titles").orderByChild("title")
                        .equalTo(String.valueOf(sharetitle.getText()));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>0){
                            Toast.makeText(ShareActivity.this, "Choose a different title name", Toast.LENGTH_SHORT).show();
                        }else if(FileList.size()== 0){
                            Toast.makeText(ShareActivity.this, "You didn't choose file(s)", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(String.valueOf(sharetitle.getText()).equals("") ){
                                Toast.makeText(ShareActivity.this, "Please type a title name", Toast.LENGTH_SHORT).show();
                            }
                            else if(String.valueOf(sharetitle.getText()).contains(".")){
                                Toast.makeText(ShareActivity.this, "You should use *,* instead of *.*" , Toast.LENGTH_SHORT).show();
                            }
                            else if(String.valueOf(sharetitle.getText()).contains("#")){
                                Toast.makeText(ShareActivity.this, "You can't use special characters in the title" , Toast.LENGTH_SHORT).show();
                            }
                            else if(String.valueOf(sharetitle.getText()).contains("$")){
                                Toast.makeText(ShareActivity.this, "You can't use special characters in the title" , Toast.LENGTH_SHORT).show();
                            }
                            else if(String.valueOf(sharetitle.getText()).contains("[")){
                                Toast.makeText(ShareActivity.this, "You can't use special characters in the title" , Toast.LENGTH_SHORT).show();
                            }
                            else if(String.valueOf(sharetitle.getText()).contains("]")){
                                Toast.makeText(ShareActivity.this, "You can't use special characters in the title" , Toast.LENGTH_SHORT).show();
                            }

                            else{
                                progressDialog.show();
                                alert.setText("If Loading Takes too long please Press the button again");

                                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("FileFolder");
                                for(upload_count = 0; upload_count < FileList.size(); upload_count++){
                                    Uri IndividualFile = FileList.get(upload_count);
                                    final StorageReference ImageName = ImageFolder.child("Image"+IndividualFile.getLastPathSegment());
                                    ImageName.putFile(IndividualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override

                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                                @Override

                                                public void onSuccess(Uri uri) {
                                                    PostsRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                countPosts = dataSnapshot.getChildrenCount();
                                                            }
                                                            else{
                                                                countPosts=0;
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                    String url = String.valueOf(uri);
                                                    StoreLink(url);
                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                    HashMap hashMp = new HashMap();

                                                    hashMp.put("name", String.valueOf(name.getText()));
                                                    hashMp.put("userimage", myPhoto);
                                                    hashMp.put("title", String.valueOf(sharetitle.getText()));
                                                    hashMp.put("counter", countPosts);
                                                    databaseReference.child("Titles").child(String.valueOf(sharetitle.getText())).setValue(hashMp);


                                                }

                                            });

                                        }

                                    });

                                }

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
    private void StoreLink(String url) {
        //.child(String.valueOf(sharetitle.getText()))
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("filelink",url);
        databaseReference.child("Notes").child(String.valueOf(sharetitle.getText())).push().setValue(hashMap);

        progressDialog.dismiss();
        alert.setText("File Uploaded Successfully");
        upload.setVisibility(View.GONE);
        FileList.clear();

    }


    @SuppressLint("SetTextI18n")

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){

            if(resultCode == RESULT_OK){

                if(data.getClipData() != null){

                    int countClipData = data.getClipData().getItemCount();

                    int currentImageSelect = 0;

                    while (currentImageSelect < countClipData){


                        FileUri = data.getClipData().getItemAt(currentImageSelect).getUri();

                        FileList.add(FileUri);

                        currentImageSelect = currentImageSelect +1;

                    }

                    alert.setVisibility(View.VISIBLE);

                    alert.setText("You Have Selected "+ FileList.size() +" Files");

                    choose.setVisibility(View.GONE);

                }else{

                    Toast.makeText(this, "Please Select Multiple File", Toast.LENGTH_SHORT).show();

                }
            }

        }

    }
}
