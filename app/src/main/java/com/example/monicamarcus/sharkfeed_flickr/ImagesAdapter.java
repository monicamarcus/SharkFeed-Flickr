package com.example.monicamarcus.sharkfeed_flickr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.monicamarcus.sharkfeed_flickr.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.example.monicamarcus.sharkfeed_flickr.MyApplication.getAppContext;

/**
 * Created by monicamarcus on 2/23/17.
 */

class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Bitmap image);
    }

    private ArrayList<Bitmap> images;
    private ArrayList<String> http_o; // = new ArrayList<String>();
    private static String clickedImageUrl;
    private OnItemClickListener listener = null;
    private static ImageView imageView;

    public ImagesAdapter(ArrayList<Bitmap> images, ArrayList<String> http_o) { //, OnItemClickListener listener) {
        this.images = images;
        this.http_o = http_o;
        //this.listener = listener;
    }

    public ImagesAdapter(ArrayList<Bitmap> images) {
        this.images = images;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder (CardView v) {
            super(v);
            cardView = v;
        }
        public void bind(final Bitmap image, final OnItemClickListener listener) {
            if (clickedImageUrl != null && clickedImageUrl.length() > 0)
                Glide.with(itemView.getContext()).load(clickedImageUrl).into(imageView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(image);
//                }
//            });
//        }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getAppContext(), DetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("image",byteArray);
                    intent.putExtras(bundle);
                    MyApplication.getAppContext().startActivity(intent);
                }
            });
        }
    }


    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (http_o.size() > position) {
            clickedImageUrl = http_o.get(position);
            Log.d("ImagesAdapter", "HERE position = " + position);
        }
        CardView cardView = holder.cardView;
        imageView = (ImageView) cardView.findViewById(R.id.image);
        Bitmap bitMap = images.get(position);
        imageView.setImageBitmap(bitMap);
        holder.bind(bitMap, listener);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
