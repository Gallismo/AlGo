package com.example.algo.models;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.algo.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderViewModel extends AndroidViewModel {
    private final DbRepo dbRepo;
    private LiveData<Map<Order, Status>> ordersLiveData;
    private LiveData<Stats> statsLiveData;

    public OrderViewModel(Application context) {
        super(context);
        dbRepo = DbRepo.getInstance(context);
    }

    public long insertOneOrder(Order order) {
        return dbRepo.insertOneOrder(order);
    }

    public int switchStatus(long status_id, long order_id) {
        return dbRepo.switchStatusOrder(status_id, order_id);
    }

    public int updatePaid(int paid, long order_id) {
        return dbRepo.updatePaid(paid, order_id);
    }

    public int deleteOrder(long order_id) {
        return dbRepo.deleteOrder(order_id);
    }


    public void setOrders(String SQL) {
        ordersLiveData = dbRepo.getAllOrders(SQL);
    }

    public LiveData<Map<Order, Status>> getLiveDataOrders() {
        return ordersLiveData;
    }

//    public LiveData<Map<Order, Status>> getLiveDataOrders(String SQL) {
//        ordersLiveData = dbRepo.getAllOrders(SQL);
//        return ordersLiveData;
//    }

    public ArrayList<OrderStatus> getOrders() {
        ArrayList<OrderStatus> result = new ArrayList<>();

        if (ordersLiveData.getValue() == null) {
            return result;
        }

        for (Map.Entry<Order, Status> item: ordersLiveData.getValue().entrySet()) {
            result.add(new OrderStatus(item.getKey(), item.getValue()));
        }

        return result;
    }

//    public ArrayList<OrderStatus> getOrders(String SQL) {
//        ordersLiveData = dbRepo.getAllOrders(SQL);
//        ArrayList<OrderStatus> result = new ArrayList<>();
//
//        if (ordersLiveData.getValue() == null) {
//            return result;
//        }
//
//        for (Map.Entry<Order, Status> item: ordersLiveData.getValue().entrySet()) {
//            result.add(new OrderStatus(item.getKey(), item.getValue()));
//        }
//
//        return result;
//    }

    public void setStats(long dateStart, long dateEnd) {
        statsLiveData = dbRepo.getStats(dateStart, dateEnd);
    }

    public LiveData<Stats> getStatsLiveDate() {
        return statsLiveData;
    }

    public Stats getStats() {
        return statsLiveData.getValue();
    }



}
