package com.playstore.gokul.mysquad;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.FirebaseApp;

public class home extends AppCompatActivity {
    private BottomNavigationView mMainView;
    private FrameLayout mMainFrame;

    private HomeFragment mHomeFragment;
    private BookFragment mBookFragment;
    private OrderFragment mOrderFragment;
    private ContactFragment mContactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        mHomeFragment = new HomeFragment ();
        mBookFragment = new BookFragment ();
        mOrderFragment = new OrderFragment ();
        mContactFragment = new ContactFragment ();

        mMainFrame=(FrameLayout) findViewById (R.id.main_frame);
        mMainView=(BottomNavigationView) findViewById (R.id.main_nav);

        mMainView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId ()){
                    case R.id.nav_home :
                        setFragment(mHomeFragment);
                        return true;

                    case R.id.nav_booking:
                        setFragment(mHomeFragment);
                        return true;

                    case R.id.nav_orders:
                        setFragment(mOrderFragment);
                        return true;

                    case R.id.nav_contact:
                        setFragment(mContactFragment);
                        return true;

                        default:
                        return false;

                }
            }
        });

    }

    private void setFragment(Fragment homefragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
        fragmentTransaction.replace (R.id.main_frame,homefragment);
        fragmentTransaction.commit ();
    }
}
