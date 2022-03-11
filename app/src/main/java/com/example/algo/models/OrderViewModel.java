package com.example.algo.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private DbRepo dbRepo;
    private LiveData<ArrayList<Order>> ordersLiveData;

    public OrderViewModel(Application context) {
        super(context);
        dbRepo = new DbRepo(context);
        ordersLiveData = dbRepo.getAllOrders();
    }

    public long insertOneOrder(Order order) {
        return dbRepo.insertOneOrder(order);
    }

    public LiveData<ArrayList<Order>> getLiveDataOrders() {
        return ordersLiveData;
    }

    public ArrayList<Order> getOrders() {
        return ordersLiveData.getValue();
    }

}
