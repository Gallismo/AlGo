package com.example.algo.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.algo.FilterOrdersActivity;
import com.example.algo.R;
import com.example.algo.custom.CustomActivity;
import com.example.algo.custom.TextInput;
import com.example.algo.models.OrderViewModel;
import com.example.algo.models.Stats;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatsFragment extends Fragment {
    private final Activity activity;
    Calendar date = Calendar.getInstance();
    Calendar dateStart = Calendar.getInstance();
    Calendar dateEnd = Calendar.getInstance();
    TextView monthOutput, moneySumOutput, productsSumOutput, orderCountOutput;
    TextInput statsStart, statsEnd;
    TextInputLayout statsStartLay, statsEndLay;
    Button applyButton;
    FloatingActionButton increase, decrease;
    private OrderViewModel orderViewModel;

    public StatsFragment(Activity mActivity){
        activity = mActivity;
    }

    public static StatsFragment newInstance(Activity activity) {
        return new StatsFragment(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        monthOutput = view.findViewById(R.id.month_output);
        moneySumOutput = view.findViewById(R.id.money_sum_output);
        productsSumOutput = view.findViewById(R.id.products_sum_output);
        orderCountOutput = view.findViewById(R.id.orders_count_output);
        increase = view.findViewById(R.id.month_increase);
        decrease = view.findViewById(R.id.month_decrease);
        applyButton = view.findViewById(R.id.stats_apply);
        statsStart = view.findViewById(R.id.stats_date_start);
        statsEnd = view.findViewById(R.id.stats_date_end);
        statsStartLay = view.findViewById(R.id.stats_start_layout);
        statsEndLay = view.findViewById(R.id.stats_end_layout);

        increase.setOnClickListener(increaseListener);
        decrease.setOnClickListener(decreaseListener);
        applyButton.setOnClickListener(applyListener);

        statsStart.setOnClickListener(dateOpen);
        statsStartLay.setOnClickListener(dateOpen);
        statsEnd.setOnClickListener(dateOpen);
        statsEndLay.setOnClickListener(dateOpen);

        orderViewModel = CustomActivity.orderViewModel;

        dateSet();
        setInitialDateTime();

        orderViewModel.setStats(dateStart.getTimeInMillis(), dateEnd.getTimeInMillis());

        orderViewModel.getStatsLiveDate().observe(this, new Observer<Stats>() {
            @Override
            public void onChanged(Stats stats) {
                updateText(stats);
            }
        });

        loadStats();
        return view;
    }

    private View.OnClickListener applyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                manualDateSet();
                loadStats();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public void loadStats() {
        orderViewModel.setStats(dateStart.getTimeInMillis(), dateEnd.getTimeInMillis());

        orderViewModel.getStatsLiveDate().observe(this, new Observer<Stats>() {
            @Override
            public void onChanged(Stats stats) {
                updateText(stats);
            }
        });
    }

    private void updateText(Stats stats) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        moneySumOutput.setText(formatter.format(stats.moneySum).replaceAll(",", " "));
        productsSumOutput.setText(formatter.format(stats.productsSum).replaceAll(",", " "));
        orderCountOutput.setText(formatter.format(stats.ordersCount).replaceAll(",", " "));
    }

    private void manualDateSet() throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
        Editable textStart = statsStart.getText();
        Editable textEnd = statsEnd.getText();
        Date start, end;
        if (textStart != null && textEnd != null) {
            start = parser.parse(textStart.toString());
            end = parser.parse(textEnd.toString());
        } else {
            return;
        }

        dateStart.setTime(start);
        dateStart.set(Calendar.HOUR_OF_DAY, dateStart.getActualMinimum(Calendar.HOUR_OF_DAY));
        dateStart.set(Calendar.MINUTE, dateStart.getActualMinimum(Calendar.MINUTE));
        dateStart.set(Calendar.SECOND, dateStart.getActualMinimum(Calendar.SECOND));
        dateStart.set(Calendar.MILLISECOND, dateStart.getActualMinimum(Calendar.MILLISECOND));

        dateEnd.setTime(end);
        dateEnd.set(Calendar.HOUR_OF_DAY, dateEnd.getActualMaximum(Calendar.HOUR_OF_DAY));
        dateEnd.set(Calendar.MINUTE, dateEnd.getActualMaximum(Calendar.MINUTE));
        dateEnd.set(Calendar.SECOND, dateEnd.getActualMaximum(Calendar.SECOND));
        dateEnd.set(Calendar.MILLISECOND, dateEnd.getActualMaximum(Calendar.MILLISECOND));
    }

    private void dateSet() {
        date.set(Calendar.DATE, date.getActualMinimum(Calendar.DATE));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));

        dateStart = (Calendar) date.clone();
        dateStart.set(Calendar.DATE, dateStart.getActualMinimum(Calendar.DATE));
        dateStart.set(Calendar.HOUR_OF_DAY, dateStart.getActualMinimum(Calendar.HOUR_OF_DAY));
        dateStart.set(Calendar.MINUTE, dateStart.getActualMinimum(Calendar.MINUTE));
        dateStart.set(Calendar.SECOND, dateStart.getActualMinimum(Calendar.SECOND));
        dateStart.set(Calendar.MILLISECOND, dateStart.getActualMinimum(Calendar.MILLISECOND));

        dateEnd = (Calendar) date.clone();
        dateEnd.set(Calendar.DATE, dateEnd.getActualMaximum(Calendar.DATE));
        dateEnd.set(Calendar.HOUR_OF_DAY, dateEnd.getActualMaximum(Calendar.HOUR_OF_DAY));
        dateEnd.set(Calendar.MINUTE, dateEnd.getActualMaximum(Calendar.MINUTE));
        dateEnd.set(Calendar.SECOND, dateEnd.getActualMaximum(Calendar.SECOND));
        dateEnd.set(Calendar.MILLISECOND, dateEnd.getActualMaximum(Calendar.MILLISECOND));
    }

    private View.OnClickListener increaseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
            dateSet();
            setInitialDateTime();
            loadStats();
        }
    };

    private View.OnClickListener decreaseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
            dateSet();
            setInitialDateTime();
            loadStats();
        }
    };

    private void setInitialDateTime() {
        DateFormatSymbols months = new DateFormatSymbols() {
            @Override
            public String[] getMonths() {
                return new String[] {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                        "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
            }
        };
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM yyyy", months);
        monthOutput.setText(dateFormatter.format(date.getTime()));
        dateFormatter.applyPattern("dd.MM.yyyy");
        statsStart.setText(dateFormatter.format(dateStart.getTime()));
        statsEnd.setText(dateFormatter.format(dateEnd.getTime()));
    }

    private void dateStartOpen() {
        new DatePickerDialog(getContext(), dateSetListener(dateStart),
                dateStart.get(Calendar.YEAR),
                dateStart.get(Calendar.MONTH),
                dateStart.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    private void dateEndOpen() {
        new DatePickerDialog(getContext(), dateSetListener(dateEnd),
                dateEnd.get(Calendar.YEAR),
                dateEnd.get(Calendar.MONTH),
                dateEnd.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void openDateDialog(View view) {

        switch (view.getId()) {
            case R.id.stats_date_start:
            case R.id.stats_start_layout:
                dateStartOpen();
                break;
            case R.id.stats_date_end:
            case R.id.stats_end_layout:
                dateEndOpen();
                break;
        }
    }

    private View.OnClickListener dateOpen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openDateDialog(view);
        }
    };


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
}
