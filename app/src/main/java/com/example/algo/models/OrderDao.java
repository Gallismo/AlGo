package com.example.algo.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OrderDao {

    public static String getAll = "SELECT `order`.*, status.name, status.color FROM `order` JOIN status ON `order`.status_id = status.id ORDER BY `order`.date DESC";

    @Query(getAll)
    LiveData<Map<Order, Status>> getAllOrders();

//    @Query("SELECT * FROM `order` WHERE date > :date_after")
//    List<Order> getOrdersAfterDate(Date date_after);

    @Insert(onConflict = REPLACE)
    long insertOrder(Order order);

    @Query("UPDATE `order` SET status_id = :status_id WHERE id = :order_id")
    int switchStatus(long status_id, long order_id);

    @Query("UPDATE `order` SET paid = :paid WHERE id = :order_id")
    int updatePaid(double paid, long order_id);

    @Query("DELETE from `order` WHERE id = :order_id")
    int deleteOrder(long order_id);
}
