package com.dargoz.madesubmission.main;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.R;
import com.dargoz.madesubmission.main.favorite.FavoriteFragment;
import com.dargoz.madesubmission.repository.AppDatabase;
import com.dargoz.madesubmission.repository.DaoTask;
import com.dargoz.madesubmission.repository.MovieEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "movies").build();

        final TabLayout mainTabLayout = findViewById(R.id.main_tab_layout);
        final ViewPager mainViewPager = findViewById(R.id.main_view_pager);
        final BottomNavigationView bottomNavigationView =
                findViewById(R.id.main_bottom_navigation_view);

        mainTabLayout.addTab(mainTabLayout.newTab()
                .setText(getResources()
                .getString(R.string.tab_title_movie)));
        mainTabLayout.addTab(mainTabLayout.newTab()
                .setText(getResources()
                .getString(R.string.tab_title_tv_show)));
        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),
                mainTabLayout.getTabCount()));
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_favorite){
                    mainViewPager.setAdapter(new FavoritePagerAdapter(getSupportFragmentManager(),2));
                    setTitle(R.string.favorite_title);
                }else {
                    mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),
                            mainTabLayout.getTabCount()));
                    setTitle(R.string.app_name);
                }
                return false;
            }
        });

        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.language_setting){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
