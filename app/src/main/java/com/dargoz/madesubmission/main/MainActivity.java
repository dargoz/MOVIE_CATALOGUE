package com.dargoz.madesubmission.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewParent;
import android.widget.ListView;

import com.dargoz.madesubmission.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTabLayout = findViewById(R.id.main_tab_layout);
        mainViewPager = findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),1));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
