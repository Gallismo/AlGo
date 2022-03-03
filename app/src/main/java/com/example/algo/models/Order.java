package com.example.algo.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = @ForeignKey(entity = Status.class, parentColumns = "id", childColumns = "status_id",
        onDelete = SET_NULL, onUpdate = CASCADE))
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String client_name;

    public String city;

    public int products_count;

    public double sum;

    public double paid;

    public long date;

    public long status_id = 1;

    public String notes;
}
