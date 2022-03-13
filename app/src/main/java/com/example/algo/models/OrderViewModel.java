package com.example.algo.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderViewModel extends AndroidViewModel {
    private DbRepo dbRepo;
    private LiveData<Map<Order, Status>> ordersLiveData;

    public OrderViewModel(Application context) {
        super(context);
        dbRepo = new DbRepo(context);
        ordersLiveData = dbRepo.getAllOrders();
    }

    public long insertOneOrder(Order order) {
        return dbRepo.insertOneOrder(order);
    }

    public LiveData<Map<Order, Status>> getLiveDataOrders() {
        return ordersLiveData;
    }

    public ArrayList<OrderStatus> getOrders() {
        ArrayList<OrderStatus> result = new ArrayList<OrderStatus>();

        if (ordersLiveData.getValue() == null) {
            return result;
        }

        for(Map.Entry<Order, Status> entry: ordersLiveData.getValue().entrySet()) {
            Order order = entry.getKey();
            Status status = entry.getValue();
            OrderStatus orderStatus = new OrderStatus(order, status);
            result.add(orderStatus);
        }

        return result;
    }

}
