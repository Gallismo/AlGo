package com.example.algo.models;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbRepo {
    private AppDatabase db;
    private OrderDao orderDao;
    private LiveData<ArrayList<Order>> orders;

    public DbRepo(Application context) {
        db = AppDatabase.getDatabase(context);
        orderDao = db.orderDao();
        orders = orderDao.getAllOrders();
    }

    public LiveData<ArrayList<Order>> getAllOrders() {
        return orders;
    }

    public long insertOneOrder(Order order) {
        return orderDao.insertOrder(order);
    }

}
