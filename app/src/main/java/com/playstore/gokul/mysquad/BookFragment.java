package com.playstore.gokul.mysquad;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {
    RecyclerView expanderRecyclerView;
    View mView;
    CalendarView mCalendarView;
    TextView myDate;
    EditText location;
    Button book;
    private DatabaseReference mDatabase;
    EditText last_name;
    EditText first_name;
    String fname,lname,address,fundate,uid,phone;





    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView= inflater.inflate (R.layout.fragment_book, container, false);



        mCalendarView =mView.findViewById (R.id.calender_view);
        myDate =mView.findViewById (R.id.bookDate);

        first_name = (EditText) mView.findViewById (R.id.bookfirstname);


        last_name = mView.findViewById (R.id.booklastname);


        location = mView.findViewById (R.id.bookLocAddress);



        book = mView.findViewById (R.id.bookDone);

       //Firebase

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid=currentFirebaseUser.getUid ();

        phone=mDatabase.child ("bookings").child (uid).child ("phonenumber").getDatabase ().toString ();
        Log.d("Insta","Phone : "+ phone);


        mCalendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fundate = dayOfMonth+"/"+(month+1)+"/"+year;
                myDate.setText (fundate);

            }
        });


        book.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                fname=first_name.getText().toString ();
                lname=last_name.getText().toString();
                address=location.getText().toString ();
                writeNewBooking (uid,fname,lname,address,fundate);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                uid =currentFirebaseUser.getUid ().toString ();
                DatabaseReference myRef = database.getReference().child ("bookings").child (uid);
                myRef.addValueEventListener(new ValueEventListener () {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String s1 = dataSnapshot.child ("status").getValue(String.class);

                        if(s1!=null) {
                            Toast.makeText (getActivity (), "You already have an On-Going Project!", Toast.LENGTH_LONG).show ();
                        }
                        else{
                            if(fundate!=null){
                                Toast.makeText (getActivity (), "Your Booking has been Recorded", Toast.LENGTH_SHORT).show ();


                            }
                            else{
                                Toast.makeText (getActivity (), "Please Give Date", Toast.LENGTH_SHORT).show ();
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

            }
        });


        return mView;
    }


    //User Class
    @IgnoreExtraProperties
    public class User {

        public String firstName,lastName,address,userdate;


        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String fnn, String lnn,String add,String userdateee) {
            this.firstName = fnn;
            this.lastName = lnn;
            this.address = add;
            this.userdate = userdateee;
            ;

        }

    }

    //Function to add Values in DB
    private void writeNewBooking(String userId,String firstName, String lastName,String address,String userdate) {
        User user = new User(firstName,lastName,address,userdate);

        mDatabase.child("bookings").child(userId).setValue(user);
    }


}
