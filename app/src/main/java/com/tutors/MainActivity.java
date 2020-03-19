package com.tutors;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" Tutors");
        getSupportActionBar().setLogo(R.drawable.tutors);


        setupMyViewpager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    void setupMyViewpager(ViewPager v) {

        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vpa.myset(new LogInFragment(), "LogIn");
        vpa.myset(new SignUpFragment(), "SignUp");
        v.setAdapter(vpa);
    }
}

