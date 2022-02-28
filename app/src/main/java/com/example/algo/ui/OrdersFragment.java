package com.example.algo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.algo.R;

public class OrdersFragment extends Fragment {
    public OrdersFragment(){}

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }
}
