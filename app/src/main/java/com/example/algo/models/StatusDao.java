package com.example.algo.models;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StatusDao {
    @Query("SELECT * FROM status")
    List<Status> getAllStatuses();
}
