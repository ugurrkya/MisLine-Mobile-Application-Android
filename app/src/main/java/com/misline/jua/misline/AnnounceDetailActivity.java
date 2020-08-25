package com.misline.jua.misline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AnnounceDetailActivity extends AppCompatActivity {

    TextView musername, mtitle, mdescription, mdate;
    ImageView mAnnounceImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_detail);

        musername = (TextView) findViewById(R.id.dtnameofuser);
        mtitle = (TextView) findViewById(R.id.fortitle);
        mdescription = (TextView) findViewById(R.id.fordescription);
        mdate = (TextView) findViewById(R.id.fordate);
        mAnnounceImage = (ImageView) findViewById(R.id.dtannounceimage);

        String titlee = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        String announceimg = getIntent().getStringExtra("announcephoto");
        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");

        musername.setText(name);
        mdescription.setText(desc);
        mdate.setText(date);
        mtitle.setText(titlee);
        Glide.with(getApplicationContext()).load(announceimg).into(mAnnounceImage);

    }
}
