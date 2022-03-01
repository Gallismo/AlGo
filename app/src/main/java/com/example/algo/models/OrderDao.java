package com.example.algo.models;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM `order`")
    List<Order> getAllOrders();

    @Query("SELECT * FROM `order` WHERE date > :date_after")
    List<Order> getOrdersAfterDate(Date date_after);
}
