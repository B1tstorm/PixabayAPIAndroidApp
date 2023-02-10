package de.fh_kiel.iue.mob.models;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.os.SystemClock;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_kiel.iue.mob.ApiHandler;
import de.fh_kiel.iue.mob.PicturesAdapter;

// Diese Klasse enthält die 100 ausgedachten statischen Daten für die Applikation
public class DataContainer extends AsyncTask<Integer, Integer, Boolean> {

    static final String TAG = "DataContainer";
    GridView gridView;
//    ArrayAdapter<String> adapter;
    PicturesAdapter adapter;
    Context context;
    ApiHandler api;
    Picture picture;
    Boolean dataState;

    public DataContainer(GridView mGridView, PicturesAdapter mAdapter, Context mContext, String searchKey, Boolean mDataState){
        gridView = mGridView;
        adapter = mAdapter;
        context = mContext;
        dataState = mDataState;
        api = new ApiHandler(context, searchKey);
        images.clear();
        views.clear();
        likes.clear();
        picture = new Picture();
    }


    public DataContainer() {

    }

    private static ArrayList<String> images = new ArrayList<>();
    public static ArrayList<String> getImages() { return images; }
    private static ArrayList<String> likes = new ArrayList<>();
    public static ArrayList<String> getLikes() { return likes; }
    private static ArrayList<String> views = new ArrayList<>();
    public static ArrayList<String> getViews() { return views; }


    public static ArrayList<String> getData(int position) {
        ArrayList<String> pictureData = new ArrayList<>();
        pictureData.add(images.get(position));
        pictureData.add(views.get(position));
        pictureData.add(likes.get(position));

        return pictureData;
    }

    public void deleteData() {
        images.clear();
        views.clear();
        likes.clear();
        this.cancel(true);
    }

    @Override
    protected Boolean doInBackground(Integer... aInt) {
        SystemClock.sleep(aInt[1]);
        for (int i=0; i<=aInt[0]; i++){
            if (dataState) {
                if (!api.getDataFromJson(i).isEmpty()) {
                    images.add(api.getDataFromJson(i).get(0));
                    likes.add(api.getDataFromJson(i).get(1));
                    views.add(api.getDataFromJson(i).get(2));
                } else {
                    break;
                }
            } else {
                SystemClock.sleep(aInt[1]);
                images.add("" + getImages().size() + 1);
                likes.add("" + getImages().size() + 1);
                views.add("" + getImages().size() + 1);
            }

            publishProgress(i);
            if (isCancelled()) break;
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.v(TAG, String.format("onProgressUpdate(%d)", progress[0]));
        gridView.setAdapter(adapter);

        if (progress[0] % 50 == 0) {
            Toast toast = Toast.makeText(context, progress[0] + "%", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.v(TAG, String.format("onPostExecute(%b)", result));
        String res;

        if (result) {
            res = "Fertig!";
        } else {
            res = "Fehler";
        }

        gridView.setAdapter(adapter);
        Toast toast = Toast.makeText(context, res, Toast.LENGTH_LONG);
        toast.show();
    }

    // Statische Klasse wie in der Aufgabestellung
    public static class Data {

        private static ArrayList<String> images = new ArrayList<>();
        public static ArrayList<String> getImages() { return images; }
        public static void setImages() { images.add("" + getImages().size()); }

        private static ArrayList<String> likes = new ArrayList<>();
        public static ArrayList<String> getLikes() { return likes; }
        public static void setLikes() {
            likes.add("" + getLikes().size() * 2);
        }

        private static ArrayList<String> views = new ArrayList<>();
        public static ArrayList<String> getViews() { return views; }
        public static void setViews() {
            views.add("" + getViews().size() * 5);
        }

        public static ArrayList<String> getData(int position) {
            ArrayList<String> pictureData = new ArrayList<>();
            pictureData.add(images.get(position));
            pictureData.add(views.get(position));
            pictureData.add(likes.get(position));

            return pictureData;
        }
    }
}
