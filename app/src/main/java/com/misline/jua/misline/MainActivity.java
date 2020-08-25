package com.misline.jua.misline;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference profileUserRef;

    private String currentUserId;
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private TextView nameuser, studentnumber,classnumber,email;
    private CircleImageView imageuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    imageuser = (CircleImageView) findViewById(R.id.nav_studentimage);
                    nameuser =  (TextView)findViewById(R.id.nav_identity);
                    studentnumber =  (TextView)findViewById(R.id.nav_studentnumber);
                    classnumber =  (TextView)findViewById(R.id.nav_studentclass);
                    email =  (TextView)findViewById(R.id.nav_student_mail);
                    String myProfileImage = dataSnapshot.child("studentphoto").getValue().toString();
                    String myIdentity = dataSnapshot.child("identity").getValue().toString();
                    String myEmail= dataSnapshot.child("email").getValue().toString();
                    String myStudentNumber = dataSnapshot.child("studentnumber").getValue().toString();
                    String myClass = dataSnapshot.child("studentclass").getValue().toString();
                    Glide.with(getApplicationContext()).load(myProfileImage).into(imageuser);
                    nameuser.setText(myIdentity);
                    studentnumber.setText(myStudentNumber);
                    classnumber.setText(myClass);
                    email.setText(myEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AnnouncementsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_announcements);}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_announcements:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AnnouncementsFragment()).commit();
                break;
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AnnounceFragment()).commit();
                break;
            case R.id.nav_messages:
                Intent i = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(i);
                break;
            case R.id.nav_sharings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SharingsFragment()).commit();
                break;
            case R.id.nav_signout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SignOutFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}
