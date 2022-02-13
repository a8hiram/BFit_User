package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.cuberto.liquid_swipe.LiquidPager;

public class OnBoarding extends AppCompatActivity {

    LiquidPager pager;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        pager = findViewById(R.id.pager);
        viewPager = new ViewPager(getSupportFragmentManager(), 1);
        pager.setAdapter(viewPager);
    }

}