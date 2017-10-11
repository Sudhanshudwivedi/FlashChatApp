package com.example.sudhanshu.flash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionspagerAdapter mSelectionPagerAdapter;
    private TabLayout mTablayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.main_page);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Flash Chat");
        mTablayout=(TabLayout)findViewById(R.id.main_tabs);


        mSelectionPagerAdapter=new SectionspagerAdapter(getSupportFragmentManager());

      mViewPager=(ViewPager) findViewById(R.id.tabPager);

      mViewPager.setAdapter(mSelectionPagerAdapter);
        mTablayout=(TabLayout)findViewById(R.id.main_tabs);
        mTablayout.setupWithViewPager(mViewPager);

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
       super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }
        if(item.getItemId()==R.id.menu_account)
        {
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        }
        if(item.getItemId()==R.id.menu_users)
        {
            startActivity(new Intent(MainActivity.this,AllUserActivity.class));
        }

                return true;
    }

}
