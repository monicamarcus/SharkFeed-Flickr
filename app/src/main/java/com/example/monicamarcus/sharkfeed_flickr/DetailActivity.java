package com.example.monicamarcus.sharkfeed_flickr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        byte[] byteArray = bundle.getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bmp);
    }
}
