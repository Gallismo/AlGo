package com.example.algo;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.*;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.algo.custom.CustomActivity;
import com.example.algo.models.Status;
import com.example.algo.models.StatusDao;
import com.example.algo.ui.OrdersFragment;
import com.example.algo.ui.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.example.algo.App.onCreateCallback;

public class MainActivity extends CustomActivity {

    private OrdersFragment ordersFragment;
    private StatsFragment statsFragment;
    private ActionBar actionBar;
    private int currentFragment;

    private final NavigationBarView.OnItemSelectedListener navSelectListener
            = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_item_orders:
                    loadFragment(ordersFragment);
                    actionBar.setTitle("Заказы");
                    return true;
                case R.id.nav_item_stats:
                    loadFragment(statsFragment);
                    actionBar.setTitle("Статистика");
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_orders:
                Intent intent = new Intent(this, FilterOrdersActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        switch (currentFragment) {
            case 1:
                menuInflater.inflate(R.menu.orders_action_bar, menu);
                break;
            case 2:
                menuInflater.inflate(R.menu.stats_action_bar, menu);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void loadFragment(Fragment fragment) {


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment == ordersFragment) {
            currentFragment = 1;
            ft.hide(statsFragment);
            ft.show(ordersFragment);
        }
        if (fragment == statsFragment) {
            currentFragment = 2;
            ft.hide(ordersFragment);
            ft.show(statsFragment);
        }
        ft.commit();

        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        currentFragment = 1;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_bar);

        navigation.setOnItemSelectedListener(navSelectListener);


        ordersFragment = OrdersFragment.newInstance(this);
        statsFragment = StatsFragment.newInstance(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_content, ordersFragment);
        ft.add(R.id.main_content, statsFragment);
        ft.hide(statsFragment);
        ft.commit();

        actionBar.setTitle("Заказы");
    }



}