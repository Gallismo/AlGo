package com.example.algo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.algo.R;
import com.example.algo.models.Order;
import com.example.algo.models.OrderStatus;

import java.util.ArrayList;

public class OrdersListAdapter extends BaseExpandableListAdapter {
    private ArrayList<OrderStatus> orders;
    private Context context;

    public OrdersListAdapter(Context mContext, ArrayList<OrderStatus> mOrders) {
        context = mContext;
        orders = mOrders;
    }

    public void setOrders(ArrayList<OrderStatus> mOrders) {
        orders = mOrders;
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return orders.size();
    }

    @Override
    public int getChildrenCount(int orderPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int orderPosition) {
        return orders.get(orderPosition);
    }

    @Override
    public Object getChild(int orderPosition, int childPosition) {
        return orders.get(orderPosition).id;
    }

    @Override
    public long getGroupId(int orderPosition) {
        return orderPosition;
    }

    @Override
    public long getChildId(int orderPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int orderPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_item, null);
        }

        if (orders.isEmpty()) {
            return convertView;
        }

        TextView client = (TextView) convertView.findViewById(R.id.client_output);
        TextView city = (TextView) convertView.findViewById(R.id.city_output);
        TextView status = (TextView) convertView.findViewById(R.id.status_output);
        TextView count = (TextView) convertView.findViewById(R.id.count_output);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_output);
        TextView paid = (TextView) convertView.findViewById(R.id.paid_output);

        client.setText(orders.get(orderPosition).client_name);
        city.setText(orders.get(orderPosition).city);
        status.setText(orders.get(orderPosition).status_name);
        count.setText(Integer.toString(orders.get(orderPosition).products_count));
        sum.setText(Double.toString(orders.get(orderPosition).sum));
        paid.setText(Double.toString(orders.get(orderPosition).paid));

        return convertView;
    }

    @Override
    public View getChildView(int orderPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
