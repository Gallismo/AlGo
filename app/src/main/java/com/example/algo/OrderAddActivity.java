package com.example.algo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class OrderAddActivity extends CustomActivity {

    Calendar date = Calendar.getInstance();
    TextInput dateInput;
    ActionBar actionBar;
    public static final int MIN_TEXT_LENGTH = 1;
    public static final String EMPTY_STRING = "";
    TextView id_holder, status_id_holder;
    TextInput paid, sum, clientName, city, count, notes;

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

        paid = (TextInput) findViewById(R.id.paid_input);
        sum = (TextInput) findViewById(R.id.sum_input);
        clientName = (TextInput) findViewById(R.id.client_name_input);
        city = (TextInput) findViewById(R.id.city_input);
        count = (TextInput) findViewById(R.id.count_input);
        notes = (TextInput) findViewById(R.id.notes_input);
        id_holder = (TextView) findViewById(R.id.id_holder_input);
        status_id_holder = (TextView) findViewById(R.id.status_id_holder);

        addListenersAndFillInputs();

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

        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
        Order order = new Order();

        order.client_name = clientName.getText().toString();
        order.city = city.getText().toString();
        order.products_count = Integer.parseInt(count.getText().toString());
        order.sum = Double.parseDouble(sum.getText().toString().replaceAll(" ", ""));
        order.paid = Double.parseDouble(paid.getText().toString().replaceAll(" ", ""));
        order.notes = notes.getText().toString();
        order.date = parser.parse(dateInput.getText().toString()).getTime();

        if (id_holder.getText() != null && id_holder.getText() != "") {
            order.id = Long.parseLong(id_holder.getText().toString());
        }

        if (status_id_holder.getText() == null) {
            order.status_id = 1;
        } else {
            order.status_id = Long.parseLong(status_id_holder.getText().toString());
        }

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

    public void addListenersAndFillInputs() {
        TextInputLayout paidLayout = (TextInputLayout) findViewById(R.id.paid_layout);
        TextInputLayout sumLayout = (TextInputLayout) findViewById(R.id.sum_layout);
        TextInputLayout clientNameLayout = (TextInputLayout) findViewById(R.id.client_name_layout);
        TextInputLayout cityLayout = (TextInputLayout) findViewById(R.id.city_layout);
        TextInputLayout countLayout = (TextInputLayout) findViewById(R.id.count_layout);
        TextInputLayout dateLayout = (TextInputLayout) findViewById(R.id.date_layout);

        Intent intent = getIntent();
        clientName.setText(intent.getStringExtra("client_name"));
        city.setText(intent.getStringExtra("city"));
        if (intent.getIntExtra("count", 0) != 0) {
            count.setText(Integer.toString(intent.getIntExtra("count", 0)));
        }
        if (intent.getDoubleExtra("paid", 0) != Double.parseDouble(Integer.toString(0))) {
            paid.setText(Double.toString(intent.getDoubleExtra("paid", 0)));
        }
        if (intent.getDoubleExtra("sum", 0) != Double.parseDouble(Integer.toString(0))) {
            sum.setText(Double.toString(intent.getDoubleExtra("sum", 0)));
        }
        if (intent.getLongExtra("date", 0) != Long.parseLong(Integer.toString(0))) {
            dateInput.setText(new SimpleDateFormat("dd.MM.yyyy")
                    .format(new Date(intent.getLongExtra("date", 0))));
        } else {
            dateInput.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        }
        notes.setText(intent.getStringExtra("notes"));
        if (intent.getLongExtra("id", 0) != Long.parseLong(Integer.toString(0))) {
            id_holder.setText(Long.toString(intent.getLongExtra("id", 0)));
        }
        if (intent.getLongExtra("status_id", 0) != Long.parseLong(Integer.toString(0))) {
            status_id_holder.setText(Long.toString(intent.getLongExtra("status_id", 0)));
        } else {
            status_id_holder.setText("1");
        }

        paid.addTextChangedListener(new MoneyTextWatcher(paid));
        sum.addTextChangedListener(new MoneyTextWatcher(sum));
        paid.addTextChangedListener(new FillingTextWatcher(paid, paidLayout, getApplication()));
        sum.addTextChangedListener(new FillingTextWatcher(sum, sumLayout, getApplication()));
        clientName.addTextChangedListener(new FillingTextWatcher(clientName, clientNameLayout, getApplication()));
        city.addTextChangedListener(new FillingTextWatcher(city, cityLayout, getApplication()));
        count.addTextChangedListener(new FillingTextWatcher(count, countLayout, getApplication()));
        dateInput.addTextChangedListener(new FillingTextWatcher(dateInput, dateLayout, getApplication()));
    }

}