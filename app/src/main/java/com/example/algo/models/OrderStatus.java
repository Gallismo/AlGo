package com.example.algo.models;


public class OrderStatus {
    public OrderStatus(Order order, Status status) {
        id = order.id;
        client_name = order.client_name;
        city = order.city;
        products_count = order.products_count;
        sum = order.sum;
        paid = order.paid;
        date = order.date;
        status_id = order.status_id;
        notes = order.notes;
        status_name = status.name;
        status_color = status.color;
    }

    public long id;

    public String client_name;

    public String city;

    public int products_count;

    public int sum;

    public int paid;

    public long date;

    public long status_id;

    public String status_name;

    public String status_color;

    public String notes;
}
