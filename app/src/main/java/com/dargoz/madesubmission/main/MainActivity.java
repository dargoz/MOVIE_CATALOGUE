package com.dargoz.madesubmission.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dargoz.madesubmission.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  {
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTabLayout = findViewById(R.id.main_tab_layout);
        mainViewPager = findViewById(R.id.main_view_pager);

        mainTabLayout.addTab(mainTabLayout.newTab().setText("Movies"));
        mainTabLayout.addTab(mainTabLayout.newTab().setText("Tv Shows"));
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
    }

}
