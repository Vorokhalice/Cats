package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements Vote.OnFragmentInteractionListener, Breed.OnFragmentInteractionListener, Search.OnFragmentInteractionListener, Fave.OnFragmentInteractionListener, Upload.OnFragmentInteractionListener{
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    private static int RESULT_LOAD_IMAGE = 1;
    private MyPagerAdapter myPagerAdapter;
    public static Context contextOfApplication;
    //private LocalDataSource localDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        getSupportActionBar().hide();
        //Toolbar toolbar = findViewById(R.id.tools);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String restoredName = prefs.getString("name", null);
        if (restoredName == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", String.valueOf((int)(Math.random()*99999999)));
            editor.apply();
        }
        Log.e("Main","restoredName" + prefs.getString("name", null));
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.vote);
        tabLayout.getTabAt(1).setIcon(R.drawable.breed);
        tabLayout.getTabAt(2).setIcon(R.drawable.search);
        tabLayout.getTabAt(3).setIcon(R.drawable.fave);
        tabLayout.getTabAt(4).setIcon(R.drawable.upload);
    }
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }*/
}
