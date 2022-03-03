package com.example.algo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import com.example.algo.custom.CustomActivity;
import com.example.algo.custom.TextInput;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderAddActivity extends CustomActivity {

    Calendar date = Calendar.getInstance();
    TextInput dateInput;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_add);

        dateInput = findViewById(R.id.date_input);
        setInitialDateTime();

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDateDialog(View view) {
        new DatePickerDialog(OrderAddActivity.this, dateSetListener,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, day);
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        dateInput.setText(dateFormatter.format(date.getTime()));
    }

}