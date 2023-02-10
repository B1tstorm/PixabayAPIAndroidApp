package de.fh_kiel.iue.mob;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class PicturesAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<String> pictures;
    private Boolean dataState;

    public PicturesAdapter(Context mContext, ArrayList<String> mPictures, Boolean mDataState) {
        this.context = mContext;
        this.pictures = mPictures;
        this.dataState = mDataState;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);

        if(dataState) {
            // Bild wird über die URL aus dem Internet geladen und angezeigt
            Glide.with(context).load(pictures.get(position)).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        return imageView;
    }
}
