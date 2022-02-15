package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LiquidPagerAdapter extends FragmentStatePagerAdapter {


    public LiquidPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OnBoarding1();
            case 1:
                return new OnBoarding2();
            case 2:
                return new OnBoarding3();
            case 3:
                return new OnBoarding4();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
