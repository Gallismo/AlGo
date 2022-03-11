package com.example.algo.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.example.algo.models.Order;

import java.util.ArrayList;

public class OrdersListAdapter extends BaseExpandableListAdapter {
    private ArrayList<Order> orders;



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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
