package com.misline.jua.misline;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class SharingsFragment extends Fragment {
    private DatabaseReference checkRef;
    RecyclerView mRecyclerView;
    Button btn_share;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    private String title;
    private String title2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sharings, container, false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = getView().findViewById(R.id.toolbar);



        btn_share = (Button) getView().findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShareActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        //RecyclerView
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //send query to FB
        mfirebaseDatabase = FirebaseDatabase.getInstance();




        mRef = mfirebaseDatabase.getReference("Titles");



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    final String userId = user.getUid();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(userId)) {


                            } else {

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {

                }
            }

        };

        mAuth.addAuthStateListener(mAuthListener);



        Query Sorts = mRef.orderByChild("counter");



        FirebaseRecyclerAdapter<titleModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<titleModel, ViewHolder>(
                        titleModel.class,
                        R.layout.row,
                        ViewHolder.class,
                        Sorts
                ){
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, titleModel titlemodel, int position) {

                        viewHolder.setDetails(getActivity().getApplicationContext(), titlemodel.getTitle(),
                                titlemodel.getName(), titlemodel.getUserimage());

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);


                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                String mShareNameView = getItem(position).getTitle();
                                String mNoterNameView =getItem(position).getName();
                                String mUserImage =getItem(position).getUserimage();


                                //pass this data to new activity
                                Intent intent = new Intent(view.getContext(),SharingDetailsActivity.class);
                                intent.putExtra("title", mShareNameView);
                                intent.putExtra("name",mNoterNameView);
                                intent.putExtra("userimage",mUserImage);
                                startActivity(intent);
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO
                            }
                        });

                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);



    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
