package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.cats.fragments.*;
import com.example.cats.adapters.MyPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements Vote.OnFragmentInteractionListener, Breed.OnFragmentInteractionListener, Search.OnFragmentInteractionListener, Fave.OnFragmentInteractionListener/*, Upload.OnFragmentInteractionListener*/{
    private MyPagerAdapter myPagerAdapter;
    public static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        getSupportActionBar().hide();
        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        String restoredName = prefs.getString("name", null);
        if (restoredName == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", String.valueOf((int)(Math.random()*999999999)));
            editor.apply();
        }
        Log.e("Main","restoredName" + prefs.getString("name", null));
        ViewPager viewPager = findViewById(R.id.pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.vote);
        tabLayout.getTabAt(1).setIcon(R.drawable.breed);
        tabLayout.getTabAt(2).setIcon(R.drawable.search);
        tabLayout.getTabAt(3).setIcon(R.drawable.fave);
    }
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
