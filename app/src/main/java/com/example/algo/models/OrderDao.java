package com.example.algo.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM `order` JOIN status ON `order`.status_id = status.id")
    LiveData<Map<Order, Status>> getAllOrders();

//    @Query("SELECT * FROM `order` WHERE date > :date_after")
//    List<Order> getOrdersAfterDate(Date date_after);

    @Insert
    long insertOrder(Order order);
}
