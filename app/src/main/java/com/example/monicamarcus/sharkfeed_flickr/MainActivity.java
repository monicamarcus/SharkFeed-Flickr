package com.example.monicamarcus.sharkfeed_flickr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> http_t = new ArrayList<String>();
    private ArrayList<String> http_o = new ArrayList<String>();
    private ArrayList<Bitmap> myImages = new ArrayList<Bitmap>();
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView rvItems;
    private Button sharkFeedButton;
    private ImagesAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharkFeedButton = (Button) findViewById(R.id.shark_feed_button);

        // Configure the RecyclerView
        rvItems = (RecyclerView) findViewById(R.id.images_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rvItems.setLayoutManager(gridLayoutManager);

        adapter = new ImagesAdapter(myImages);

        Intent intent = getIntent();
        if (intent != null) {
            String jsonData = intent.getStringExtra("jsonData");
            if (jsonData != null) {
                extractInfo(jsonData);
                if (http_t != null) {
                    Intent intent1 = new Intent(this, DownloadImagesService.class);
                    intent1.putExtra("http_t", http_t);
                    startService(intent1);
                }
            } else {
                Log.d("MainActivity onCreate()", "jsonData = " + jsonData);
            }
            ArrayList<String> filePaths = intent.getStringArrayListExtra("files");
            for (int i = 0; filePaths != null && i < filePaths.size(); i++) {
                //File sd = Environment.getExternalStorageDirectory();
                File image = new File(filePaths.get(i));
                image.setReadable(true);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
                //bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                myImages.add(bitmap);
                adapter.notifyDataSetChanged();
            }
        }
        rvItems.setAdapter(adapter);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvItems.addOnScrollListener(scrollListener);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    //Obtain the urls for thumb size images and for original size images
    private void extractInfo(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject2 = jsonObject.getJSONObject("photos");
            JSONArray jsonArray = jsonObject2.getJSONArray("photo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                try {
                    String afterDecode = URLDecoder.decode(jsonPart.getString("url_t"), "UTF-8");
                    http_t.add(i,afterDecode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //http_o.add(i,jsonPart.getString("url_o"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // A click on the button starts this service
    public void getSharkFeed(View view) {
        Intent intent = new Intent(this, SharkFeedService.class);
        startService(intent);
    }

}

