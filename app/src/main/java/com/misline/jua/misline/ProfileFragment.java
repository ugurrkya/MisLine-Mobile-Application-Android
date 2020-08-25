package com.misline.jua.misline;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ImageView image_profile;
    TextView fullname, bio, studentclass, studentnumber, studentmail;
    Button edit_profile;


    private String currentUserId;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


        image_profile = view.findViewById(R.id.image_profile);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        studentclass = view.findViewById(R.id.student_class);
        studentnumber = view.findViewById(R.id.studentnumber);
        studentmail = view.findViewById(R.id.studentmail);
        edit_profile = view.findViewById(R.id.edit_profile);
        UserInfo();

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),EditProfileActivity.class));
            }
        });



        return view;
    }

    private void UserInfo(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                if(isAdded()) {
                    String myProfileImage = dataSnapshot.child("studentphoto").getValue().toString();
                    String myIdentity = dataSnapshot.child("identity").getValue().toString();
                    String myEmail = dataSnapshot.child("email").getValue().toString();
                    String myStudentNumber = dataSnapshot.child("studentnumber").getValue().toString();
                    String myClass = dataSnapshot.child("studentclass").getValue().toString();
                    String myBio = dataSnapshot.child("bio").getValue().toString();
                    Glide.with(getContext()).load(myProfileImage).into(image_profile);
                    fullname.setText(myIdentity);
                    studentnumber.setText(myStudentNumber);
                    studentclass.setText(myClass);
                    studentmail.setText(myEmail);
                    bio.setText(myBio);
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
