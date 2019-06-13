package com.example.cats.adapters;

import com.example.cats.fragments.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Vote();
            case 1: return new Breed();
            case 2: return new Search();
            case 3: return new Fave();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 4;
    }
}
