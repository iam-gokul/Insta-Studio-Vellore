package com.playstore.gokul.mysquad;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {
    private BottomNavigationView mMainView;
    private FrameLayout mMainFrame;

    private HomeFragment mHomeFragment;
    private BookFragment mBookFragment;
    private OrderFragment mOrderFragment;
    private ContactFragment mContactFragment;
    String s1,uid;

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

        setFragment (mHomeFragment);

        mMainView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId ()){
                    case R.id.nav_home :
                        setFragment(mHomeFragment);
                        return true;

                    case R.id.nav_booking:
                        setFragment(mBookFragment);
                        return true;

                    case R.id.nav_orders:
                        setFragmentOrder(mOrderFragment);
                        return true;

                    case R.id.nav_contact:
                        setFragment(mContactFragment);
                        return true;

                        default:
                        return false;

                }
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid =currentFirebaseUser.getUid ().toString ();
        DatabaseReference myRef = database.getReference().child ("bookings").child (uid);


    }

    private void setFragment(Fragment homefragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
        fragmentTransaction.replace (R.id.main_frame,homefragment);
        fragmentTransaction.commit ();
    }

    private void setFragmentOrder(final Fragment homefragment){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid =currentFirebaseUser.getUid ().toString ();
        DatabaseReference myRef = database.getReference().child ("bookings").child (uid);
        myRef.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                s1 = dataSnapshot.child ("userdate").getValue(String.class);

                if(s1!=null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                    fragmentTransaction.replace (R.id.main_frame,homefragment);
                    fragmentTransaction.commit ();



                }
                else{
                    Toast.makeText (home.this, "You haven't Booked any order yet!", Toast.LENGTH_LONG).show ();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });




    }

}
