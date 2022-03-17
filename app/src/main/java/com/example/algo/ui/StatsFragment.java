package com.example.algo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.fragment.app.Fragment;
import com.example.algo.R;

public class StatsFragment extends Fragment {
    private final Activity activity;
    public StatsFragment(Activity mActivity){
        activity = mActivity;
    }

    public static StatsFragment newInstance(Activity activity) {
        return new StatsFragment(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView root = new ScrollView(inflater.getContext());
        return inflater.inflate(R.layout.fragment_stats, root, true);
    }
}
