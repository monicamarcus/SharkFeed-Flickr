package com.example.monicamarcus.sharkfeed_flickr;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SharkFeedService extends IntentService {
    public static final String myURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=949e98778755d1982f537d56236bbb42&text=shark&format=json&nojsoncallback=1&page=1&extras=url_t,url_c,url_l,url_o";

    public SharkFeedService() {
        super("SharkFeedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String jsonResult = getJsonData(myURL);
            Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
            intent1.putExtra("jsonData", jsonResult);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
    }

    private String getJsonData(String urlString) {
        String result = "";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            int oneChar = 0;
            while ((oneChar = reader.read()) != -1) {
                result += (char) oneChar;
            }
        } catch (MalformedURLException e) {
                //e.getMessage();
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return result;
    }
}