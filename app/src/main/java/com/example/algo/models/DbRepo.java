package com.example.algo.models;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DbRepo {
    private final AppDatabase db;
    private final OrderDao orderDao;
    private LiveData<Map<Order, Status>> orders;
    private static DbRepo INSTANCE;
    private LiveData<Stats> statsLiveData;

    public DbRepo(Application context) {
        db = AppDatabase.getDatabase(context);
        orderDao = db.orderDao();
    }

    @SuppressLint("RestrictedApi")
    private void setLiveData(String stringSQL) {
        SimpleSQLiteQuery SQL = new SimpleSQLiteQuery(stringSQL);
        orders = db.getInvalidationTracker().createLiveData(new String[]{"order","status"}, false, ()->{
           return orderDao.getAllOrders(SQL);
        });
    }

    @SuppressLint("RestrictedApi")
    private void setSumAndCountLiveData(long dateStart, long dateEnd) {
        statsLiveData = db.getInvalidationTracker().createLiveData(new String[]{"order","status"}, false, ()->{
           return orderDao.getStats(dateStart, dateEnd);
        });
    }

    public static DbRepo getInstance(Application context) {
        if (INSTANCE == null) {
            synchronized (DbRepo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DbRepo(context);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<Map<Order, Status>> getAllOrders(String SQL) {
        setLiveData(SQL);
        return orders;
    }

    public LiveData<Stats> getStats(long dateStart, long dateEnd) {
        setSumAndCountLiveData(dateStart, dateEnd);
        return statsLiveData;
    }

    public long insertOneOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    public int updatePaid(int paid, long order_id) {
        return orderDao.updatePaid(paid, order_id);
    }

    public int deleteOrder(long order_id) {
        return orderDao.deleteOrder(order_id);
    }

    public int switchStatusOrder(long status_id, long order_id) {
        return orderDao.switchStatus(status_id, order_id);
    }

}
