package com.example.algo.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.*;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OrderDao {


    @RawQuery
    Map<Order, Status> getAllOrders(SupportSQLiteQuery query);

    @Insert(onConflict = REPLACE)
    long insertOrder(Order order);

    @Query("UPDATE `order` SET status_id = :status_id WHERE id = :order_id")
    int switchStatus(long status_id, long order_id);

    @Query("UPDATE `order` SET paid = :paid WHERE id = :order_id")
    int updatePaid(int paid, long order_id);

    @Query("DELETE from `order` WHERE id = :order_id")
    int deleteOrder(long order_id);

//    @Query("SELECT SUM(sum) from `order` WHERE date >= :dateStart AND date <= :dateEnd")
//    float moneySum(long dateStart, long dateEnd);
//
//    @Query("SELECT SUM(products_count) from `order` WHERE date >= :dateStart AND date <= :dateEnd")
//    int productsSum(long dateStart, long dateEnd);
//
//    @Query("SELECT COUNT(*) from `order` WHERE date >= :dateStart AND date <= :dateEnd")
//    int orderCount(long dateStart, long dateEnd);
    @Query("SELECT SUM(sum) as moneySum, SUM(products_count) as productsSum," +
            " COUNT(*) as ordersCount FROM `order` WHERE date BETWEEN :dateStart AND :dateEnd")
    Stats getStats(long dateStart, long dateEnd);
}
