package com.example.algo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.algo.R;
import com.example.algo.custom.CustomActivity;
import com.example.algo.models.OrderViewModel;
import com.example.algo.models.Stats;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatsFragment extends Fragment {
    private final Activity activity;
    Calendar date = Calendar.getInstance();
    Calendar dateStart = Calendar.getInstance();
    Calendar dateEnd = Calendar.getInstance();
    TextView monthOutput, moneySumOutput, productsSumOutput, orderCountOutput;
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

        increase.setOnClickListener(increaseListener);
        decrease.setOnClickListener(decreaseListener);

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

        dateEnd = (Calendar) date.clone();
        dateEnd.set(Calendar.DATE, dateEnd.getActualMaximum(Calendar.DATE));
        dateEnd.set(Calendar.HOUR_OF_DAY, dateEnd.getActualMaximum(Calendar.HOUR_OF_DAY));
        dateEnd.set(Calendar.MINUTE, dateEnd.getActualMaximum(Calendar.MINUTE));
        dateEnd.set(Calendar.SECOND, dateEnd.getActualMaximum(Calendar.SECOND));
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

        dateSet();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM yyyy", months);
        monthOutput.setText(dateFormatter.format(date.getTime()));
    }
}
