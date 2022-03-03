package com.example.algo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.fragment.app.Fragment;
import com.example.algo.MainActivity;
import com.example.algo.OrderAddActivity;
import com.example.algo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OrdersFragment extends Fragment {
    public OrdersFragment(){}

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ListView listView = (ListView) view.findViewById(R.id.orders_list);
        String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
                "Костя", "Игорь", "Анна", "Денис", "Андрей", "Марья", "Петр", "Антон", "Даша", "Борис",
                "Костя", "Игорь", "Анна", "Денис", };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

        FloatingActionButton orderAddButton = (FloatingActionButton) view.findViewById(R.id.add_button);
        orderAddButton.setOnClickListener(orderAddClickListener);

        return view;
    }

    private View.OnClickListener orderAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OrdersFragment.this.getActivity(), OrderAddActivity.class);
            startActivity(intent);
        }
    };
}
