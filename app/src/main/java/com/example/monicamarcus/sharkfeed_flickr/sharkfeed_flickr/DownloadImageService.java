package com.example.monicamarcus.sharkfeed_flickr.sharkfeed_flickr;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadImageService extends IntentService {

    public DownloadImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bitmap myImage = null;
        ArrayList<ByteArrayOutputStream> imagesBytes = new ArrayList<ByteArrayOutputStream>();
        if (intent != null) {
            HttpURLConnection conn = null;
            ArrayList<String> http_t = intent.getStringArrayListExtra("http_t");
            if (http_t != null && http_t.size() > 0) {
                try {
                    for (int i = 0; i < http_t.size(); i++) {
                        URL url = new URL(http_t.get(i));
                        conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        myImage = BitmapFactory.decodeStream(is);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        myImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imagesBytes.add(stream);
                        //bytes = stream.toByteArray();
                    }
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        if (conn != null) conn.disconnect();
                }
            }
            Intent intent2 = new Intent(this.getApplicationContext(), MainActivity.class);
            intent2.putExtra("image_bytes", imagesBytes);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }
}
