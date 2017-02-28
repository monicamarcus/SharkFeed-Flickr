package com.example.monicamarcus.sharkfeed_flickr;

import android.app.IntentService;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DownloadImagesService extends IntentService {

    public DownloadImagesService() {
        super("DownloadImagesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> files = new ArrayList<String>();
            ArrayList<String> http_t = intent.getStringArrayListExtra("http_t");
            //ArrayList<String> http_o = intent.getStringArrayListExtra("http_o");
            if (http_t != null && http_t.size() > 0) {
                try {
                    for (int i = 0; i < http_t.size(); i++) {
                        FutureTarget<File> future = Glide.with(getApplicationContext())
                                .load(http_t.get(i))
                                .downloadOnly(200, 200);
                        File cacheFile = future.get();
                        files.add(cacheFile.getPath());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            Intent intent2 = new Intent(this.getApplicationContext(), MainActivity.class);
            intent2.putExtra("files", files);
            //intent2.putExtra("http_o", http_o);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }

}
