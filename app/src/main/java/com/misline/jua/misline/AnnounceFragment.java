
package com.misline.jua.misline;


import android.content.Intent;
import android.graphics.Color;
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

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnnounceFragment extends Fragment {
    RecyclerView mRecyclerView;
    Button btn_announce;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    DatabaseReference mRef;
    FirebaseDatabase mfirebaseDatabase;
    Button societybt, eventbt, internbt;
    private String d;
    DatabaseReference mIntern;
    DatabaseReference mSociety;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announce, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = getView().findViewById(R.id.toolbar);

        societybt = (Button) getView().findViewById(R.id.societybutton);
        eventbt = (Button) getView().findViewById(R.id.eventbutton);
        internbt = (Button) getView().findViewById(R.id.internshipbutton);
        btn_announce = (Button) getView().findViewById(R.id.btn_announce);
        btn_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AnnounceActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        //RecyclerView
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mfirebaseDatabase = FirebaseDatabase.getInstance();



        mRef = mfirebaseDatabase.getReference().child("Event");
        mIntern = mfirebaseDatabase.getReference().child("Internship");
        mSociety = mfirebaseDatabase.getReference().child("Society");

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


        eventbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String d = mRef.push().getKey();
                Query eventss = mRef.orderByChild("counter");






                FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce>(
                                announceModel.class,
                                R.layout.rowannounce,
                                ViewHolderAnnounce.class,
                                eventss
                        ) {
                            @Override
                            protected void populateViewHolder(ViewHolderAnnounce viewHolder, announceModel announceModel, int position) {

                                viewHolder.setDetails(getActivity().getApplicationContext(), announceModel.getName(), announceModel.getTitle(),
                                        announceModel.getUserimage(),announceModel.getAnnouncephoto(), announceModel.getDescription(),announceModel.getDate()


                                );

                            }

                            @Override
                            public ViewHolderAnnounce onCreateViewHolder(ViewGroup parent, int viewType) {

                                ViewHolderAnnounce viewHolder = super.onCreateViewHolder(parent, viewType);


                                viewHolder.setOnClickListener(new ViewHolderAnnounce.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //Views


                                        //get data from views

                                        String mTitleName = getItem(position).getTitle();
                                        String mNoterNameView =getItem(position).getName();
                                        String mUserImage =getItem(position).getUserimage();
                                        String mDate = getItem(position).getDate();
                                        String mDescription = getItem(position).getDescription();
                                        String mPhoto = getItem(position).getAnnouncephoto();

                                        //pass this data to new activity
                                        Intent intent = new Intent(view.getContext(),AnnounceDetailActivity.class);
                                        intent.putExtra("title", mTitleName);
                                        intent.putExtra("name",mNoterNameView);
                                        intent.putExtra("userimage",mUserImage);
                                        intent.putExtra("announcephoto", mPhoto);
                                        intent.putExtra("date", mDate);
                                        intent.putExtra("description", mDescription);
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
                internbt.setBackgroundColor(Color.TRANSPARENT);
                societybt.setBackgroundColor(Color.TRANSPARENT);
                eventbt.setBackgroundColor(getResources().getColor(R.color.butoncolor));

            }
        });
        internbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query eventss = mIntern.orderByChild("counter");



                FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce>(
                                announceModel.class,
                                R.layout.rowannounce,
                                ViewHolderAnnounce.class,
                                eventss
                        ) {
                            @Override
                            protected void populateViewHolder(ViewHolderAnnounce viewHolder, announceModel announceModel, int position) {

                                viewHolder.setDetails(getActivity().getApplicationContext(), announceModel.getName(), announceModel.getTitle(),
                                        announceModel.getUserimage(), announceModel.getAnnouncephoto(),announceModel.getDescription(),announceModel.getDate()


                                );

                            }

                            @Override
                            public ViewHolderAnnounce onCreateViewHolder(ViewGroup parent, int viewType) {

                                ViewHolderAnnounce viewHolder = super.onCreateViewHolder(parent, viewType);


                                viewHolder.setOnClickListener(new ViewHolderAnnounce.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //Views


                                        //get data from views

                                        String mTitleName = getItem(position).getTitle();
                                        String mNoterNameView =getItem(position).getName();
                                        String mUserImage =getItem(position).getUserimage();
                                        String mDate = getItem(position).getDate();
                                        String mDescription = getItem(position).getDescription();
                                        String mPhoto = getItem(position).getAnnouncephoto();
                                        //pass this data to new activity
                                        Intent intent = new Intent(view.getContext(),AnnounceDetailActivity.class);
                                        intent.putExtra("title", mTitleName);
                                        intent.putExtra("name",mNoterNameView);
                                        intent.putExtra("userimage",mUserImage);
                                        intent.putExtra("description", mDescription);
                                        intent.putExtra("date", mDate);
                                        intent.putExtra("announcephoto", mPhoto);
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
                internbt.setBackgroundColor(getResources().getColor(R.color.butoncolor));
                societybt.setBackgroundColor(Color.TRANSPARENT);
                eventbt.setBackgroundColor(Color.TRANSPARENT);

            }
        });
        societybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query eventss = mSociety.orderByChild("counter");



                FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce>(
                                announceModel.class,
                                R.layout.rowannounce,
                                ViewHolderAnnounce.class,
                                eventss
                        ) {
                            @Override
                            protected void populateViewHolder(ViewHolderAnnounce viewHolder, announceModel announceModel, int position) {

                                viewHolder.setDetails(getActivity().getApplicationContext(), announceModel.getName(), announceModel.getTitle(),
                                        announceModel.getUserimage(),announceModel.getAnnouncephoto(), announceModel.getDescription(),announceModel.getDate()


                                );

                            }

                            @Override
                            public ViewHolderAnnounce onCreateViewHolder(ViewGroup parent, int viewType) {

                                ViewHolderAnnounce viewHolder = super.onCreateViewHolder(parent, viewType);


                                viewHolder.setOnClickListener(new ViewHolderAnnounce.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //Views


                                        //get data from views

                                        String mTitleName = getItem(position).getTitle();
                                        String mNoterNameView =getItem(position).getName();
                                        String mUserImage =getItem(position).getUserimage();
                                        String mDate = getItem(position).getDate();
                                        String mDescription = getItem(position).getDescription();
                                        String mPhoto = getItem(position).getAnnouncephoto();

                                        //pass this data to new activity
                                        Intent intent = new Intent(view.getContext(),AnnounceDetailActivity.class);
                                        intent.putExtra("title", mTitleName);
                                        intent.putExtra("name",mNoterNameView);
                                        intent.putExtra("userimage",mUserImage);
                                        intent.putExtra("description", mDescription);
                                        intent.putExtra("date", mDate);
                                        intent.putExtra("announcephoto", mPhoto);
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
                societybt.setBackgroundColor(getResources().getColor(R.color.butoncolor));
                eventbt.setBackgroundColor(Color.TRANSPARENT);
                internbt.setBackgroundColor(Color.TRANSPARENT);

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Query eventss = mRef.orderByChild("counter");






        FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<announceModel, ViewHolderAnnounce>(
                        announceModel.class,
                        R.layout.rowannounce,
                        ViewHolderAnnounce.class,
                        eventss
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderAnnounce viewHolder, announceModel announceModel, int position) {

                        viewHolder.setDetails(getActivity().getApplicationContext(), announceModel.getName(), announceModel.getTitle(),
                                announceModel.getUserimage(),announceModel.getAnnouncephoto(), announceModel.getDescription(),announceModel.getDate()


                        );

                    }

                    @Override
                    public ViewHolderAnnounce onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolderAnnounce viewHolder = super.onCreateViewHolder(parent, viewType);


                        viewHolder.setOnClickListener(new ViewHolderAnnounce.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views


                                //get data from views

                                String mTitleName = getItem(position).getTitle();
                                String mNoterNameView =getItem(position).getName();
                                String mUserImage =getItem(position).getUserimage();
                                String mDate = getItem(position).getDate();
                                String mDescription = getItem(position).getDescription();
                                String mPhoto = getItem(position).getAnnouncephoto();

                                //pass this data to new activity
                                Intent intent = new Intent(view.getContext(),AnnounceDetailActivity.class);
                                intent.putExtra("title", mTitleName);
                                intent.putExtra("name",mNoterNameView);
                                intent.putExtra("userimage",mUserImage);
                                intent.putExtra("announcephoto", mPhoto);
                                intent.putExtra("date", mDate);
                                intent.putExtra("description", mDescription);
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
        internbt.setBackgroundColor(Color.TRANSPARENT);
        societybt.setBackgroundColor(Color.TRANSPARENT);
        eventbt.setBackgroundColor(getResources().getColor(R.color.butoncolor));
    }

    @Override
    public void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }
}
