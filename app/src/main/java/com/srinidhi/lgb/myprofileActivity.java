package com.srinidhi.lgb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.rey.material.widget.TabPageIndicator;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class myprofileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        ScrollerViewPager viewPager=findViewById(R.id.scroll_view_pager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        SpringIndicator indicator=findViewById(R.id.indicator);

        indicator.setViewPager(viewPager);


    }
}
