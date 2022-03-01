package com.example.algo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Status {
    @PrimaryKey
    public long id;

    public String name;

    public String color;
}
