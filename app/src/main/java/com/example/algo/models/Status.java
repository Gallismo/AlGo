package com.example.algo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "status")
public class Status {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String color;

    @Override
    public String toString() {
        return name;
    }
}
