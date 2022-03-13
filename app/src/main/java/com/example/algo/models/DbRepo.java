package com.example.algo.models;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbRepo {
    private AppDatabase db;
    private OrderDao orderDao;
    private LiveData<Map<Order, Status>> orders;

    public DbRepo(Application context) {
        db = AppDatabase.getDatabase(context);
        orderDao = db.orderDao();
        orders = orderDao.getAllOrders();
    }

    public LiveData<Map<Order, Status>> getAllOrders() {
        return orders;
    }

    public long insertOneOrder(Order order) {
        return orderDao.insertOrder(order);
    }

}
