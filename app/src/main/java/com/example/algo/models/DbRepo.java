package com.example.algo.models;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.algo.AppDatabase;

import java.util.List;

public class DbRepo {
    private AppDatabase db;
    private OrderDao orderDao;
    private LiveData<List<Order>> orders;

    public DbRepo(Application context) {
        db = AppDatabase.getDatabase(context);
        orderDao = db.orderDao();
        orders = orderDao.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return orders;
    }

    public long insertOneOrder(Order order) {
        return orderDao.insertOrder(order);
    }

}
