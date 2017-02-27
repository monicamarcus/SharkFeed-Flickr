package com.example.monicamarcus.sharkfeed_flickr;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by monicamarcus on 2/23/17.
 */

class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private ArrayList<Bitmap> images;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder (CardView v) {
            super(v);
            cardView = v;
        }
    }
    //??????? Never CALLED
    public void add(int position, Bitmap item) {
        images.add(position, item);
        notifyItemInserted(position);
    }

    public ImagesAdapter(ArrayList<Bitmap> images) {
        this.images = images;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("ImagesAdapter","HERE onBindViewHolder position = " + position);
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Bitmap bitMap = images.get(position);
        imageView.setImageBitmap(bitMap);
    }

    @Override
    public int getItemCount() {
        Log.d("ImagesAdapter", "HERE images.size() = " + images.size());
        return images.size();
    }
}
