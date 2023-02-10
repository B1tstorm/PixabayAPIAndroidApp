package de.fh_kiel.iue.mob;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        super(R.layout.fragment_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, final Bundle savedInstanceState) {
        ArrayList<String> testData = requireArguments().getStringArrayList("test_data");

        ImageView tvBild = view.findViewById(R.id.bild);
        TextView tvViews = view.findViewById(R.id.views);
        TextView tvLikes = view.findViewById(R.id.likes);

        if (testData != null) {
            Glide.with(view.getContext()).load(testData.get(0)).into(tvBild);
            tvViews.setText(testData.get(1));
            tvLikes.setText(testData.get(2));
        }
    }
}