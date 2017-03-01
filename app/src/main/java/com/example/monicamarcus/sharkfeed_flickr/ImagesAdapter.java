package com.example.monicamarcus.sharkfeed_flickr;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.monicamarcus.sharkfeed_flickr.MyApplication.getAppContext;

/**
 * Created by monicamarcus on 2/23/17.
 */

class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    private static ArrayList<Bitmap> images;
    private static ArrayList<String> http_o;
    private static String clickedImageUrl;
    private OnItemClickListener listener;

    public ImagesAdapter(ArrayList<Bitmap> images, ArrayList<String> http_o, OnItemClickListener listener) {
        this.images = images;
        this.http_o = http_o;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder (CardView v) {
            super(v);
            cardView = v;
        }
        public void bind(final Bitmap image, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = images.indexOf(image);
                    clickedImageUrl = http_o.get(index);
                    listener.onItemClick(clickedImageUrl);
                }
            });
        }
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getAppContext(), DetailActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    Bundle bundle = new Bundle();
//                    bundle.putByteArray("image",byteArray);
//                    intent.putExtras(bundle);
//                    MyApplication.getAppContext().startActivity(intent);
//                }
//            });
//        }
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
        } else clickedImageUrl = "";
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Bitmap bitMap = images.get(position);
        imageView.setImageBitmap(bitMap);
        holder.bind(bitMap, listener);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

//https://farm3.staticflickr.com/2919/33012502992_5290caa93d_o.jpg