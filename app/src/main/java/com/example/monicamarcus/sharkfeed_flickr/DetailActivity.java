package com.example.monicamarcus.sharkfeed_flickr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.monicamarcus.sharkfeed_flickr.MyApplication.getAppContext;

public class DetailActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        image = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url.length() > 0) {
            Glide.with(getAppContext()).load(url).into(image);
        } else Toast.makeText(this,"The original size image is not available", Toast.LENGTH_LONG).show();
    }
}
