package com.example.algo;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.algo.custom.CustomActivity;
import com.example.algo.ui.OrdersFragment;
import com.example.algo.ui.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends CustomActivity {

    private OrdersFragment ordersFragment;
    private StatsFragment statsFragment;
    private ActionBar actionBar;

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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(ordersFragment);
        ft.hide(statsFragment);
        ft.show(fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_bar);

        navigation.setOnItemSelectedListener(navSelectListener);

        ordersFragment = OrdersFragment.newInstance();
        statsFragment = StatsFragment.newInstance();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_content, ordersFragment);
        ft.add(R.id.main_content, statsFragment);
        ft.hide(statsFragment);
        ft.commit();

        actionBar.setTitle("Заказы");
    }

}