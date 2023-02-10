package de.fh_kiel.iue.mob;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.fh_kiel.iue.mob.models.DataContainer;

public class ApiHandler {

    public static final String TAG = "ApiHandler";
    String jsonString;
    String searchKey;
    Context context;
    RequestQueue requestQueue;

    // Konstruktor welcher schon vorab die API anfragt
    public ApiHandler(Context mContext, String mSearchKey) {
        jsonString = "";
        this.context = mContext;
        this.searchKey = mSearchKey;
        requestQueue = Volley.newRequestQueue(context);

        String url = "https://pixabay.com/api/?key=18916574-4465da103db52bde53732eec2&q=" + searchKey + "&image_type=photo&pretty=true&per_page=100";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonString = response;

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error fetching content: " + error.getMessage());
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener){
            @Override
            public Priority getPriority(){
                return Priority.IMMEDIATE;
            }
        };

        requestQueue.add(stringRequest);
    }

    public ArrayList<String> getDataFromJson(Integer index) {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray hits = jsonObject.getJSONArray("hits");
            list.add(0, hits.getJSONObject(index).getString("webformatURL"));
            list.add(1, hits.getJSONObject(index).getString("likes"));
            list.add(2, hits.getJSONObject(index).getString("views"));
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

        return list;
    }

}
