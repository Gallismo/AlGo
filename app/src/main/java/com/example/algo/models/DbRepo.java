package com.example.algo.models;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.Iterator;
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

    public int updatePaid(double paid, long order_id) {
        return orderDao.updatePaid(paid, order_id);
    }

    public int switchStatusOrder(long status_id, long order_id) {
        return orderDao.switchStatus(status_id, order_id);
    }

}
