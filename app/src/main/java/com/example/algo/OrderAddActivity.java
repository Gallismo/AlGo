package com.example.algo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import com.example.algo.custom.CustomActivity;
import com.example.algo.custom.FillingTextWatcher;
import com.example.algo.custom.MoneyTextWatcher;
import com.example.algo.custom.TextInput;
import com.example.algo.models.Order;
import com.example.algo.models.OrderDao;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class OrderAddActivity extends CustomActivity {

    Calendar date = Calendar.getInstance();
    TextInput dateInput;
    ActionBar actionBar;
    public static final int MIN_TEXT_LENGTH = 1;
    public static final String EMPTY_STRING = "";

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
        actionBar.setTitle("Добавить");

        TextInput paid = (TextInput) findViewById(R.id.paid_input);
        TextInput sum = (TextInput) findViewById(R.id.sum_input);
        TextInput clientName = (TextInput) findViewById(R.id.client_name_input);
        TextInput city = (TextInput) findViewById(R.id.city_input);
        TextInput count = (TextInput) findViewById(R.id.count_input);

        TextInputLayout paidLayout = (TextInputLayout) findViewById(R.id.paid_layout);
        TextInputLayout sumLayout = (TextInputLayout) findViewById(R.id.sum_layout);
        TextInputLayout clientNameLayout = (TextInputLayout) findViewById(R.id.client_name_layout);
        TextInputLayout cityLayout = (TextInputLayout) findViewById(R.id.city_layout);
        TextInputLayout countLayout = (TextInputLayout) findViewById(R.id.count_layout);
        TextInputLayout dateLayout = (TextInputLayout) findViewById(R.id.date_layout);

        paid.addTextChangedListener(new MoneyTextWatcher(paid));
        sum.addTextChangedListener(new MoneyTextWatcher(sum));
        paid.addTextChangedListener(new FillingTextWatcher(paid, paidLayout, getApplication()));
        sum.addTextChangedListener(new FillingTextWatcher(sum, sumLayout, getApplication()));
        clientName.addTextChangedListener(new FillingTextWatcher(clientName, clientNameLayout, getApplication()));
        city.addTextChangedListener(new FillingTextWatcher(city, cityLayout, getApplication()));
        count.addTextChangedListener(new FillingTextWatcher(count, countLayout, getApplication()));
        dateInput.addTextChangedListener(new FillingTextWatcher(dateInput, dateLayout, getApplication()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_add_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save_button:
                try {
                    if (orderAdd() > 0) {
                        finish();
                    }
                } catch (ParseException e) {
                    Log.e(TAG, e.toString());
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean is_error() {
        int errors = 0;

        TextInput client_name = (TextInput) findViewById(R.id.client_name_input);
        TextInputLayout client_name_layout = (TextInputLayout) findViewById(R.id.client_name_layout);
        if (shouldShowError(client_name)) {
            errors++;
            showError(client_name_layout);
        } else {
            hideError(client_name_layout);
        }

        TextInput city = (TextInput) findViewById(R.id.city_input);
        TextInputLayout city_layout = (TextInputLayout) findViewById(R.id.city_layout);
        if (shouldShowError(city)) {
            showError(city_layout);
            errors++;
        } else {
            hideError(city_layout);
        }

        TextInput count = (TextInput) findViewById(R.id.count_input);
        TextInputLayout count_layout = (TextInputLayout) findViewById(R.id.count_layout);
        if (shouldShowError(count)) {
            showError(count_layout);
            errors++;
        } else {
            hideError(count_layout);
        }

        TextInput sum = (TextInput) findViewById(R.id.sum_input);
        TextInputLayout sum_layout = (TextInputLayout) findViewById(R.id.sum_layout);
        if (shouldShowError(sum)) {
            showError(sum_layout);
            errors++;
        } else {
            hideError(sum_layout);
        }

        TextInput paid = (TextInput) findViewById(R.id.paid_input);
        TextInputLayout paid_layout = (TextInputLayout) findViewById(R.id.paid_layout);
        if (shouldShowError(paid)) {
            showError(paid_layout);
            errors++;
        } else {
            hideError(paid_layout);
        }

        TextInput date = (TextInput) findViewById(R.id.date_input);
        TextInputLayout date_layout = (TextInputLayout) findViewById(R.id.date_layout);
        if (shouldShowError(date)) {
            showError(date_layout);
            errors++;
        } else {
            hideError(date_layout);
        }

        return errors > 0;
    }

    private long orderAdd() throws ParseException {
        if (is_error()) {
            return -1;
        }

        Order order = new Order();

        order.client_name = ( (TextInput) findViewById(R.id.client_name_input) ).getText().toString();
        order.city = ( (TextInput) findViewById(R.id.city_input) ).getText().toString();
        order.products_count = Integer.parseInt( ( (TextInput) findViewById(R.id.count_input) ).getText().toString() );
        order.sum = Double.parseDouble( ( (TextInput) findViewById(R.id.sum_input) ).getText().toString().replaceAll(" ", "") );
        order.paid = Double.parseDouble( ( (TextInput) findViewById(R.id.paid_input) ).getText().toString().replaceAll(" ", "") );

        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
        order.date = ( parser.parse( ( (TextInput) findViewById(R.id.date_input) ).getText().toString() ) ).getTime();

        order.notes = ( (TextInput) findViewById(R.id.notes_input) ).getText().toString();

        return orderViewModel.insertOneOrder(order);

    }

    private boolean shouldShowError(TextInput textInput) {
        int length = textInput.getText().length();
        return length < MIN_TEXT_LENGTH;
    }

    private void showError(TextInputLayout layout) {
        layout.setError(getString(R.string.error));
    }

    private void hideError(TextInputLayout layout) {
        layout.setError(EMPTY_STRING);
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