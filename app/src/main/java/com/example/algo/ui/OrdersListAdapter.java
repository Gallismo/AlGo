package com.example.algo.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.algo.R;
import com.example.algo.custom.FillingTextWatcher;
import com.example.algo.custom.MoneyTextWatcher;
import com.example.algo.custom.TextInput;
import com.example.algo.models.Order;
import com.example.algo.models.OrderStatus;
import com.example.algo.models.OrderViewModel;
import com.google.android.material.textfield.TextInputLayout;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrdersListAdapter extends BaseExpandableListAdapter {
    private ArrayList<OrderStatus> orders;
    private Context context;
    private final OrderViewModel viewModel;
    private final Activity activity;

    public OrdersListAdapter(Context mContext, ArrayList<OrderStatus> mOrders, OrderViewModel mViewModel, Activity mActivity) {
        context = mContext;
        orders = mOrders;
        viewModel = mViewModel;
        activity = mActivity;
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
        return orders.get(orderPosition);
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_item_expand, null);
        }


        TextView notes = convertView.findViewById(R.id.notes_output);
        notes.setText(orders.get(orderPosition).notes);

        TextView id_holder = (TextView) convertView.findViewById(R.id.database_id_holder);
        long DB_ID = orders.get(orderPosition).id;
        id_holder.setText(Long.toString(DB_ID));

        Button received = (Button) convertView.findViewById(R.id.switch_status_received);
        Button sent = (Button) convertView.findViewById(R.id.switch_status_sent);
        Button delivered = (Button) convertView.findViewById(R.id.switch_status_delivered);

        received.setOnClickListener(statusUpdateListener(1, Integer.parseInt(id_holder.getText().toString())));
        sent.setOnClickListener(statusUpdateListener(2, Integer.parseInt(id_holder.getText().toString())));
        delivered.setOnClickListener(statusUpdateListener(3, Integer.parseInt(id_holder.getText().toString())));

        TextInput paid_input = (TextInput) convertView.findViewById(R.id.expanded_paid_input);
        paid_input.addTextChangedListener(new MoneyTextWatcher(paid_input));

        Button save_paid = (Button) convertView.findViewById(R.id.expanded_paid_button_save);
        save_paid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewModel.updatePaid(Double.parseDouble( paid_input.getText().toString().replaceAll(" ", "") ),
                        Integer.parseInt(id_holder.getText().toString()))) {
                    case 1:
                        Toast.makeText(context, "Изменение сохранено", Toast.LENGTH_SHORT).show();
                    case 0:
                        Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private OnClickListener statusUpdateListener(long status_id, long order_id) {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewModel.switchStatus(status_id, order_id)) {
                    case 1:
                        Toast.makeText(context, "Статус изменен", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
