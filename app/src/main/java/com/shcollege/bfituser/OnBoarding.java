package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.cuberto.liquid_swipe.LiquidPager;
import com.shcollege.bfituser.databinding.ActivityOnBoardingBinding;

public class OnBoarding extends AppCompatActivity {

    LiquidPager pager;
    LiquidPagerAdapter adapter;

    ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new LiquidPagerAdapter(getSupportFragmentManager(),1);
        if(adapter != null && binding.pager != null)
            binding.pager.setAdapter(adapter);

    }

}