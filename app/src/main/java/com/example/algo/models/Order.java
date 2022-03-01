package com.example.algo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @PrimaryKey
    public long id;

    public String client_name;

    public String city;

    public int products_count;

    public double sum;

    public double paid;

    public long date;

    public long status_id;

    public String notes;
}
