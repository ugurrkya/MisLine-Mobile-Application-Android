package com.misline.jua.misline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SharingDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView mLectureNote, mUserName;
    CircleImageView mUserImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mLectureNote = (TextView)findViewById(R.id.dtLectureNote);
        mUserImage = (CircleImageView) findViewById(R.id.dtUserImage);
        mUserName = (TextView) findViewById(R.id.dtUserName);
        String title = getIntent().getStringExtra("title");
        String name = getIntent().getStringExtra("name");
        String userimagee = getIntent().getStringExtra("userimage");

        mLectureNote.setText(title);
        mUserName.setText(name);
        Glide.with(getApplicationContext()).load(userimagee).into(mUserImage);




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(title);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String fileName = "Click to display and download";
                String url = dataSnapshot.child("filelink").getValue(String.class);

                ((MyAdapter)recyclerView.getAdapter()).update(fileName,url);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MyAdapter myAdapter = new MyAdapter(recyclerView, getApplicationContext(), new ArrayList<String>(), new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);
    }
}
