package com.example.algo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.algo.models.Order;
import com.example.algo.models.OrderDao;
import com.example.algo.models.Status;

@Database(entities = {Order.class, Status.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();
}
