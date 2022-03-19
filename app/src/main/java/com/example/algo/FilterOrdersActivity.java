package com.example.algo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.example.algo.custom.CustomActivity;
import com.example.algo.custom.TextInput;
import com.example.algo.models.Order;
import com.example.algo.models.OrderStatus;
import com.example.algo.models.OrderViewModel;
import com.example.algo.models.Status;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class FilterOrdersActivity extends CustomActivity {
    Calendar dateStart = Calendar.getInstance();
    Calendar dateEnd = Calendar.getInstance();
    TextInput dateStartInput;
    TextInput dateEndInput;

    ActionBar actionBar;
    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_filter);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Фильтры");


        spinner = findViewById(R.id.status_filter);
        dateStartInput = findViewById(R.id.date_start_input);
        dateEndInput = findViewById(R.id.date_end_input);

        ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.status_spinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);


        spinner.setAdapter(spinnerAdapter);

        dateStart.set(Calendar.DATE, dateStart.getActualMinimum(Calendar.DATE));
        dateEnd.set(Calendar.DATE, dateEnd.getActualMaximum(Calendar.DATE));
        dateSet();
        setInitialDateTime();
    }

    private void dateSet() {
        dateStart.set(Calendar.HOUR_OF_DAY, dateStart.getActualMinimum(Calendar.HOUR_OF_DAY));
        dateStart.set(Calendar.MINUTE, dateStart.getActualMinimum(Calendar.MINUTE));
        dateStart.set(Calendar.SECOND, dateStart.getActualMinimum(Calendar.SECOND));

        dateEnd.set(Calendar.HOUR_OF_DAY, dateEnd.getActualMaximum(Calendar.HOUR_OF_DAY));
        dateEnd.set(Calendar.MINUTE, dateEnd.getActualMaximum(Calendar.MINUTE));
        dateEnd.set(Calendar.SECOND, dateEnd.getActualMaximum(Calendar.SECOND));
    }

    private void dateStartOpen() {
        new DatePickerDialog(FilterOrdersActivity.this, dateSetListener(dateStart),
                dateStart.get(Calendar.YEAR),
                dateStart.get(Calendar.MONTH),
                dateStart.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    private void dateEndOpen() {
        new DatePickerDialog(FilterOrdersActivity.this, dateSetListener(dateEnd),
                dateEnd.get(Calendar.YEAR),
                dateEnd.get(Calendar.MONTH),
                dateEnd.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void openDateDialog(View view) {

        switch (view.getId()) {
            case R.id.date_start_input:
            case R.id.date_start_layout:
                dateStartOpen();
                break;
            case R.id.date_end_input:
            case R.id.date_end_layout:
                dateEndOpen();
                break;
        }
    }


    public DatePickerDialog.OnDateSetListener dateSetListener(Calendar date) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, day);
                setInitialDateTime();
            }
        };
    }


    private void setInitialDateTime() {
        dateSet();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        dateStartInput.setText(dateFormatter.format(dateStart.getTime()));
        dateEndInput.setText(dateFormatter.format(dateEnd.getTime()));
    }

    public void filter() throws ParseException {
        String queryString = "";
        List<Object> args = new ArrayList<Object>();

        boolean containsCondition = false;

        queryString += "SELECT `order`.*, `status`.name, `status`.color FROM `order` JOIN `status` ON `order`.status_id = `status`.id";

        switch (spinner.getSelectedItem().toString()) {
            case "Принято":
                queryString += " WHERE `order`.status_id = 1";
                containsCondition = true;
                break;
            case "Отправлено":
                queryString += " WHERE `order`.status_id = 2";
                containsCondition = true;
                break;
            case "Доставлено":
                queryString += " WHERE `order`.status_id = 3";
                containsCondition = true;
                break;
        }

        CheckBox is_filter_date = findViewById(R.id.is_filter_date);
        if (is_filter_date.isChecked()) {
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            TextInput date_start = findViewById(R.id.date_start_input);
            queryString += " `order`.date >= " + parser.parse(date_start.getText().toString()).getTime();

            TextInput date_end = findViewById(R.id.date_end_input);
            queryString += " AND `order`.date <= " + parser.parse(date_end.getText().toString()).getTime();
        }

        TextInput client_filter = findViewById(R.id.client_filter_input);
        TextInput city_filter = findViewById(R.id.city_filter_input);
        if (client_filter.getText() != null && !client_filter.getText().toString().equals("")) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " `order`.client_name LIKE '%"+client_filter.getText().toString()+"%'";
        }

        if (city_filter.getText() != null && !city_filter.getText().toString().equals("")) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " `order`.city LIKE '%"+city_filter.getText().toString()+"%'";
        }

        queryString += " ORDER BY `order`.date DESC;";

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ordersFilterSQL", queryString);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = getIntent();
                if (intent.getStringExtra("ordersFilterSQL") != null && !(intent.getStringExtra("ordersFilterSQL")).equals("")) {
                    Intent closeIntent = new Intent(this, MainActivity.class);
                    closeIntent.putExtra("ordersFilterSQL", intent.getStringExtra("ordersFilterSQL"));
                    startActivity(closeIntent);
                } else {
                    finish();
                }
                break;
            case R.id.apply_button:
                try {
                    filter();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
