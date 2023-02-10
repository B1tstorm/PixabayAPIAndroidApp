package de.fh_kiel.iue.mob;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class StaticPicturesAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<String> pictures;

    public StaticPicturesAdapter(Context mContext, ArrayList<String> mPictures) {
        this.context = mContext;
        this.pictures = mPictures;
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
        // Bild wird über die URL aus dem Internet geladen und angezeigt
        //Glide.with(context).load(pictures.get(position)).into(imageView);
        imageView.setImageResource(R.drawable.ic_launcher_foreground);

        return imageView;
    }
}
