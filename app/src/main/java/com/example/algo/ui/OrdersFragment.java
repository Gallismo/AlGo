package com.example.algo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.algo.MainActivity;
import com.example.algo.OrderAddActivity;
import com.example.algo.R;
import com.example.algo.models.Order;
import com.example.algo.models.OrderStatus;
import com.example.algo.models.OrderViewModel;
import com.example.algo.models.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class OrdersFragment extends Fragment {
    public OrdersFragment(){}

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.orders_list);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        OrderViewModel orderViewModel = viewModelProvider.get(OrderViewModel.class);

        ArrayList<OrderStatus> orders = orderViewModel.getOrders();

        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(getContext(), orders, orderViewModel,
                getActivity(), new EditListener(), listView);

        orderViewModel.getLiveDataOrders().observe(this, new Observer<Map<Order, Status>>() {
            @Override
            public void onChanged(Map<Order, Status> orderStatusMap) {
                ordersListAdapter.setOrders(orderViewModel.getOrders());
            }
        });

        listView.setAdapter(ordersListAdapter);

        FloatingActionButton orderAddButton = (FloatingActionButton) view.findViewById(R.id.add_button);
        orderAddButton.setOnClickListener(orderAddClickListener);

        return view;
    }

    public class EditListener {

        public OnClickListener editClickListener(OrderStatus order) {
            return new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrdersFragment.this.getActivity(), OrderAddActivity.class);
                    intent.putExtra("id", order.id);
                    intent.putExtra("client_name", order.client_name);
                    intent.putExtra("city", order.city);
                    intent.putExtra("count", order.products_count);
                    intent.putExtra("sum", order.sum);
                    intent.putExtra("paid", order.paid);
                    intent.putExtra("date", order.date);
                    intent.putExtra("notes", order.notes);
                    intent.putExtra("status_id", order.status_id);
                    startActivity(intent);
                }
            };
        }
    }

    private View.OnClickListener orderAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OrdersFragment.this.getActivity(), OrderAddActivity.class);
            Order order = new Order();
            intent.putExtra("status_id", order.status_id);
            startActivity(intent);
        }
    };
}
